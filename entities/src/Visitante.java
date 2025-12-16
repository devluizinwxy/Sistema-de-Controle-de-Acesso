// Classe concreta para visitantes.
// Implementa Autenticavel porque também pode tentar acessar áreas.
public class Visitante extends Pessoa implements Autenticavel {


    public Visitante(String id, String nome, String email, String senha) {
        super(id, nome, email, senha);
    }


    // Comparação direta de senha, suficiente para o escopo do trabalho
    @Override
    public boolean autenticar(String senha) {
        return this.senha.equals(senha);
    }


    @Override
    public NivelAcesso getNivelAcesso() {
        return NivelAcesso.VISITANTE;
    }
}