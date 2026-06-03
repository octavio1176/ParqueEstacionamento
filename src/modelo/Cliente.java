package modelo;

public class Cliente extends Pessoa {

    private static int contadorId = 1;

    public Cliente(String nome, String telefone, String email) {
        super(contadorId++, nome, telefone, email);
    }

    public Cliente(int id, String nome, String telefone, String email) {
        super(id, nome, telefone, email);
    }

    public static void setContadorId(int valor) { contadorId = valor; }
    public static int getContadorId()           { return contadorId; }

    @Override
    public String toFicheiro() {
        return getId() + ";" + getNome() + ";" + getTelefone() + ";" + getEmail();
    }

    @Override
    public String toString() {
        return "ID       : " + getId()       + "\n" +
               "Nome     : " + getNome()     + "\n" +
               "Telefone : " + getTelefone() + "\n" +
               "Email    : " + getEmail();
    }
}
