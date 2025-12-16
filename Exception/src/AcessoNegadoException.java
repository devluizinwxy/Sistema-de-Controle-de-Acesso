// Usada quando a pessoa existe, a senha está certa,
// mas o nível de acesso não é suficiente para a área.
public class AcessoNegadoException extends RuntimeException {
    public AcessoNegadoException(String msg) {
        super(msg);
    }
}