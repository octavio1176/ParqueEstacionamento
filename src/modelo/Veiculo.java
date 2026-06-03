package modelo;

public class Veiculo {

    private int id;
    private String matricula;
    private String marca;
    private String modelo;
    private int idCliente;
    private static int contadorId = 1;

    public Veiculo(String matricula, String marca, String modelo, int idCliente) {
        this.id        = contadorId++;
        this.matricula = matricula;
        this.marca     = marca;
        this.modelo    = modelo;
        this.idCliente = idCliente;
    }

    public Veiculo(int id, String matricula, String marca, String modelo, int idCliente) {
        this.id        = id;
        this.matricula = matricula;
        this.marca     = marca;
        this.modelo    = modelo;
        this.idCliente = idCliente;
    }

    public static void setContadorId(int valor) { contadorId = valor; }
    public static int getContadorId()           { return contadorId; }

    public int getId()           { return id; }
    public String getMatricula() { return matricula; }
    public String getMarca()     { return marca; }
    public String getModelo()    { return modelo; }
    public int getIdCliente()    { return idCliente; }

    public void setMatricula(String matricula) { this.matricula = matricula; }
    public void setMarca(String marca)         { this.marca = marca; }
    public void setModelo(String modelo)       { this.modelo = modelo; }

    public String toFicheiro() {
        return id + ";" + matricula + ";" + marca + ";" + modelo + ";" + idCliente;
    }

    @Override
    public String toString() {
        return "ID        : " + id        + "\n" +
               "Matricula : " + matricula + "\n" +
               "Marca     : " + marca     + "\n" +
               "Modelo    : " + modelo    + "\n" +
               "ID Cliente: " + idCliente;
    }
}
