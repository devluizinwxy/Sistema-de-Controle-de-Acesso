// Disparada quando a senha informada não confere com a cadastrada.
// Evita usar vários if espalhados pelo código.
public class SenhaInvalidaException extends RuntimeException {
    public SenhaInvalidaException(String msg) {
        super(msg);
    }
}