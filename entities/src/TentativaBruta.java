import java.time.LocalDateTime;

// Classe auxiliar só pra guardar os dados lidos do arquivo.
// Aqui ainda não tem regra de negócio.
public class TentativaBruta {

    String idPessoa;
    String nomeArea;
    String senhaInformada;
    LocalDateTime dataHora;

    public TentativaBruta(String idPessoa, String nomeArea,
                          String senhaInformada, LocalDateTime dataHora) {
        this.idPessoa = idPessoa;
        this.nomeArea = nomeArea;
        this.senhaInformada = senhaInformada;
        this.dataHora = dataHora;
    }
}
