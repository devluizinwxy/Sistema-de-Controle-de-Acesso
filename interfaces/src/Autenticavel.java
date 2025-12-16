// Interface usada para padronizar quem pode autenticar no sistema.
// Todas as pessoas do sistema implementam isso.
public interface Autenticavel {

    // Verifica se a senha informada é a correta
    boolean autenticar(String senha);

    // Retorna o nível de acesso da pessoa
    NivelAcesso getNivelAcesso();
}
