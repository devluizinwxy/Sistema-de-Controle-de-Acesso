public class Gerente extends Pessoa implements Autenticavel {

    public Gerente(String id, String nome, String email, String senha) {
        super(id, nome, email, senha);
    }

    @Override
    public boolean autenticar(String senha) {
        return this.senha.equals(senha);
    }

    @Override
    public NivelAcesso getNivelAcesso() {
        return NivelAcesso.GERENTE;
    }
}
