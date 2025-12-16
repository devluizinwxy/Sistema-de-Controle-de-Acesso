import java.util.Objects;
import java.util.regex.Pattern;

// Classe abstrata porque ninguém é "Pessoa" direto.
// Sempre vai ser Visitante, Funcionario, etc.
public abstract class Pessoa {

    protected String id;
    protected String nome;
    protected String email;
    protected String senha;

    // Regex simples só pra garantir que tem algo@algo.algo
    private static final Pattern EMAIL_REGEX =
            Pattern.compile("^[^@]+@[^@]+\\.[^@]+$");

    // Construtor já valida o e-mail.
    // Se estiver errado, nem cria o objeto.
    public Pessoa(String id, String nome, String email, String senha) {
        if (!EMAIL_REGEX.matcher(email).matches()) {
            throw new EmailInvalidoException("E-mail inválido: " + email);
        }
        this.id = id;
        this.nome = nome;
        this.email = email;
        this.senha = senha;
    }

    // Getters básicos
    public String getId() {
        return id;
    }

    public String getNome() {
        return nome;
    }

    // equals e hashCode baseados no id,
    // porque no sistema o id identifica a pessoa.
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Pessoa)) return false;
        Pessoa pessoa = (Pessoa) o;
        return Objects.equals(id, pessoa.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    // toString simples, usado mais pra debug
    @Override
    public String toString() {
        return id + " - " + nome;
    }
}
