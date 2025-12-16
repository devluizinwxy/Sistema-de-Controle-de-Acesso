import java.time.LocalDateTime;

// Classe que representa uma tentativa de acesso já processada.
public class LogAcesso {

    private String idPessoa;
    private String nomePessoa;
    private String nomeArea;
    private LocalDateTime dataHora;
    private ResultadoAcesso resultado;
    private MotivoNegacao motivo;

    public LogAcesso(String idPessoa, String nomePessoa, String nomeArea,
                     LocalDateTime dataHora, ResultadoAcesso resultado,
                     MotivoNegacao motivo) {
        this.idPessoa = idPessoa;
        this.nomePessoa = nomePessoa;
        this.nomeArea = nomeArea;
        this.dataHora = dataHora;
        this.resultado = resultado;
        this.motivo = motivo;
    }

    public String getIdPessoa() {
        return idPessoa;
    }

    public String getNomeArea() {
        return nomeArea;
    }

    public ResultadoAcesso getResultado() {
        return resultado;
    }

    public MotivoNegacao getMotivo() {
        return motivo;
    }

    // toString pensado já pro relatório final
    @Override
    public String toString() {
        return dataHora + " | " +
                nomeArea + " | " +
                idPessoa + " - " + nomePessoa + " | " +
                resultado +
                (motivo != null ? " (" + motivo + ")" : "");
    }
}
