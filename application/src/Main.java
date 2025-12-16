import java.nio.file.Path;
import java.nio.file.Paths;

// Classe só pra executar e testar tudo.
public class Main {

    public static void main(String[] args) {

        ControladorAcessos controlador = new ControladorAcessos();

        // Caminhos dos arquivos de entrada
        controlador.carregarPessoas(Paths.get("pessoas.txt"));
        controlador.carregarAreas(Paths.get("areas.txt"));
        controlador.carregarTentativas(Paths.get("tentativas.txt"));

        // Processa todas as tentativas
        controlador.processarTentativas();

        // Salva o relatório final
        Path saida = Paths.get("relatorio_acessos.txt");
        controlador.salvarRelatorio(saida);

        System.out.println("Processamento finalizado. Verifique o relatório.");
    }
}
