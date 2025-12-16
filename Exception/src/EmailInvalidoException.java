// Exceção usada quando o e-mail não segue um formato válido.
// Facilita identificar erro de cadastro logo na leitura do arquivo.
public class EmailInvalidoException extends RuntimeException {
    public EmailInvalidoException(String msg) {
        super(msg);
    }
}