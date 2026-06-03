package modelo;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Ticket {

    private int id;
    private int idCliente;
    private int idVeiculo;
    private String codigoLugar;
    private LocalDateTime horaEntrada;
    private LocalDateTime horaSaida;
    private double valorPago;
    private boolean activo;
    private static int contadorId = 1;

    private static final DateTimeFormatter FORMATO = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");

    public Ticket(int idCliente, int idVeiculo, String codigoLugar) {
        this.id          = contadorId++;
        this.idCliente   = idCliente;
        this.idVeiculo   = idVeiculo;
        this.codigoLugar = codigoLugar;
        this.horaEntrada = LocalDateTime.now();
        this.horaSaida   = null;
        this.valorPago   = 0;
        this.activo      = true;
    }

    public Ticket(int id, int idCliente, int idVeiculo, String codigoLugar,
                  LocalDateTime horaEntrada, LocalDateTime horaSaida,
                  double valorPago, boolean activo) {
        this.id          = id;
        this.idCliente   = idCliente;
        this.idVeiculo   = idVeiculo;
        this.codigoLugar = codigoLugar;
        this.horaEntrada = horaEntrada;
        this.horaSaida   = horaSaida;
        this.valorPago   = valorPago;
        this.activo      = activo;
    }

    public static void setContadorId(int valor) { contadorId = valor; }
    public static int getContadorId()           { return contadorId; }

    public int getId()                    { return id; }
    public int getIdCliente()             { return idCliente; }
    public int getIdVeiculo()             { return idVeiculo; }
    public String getCodigoLugar()        { return codigoLugar; }
    public LocalDateTime getHoraEntrada() { return horaEntrada; }
    public LocalDateTime getHoraSaida()   { return horaSaida; }
    public double getValorPago()          { return valorPago; }
    public boolean isActivo()             { return activo; }

    public void setHoraSaida(LocalDateTime horaSaida) { this.horaSaida = horaSaida; }
    public void setValorPago(double valorPago)        { this.valorPago = valorPago; }
    public void setActivo(boolean activo)             { this.activo = activo; }


    public String toFicheiro() {
        return id           + ";" +
               idCliente    + ";" +
               idVeiculo    + ";" +
               codigoLugar  + ";" +
               horaEntrada.format(FORMATO) + ";" +
               (horaSaida != null ? horaSaida.format(FORMATO) : "null") + ";" +
               valorPago    + ";" +
               activo;
    }

    @Override
    public String toString() {
        return "ID Ticket  : " + id                                                                  + "\n" +
               "ID Cliente : " + idCliente                                                           + "\n" +
               "ID Veiculo : " + idVeiculo                                                           + "\n" +
               "Lugar      : " + codigoLugar                                                         + "\n" +
               "Entrada    : " + horaEntrada.format(FORMATO)                                         + "\n" +
               "Saida      : " + (horaSaida != null ? horaSaida.format(FORMATO) : "Ainda no parque") + "\n" +
               "Valor Pago : " + valorPago + " MT"                                                   + "\n" +
               "Estado     : " + (activo ? "Activo" : "Encerrado");
    }
}
