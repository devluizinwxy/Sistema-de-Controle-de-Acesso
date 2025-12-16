import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

// Classe responsável por controlar tudo:
// leitura dos arquivos, processamento das tentativas,
// geração dos logs e das estatísticas.
public class ControladorAcessos {

    // Usei Map para pessoas porque a busca pelo ID é muito frequente.
    // Com Map fica direto, sem precisar percorrer lista inteira.
    private Map<String, Pessoa> pessoas = new HashMap<>();

    // Mesmo motivo do Map de pessoas: buscar área pelo nome rapidamente.
    private Map<String, AreaRestrita> areas = new HashMap<>();

    // Lista com as tentativas lidas do arquivo, ainda sem validação.
    private List<TentativaBruta> tentativasBrutas = new ArrayList<>();

    // Lista final com todos os logs processados.
    private List<LogAcesso> logsGerais = new ArrayList<>();

    // Guarda linhas que deram erro ao importar pessoas (ex: e-mail inválido).
    private List<String> errosImportacao = new ArrayList<>();


    // LEITURA DE PESSOAS (NIO)

    public void carregarPessoas(Path pathPessoas) {

        try {
            // Files.lines já devolve um Stream<String>,
            // então dá pra trabalhar direto com Stream API.
            Files.lines(pathPessoas)
                    .filter(linha -> !linha.isBlank()) // ignora linhas vazias
                    .forEach(linha -> {

                        try {
                            String[] partes = linha.split(";");

                            String id = partes[0];
                            String nome = partes[1];
                            String email = partes[2];
                            String senha = partes[3];
                            NivelAcesso nivel = NivelAcesso.valueOf(partes[4]);

                            Pessoa p;

                            // Aqui decido qual subclasse criar com base no nível.
                            switch (nivel) {
                                case VISITANTE -> p = new Visitante(id, nome, email, senha);
                                case FUNCIONARIO -> p = new Funcionario(id, nome, email, senha);
                                case GERENTE -> p = new Gerente(id, nome, email, senha);
                                case ADMINISTRADOR -> p = new Administrador(id, nome, email, senha);
                                default -> throw new IllegalStateException();
                            }

                            // Só adiciona no Map se deu tudo certo.
                            pessoas.put(id, p);

                        } catch (Exception e) {
                            // Se deu erro (ex: e-mail inválido),
                            // não para o programa inteiro.
                            errosImportacao.add("Erro ao importar pessoa: " + linha);
                        }
                    });

        } catch (IOException e) {
            throw new RuntimeException("Erro ao ler arquivo de pessoas", e);
        }
    }


    // LEITURA DE ÁREAS (NIO)

    public void carregarAreas(Path pathAreas) {

        try {
            Files.lines(pathAreas)
                    .filter(linha -> !linha.isBlank())
                    .forEach(linha -> {

                        String[] partes = linha.split(";");

                        String nomeArea = partes[0];
                        NivelAcesso nivelMinimo = NivelAcesso.valueOf(partes[1]);

                        AreaRestrita area = new AreaRestrita(nomeArea, nivelMinimo);

                        areas.put(nomeArea, area);
                    });

        } catch (IOException e) {
            throw new RuntimeException("Erro ao ler arquivo de áreas", e);
        }
    }


    // LEITURA DAS TENTATIVAS (NIO)

    public void carregarTentativas(Path pathTentativas) {

        try {
            Files.lines(pathTentativas)
                    .filter(linha -> !linha.isBlank())
                    .map(linha -> {
                        // Aqui só converto texto em objeto simples,
                        // sem aplicar regra nenhuma ainda.
                        String[] partes = linha.split(";");

                        return new TentativaBruta(
                                partes[0],
                                partes[1],
                                partes[2],
                                LocalDateTime.parse(partes[3])
                        );
                    })
                    .forEach(tentativasBrutas::add);

        } catch (IOException e) {
            throw new RuntimeException("Erro ao ler arquivo de tentativas", e);
        }
    }


    // PROCESSAMENTO DAS TENTATIVAS

