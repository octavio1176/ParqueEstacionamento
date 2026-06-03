package modelo;

public class Lugar {

    private String codigo;
    private boolean ocupado;

    public Lugar(String codigo) {
        this.codigo  = codigo;
        this.ocupado = false;
    }

    public Lugar(String codigo, boolean ocupado) {
        this.codigo  = codigo;
        this.ocupado = ocupado;
    }

    public String getCodigo()               { return codigo; }
    public boolean isOcupado()              { return ocupado; }

    public void setCodigo(String codigo)    { this.codigo = codigo; }
    public void setOcupado(boolean ocupado) { this.ocupado = ocupado; }

    public String toFicheiro() {
        return codigo + ";" + ocupado;
    }

    @Override
    public String toString() {
        return "Codigo : " + codigo + "\n" +
               "Estado : " + (ocupado ? "Ocupado" : "Disponivel");
    }
}
