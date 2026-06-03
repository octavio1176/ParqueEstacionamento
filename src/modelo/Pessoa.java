package modelo;

public abstract class Pessoa {

    private int id;
    private String nome;
    private String telefone;
    private String email;

    public Pessoa(int id, String nome, String telefone, String email) {
        this.id       = id;
        this.nome     = nome;
        this.telefone = telefone;
        this.email    = email;
    }

    public int getId()          { return id; }
    public String getNome()     { return nome; }
    public String getTelefone() { return telefone; }
    public String getEmail()    { return email; }

    public void setNome(String nome)         { this.nome = nome; }
    public void setTelefone(String telefone) { this.telefone = telefone; }
    public void setEmail(String email)       { this.email = email; }

    public abstract String toFicheiro();

    @Override
    public abstract String toString();
}