    public void processarTentativas() {

        tentativasBrutas.stream()
                .map(t -> {

                    Pessoa pessoa = pessoas.get(t.idPessoa);
                    AreaRestrita area = areas.get(t.nomeArea);

                    // Se a pessoa não existe
                    if (pessoa == null) {
                        return new LogAcesso(
                                t.idPessoa,
                                "DESCONHECIDO",
                                t.nomeArea,
                                t.dataHora,
                                ResultadoAcesso.NEGADO,
                                MotivoNegacao.PESSOA_INEXISTENTE
                        );
                    }

                    // Se a área não existe
                    if (area == null) {
                        return new LogAcesso(
                                pessoa.getId(),
                                pessoa.getNome(),
                                t.nomeArea,
                                t.dataHora,
                                ResultadoAcesso.NEGADO,
                                MotivoNegacao.AREA_INEXISTENTE
                        );
                    }

                    try {
                        // Chama a regra de acesso da área
                        return area.registrarTentativa(
                                pessoa,
                                t.senhaInformada,
                                t.dataHora
                        );

                    } catch (SenhaInvalidaException e) {
                        return new LogAcesso(
                                pessoa.getId(),
                                pessoa.getNome(),
                                t.nomeArea,
                                t.dataHora,
                                ResultadoAcesso.NEGADO,
                                MotivoNegacao.SENHA_INVALIDA
                        );

                    } catch (AcessoNegadoException e) {
                        return new LogAcesso(
                                pessoa.getId(),
                                pessoa.getNome(),
                                t.nomeArea,
                                t.dataHora,
                                ResultadoAcesso.NEGADO,
                                MotivoNegacao.NIVEL_INSUFICIENTE
                        );
                    }
                })
                .peek(logsGerais::add) // já adiciona na lista geral
                .collect(Collectors.toList()); // força execução do stream
    }


    // ESTATÍSTICAS COM STREAMS


    // Conta quantas tentativas cada pessoa fez
    public Map<String, Long> contarTentativasPorPessoa() {
        return logsGerais.stream()
                .collect(Collectors.groupingBy(
                        LogAcesso::getIdPessoa,
                        Collectors.counting()
                ));
    }

    // Conta tentativas por área
    public Map<String, Long> contarTentativasPorArea() {
        return logsGerais.stream()
                .collect(Collectors.groupingBy(
                        LogAcesso::getNomeArea,
                        Collectors.counting()
                ));
    }

    // Conta só as negativas, agrupando pelo motivo
    public Map<MotivoNegacao, Long> contarNegativasPorMotivo() {
        return logsGerais.stream()
                .filter(l -> l.getResultado() == ResultadoAcesso.NEGADO)
                .collect(Collectors.groupingBy(
                        LogAcesso::getMotivo,
                        Collectors.counting()
                ));
    }

    // Calcula porcentagem de sucesso por pessoa
    public Map<String, Double> porcentagemSucessoPorPessoa() {

        return logsGerais.stream()
                .collect(Collectors.groupingBy(
                        LogAcesso::getIdPessoa,
                        Collectors.collectingAndThen(
                                Collectors.toList(),
                                lista -> {
                                    long total = lista.size();
                                    long sucesso = lista.stream()
                                            .filter(l -> l.getResultado() == ResultadoAcesso.PERMITIDO)
                                            .count();
                                    return (total == 0) ? 0.0 : (sucesso * 100.0) / total;
                                }
                        )
                ));
    }


    // RELATÓRIO FINAL (NIO)

    public void salvarRelatorio(Path pathSaida) {

        List<String> linhas = new ArrayList<>();

        linhas.add("RELATÓRIO DE CONTROLE DE ACESSO");
        linhas.add("================================");
        linhas.add("Total de tentativas: " + logsGerais.size());

        long permitidos = logsGerais.stream()
                .filter(l -> l.getResultado() == ResultadoAcesso.PERMITIDO)
                .count();

        linhas.add("Permitidas: " + permitidos);
        linhas.add("Negadas: " + (logsGerais.size() - permitidos));
        linhas.add("");

        linhas.add("DETALHAMENTO DAS TENTATIVAS:");
        logsGerais.stream()
                .map(LogAcesso::toString)
                .forEach(linhas::add);

        try {
            Files.write(
                    pathSaida,
                    linhas,
                    StandardOpenOption.CREATE,
                    StandardOpenOption.TRUNCATE_EXISTING
            );
        } catch (IOException e) {
            throw new RuntimeException("Erro ao salvar relatório", e);
        }
    }
}
