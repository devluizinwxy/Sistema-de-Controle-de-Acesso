import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

// Representa uma área do sistema que exige um nível mínimo.
public class AreaRestrita {

    private String nomeArea;
    private NivelAcesso nivelMinimoAcesso;
    private List<LogAcesso> logs = new ArrayList<>();

    public AreaRestrita(String nomeArea, NivelAcesso nivelMinimoAcesso) {
        this.nomeArea = nomeArea;
        this.nivelMinimoAcesso = nivelMinimoAcesso;
    }

    public String getNomeArea() {
        return nomeArea;
    }

    public NivelAcesso getNivelMinimoAcesso() {
        return nivelMinimoAcesso;
    }

    // Esse método concentra a regra de acesso da área.
    public LogAcesso registrarTentativa(Pessoa p,
                                        String senhaInformada,
                                        LocalDateTime dataHora) {

        Autenticavel auth = (Autenticavel) p;

        // Primeiro verifica senha
        if (!auth.autenticar(senhaInformada)) {
            throw new SenhaInvalidaException("Senha incorreta");
        }

        // Depois verifica nível
        if (!auth.getNivelAcesso().temAcessoA(nivelMinimoAcesso)) {
            throw new AcessoNegadoException("Nível insuficiente");
        }

        // Se passou por tudo, acesso permitido
        LogAcesso log = new LogAcesso(
                p.getId(),
                p.getNome(),
                nomeArea,
                dataHora,
                ResultadoAcesso.PERMITIDO,
                null
        );

        logs.add(log);
        return log;
    }
}
