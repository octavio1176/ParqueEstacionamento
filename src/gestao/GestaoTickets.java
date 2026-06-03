package gestao;

import ficheiros.GestorFicheiros;
import modelo.Lugar;
import modelo.Ticket;
import modelo.Veiculo;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Scanner;

public class GestaoTickets {

    private ArrayList<Ticket> tickets;
    private GestaoVeiculos gestaoVeiculos;
    private GestaoLugares gestaoLugares;
    private Scanner scanner;
    private GestorFicheiros gestor;

    private static final double TARIFA_POR_HORA = 100.0;
    private static final double IVA             = 0.17;
    private static final DateTimeFormatter FORMATO = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");

    public GestaoTickets(GestaoVeiculos gestaoVeiculos, GestaoLugares gestaoLugares, GestorFicheiros gestor) {
        this.tickets        = new ArrayList<>();
        this.gestaoVeiculos = gestaoVeiculos;
        this.gestaoLugares  = gestaoLugares;
        this.scanner        = new Scanner(System.in);
        this.gestor         = gestor;
    }

    public void registarEntrada() {
        System.out.println(" REGISTAR ENTRADA ");
        Lugar lugar = gestaoLugares.buscarPrimeiroLivre();
        if (lugar == null) {
            System.out.println("O parque nao tem lugares disponiveis!");
            return;
        }
        System.out.print("Matricula do veiculo: ");
        String matricula = scanner.nextLine();
        Veiculo veiculo = gestaoVeiculos.buscarPorMatricula(matricula);
        if (veiculo == null) {
            System.out.println("Veiculo com matricula " + matricula + " nao encontrado!");
            return;
        }
        if (buscarTicketActivoPorVeiculo(veiculo.getId()) != null) {
            System.out.println("O veiculo " + matricula + " ja se encontra no parque!");
            return;
        }
        Ticket ticket = new Ticket(veiculo.getIdCliente(), veiculo.getId(), lugar.getCodigo());
        tickets.add(ticket);
        lugar.setOcupado(true);
        gestor.gravarTickets(tickets);
        gestor.gravarLugares(gestaoLugares.getLugares());
        System.out.println("Entrada registada com sucesso!");
        System.out.println("Ticket ID   : " + ticket.getId());
        System.out.println("Veiculo     : " + veiculo.getMarca() + " " + veiculo.getModelo());
        System.out.println("Matricula   : " + matricula);
        System.out.println("Lugar       : " + lugar.getCodigo());
        System.out.println("Hora entrada: " + ticket.getHoraEntrada().format(FORMATO));
    }

    public void registarSaida() {
        System.out.println(" REGISTAR SAIDA ");
        System.out.print("Matricula do veiculo: ");
        String matricula = scanner.nextLine();
        Veiculo veiculo = gestaoVeiculos.buscarPorMatricula(matricula);
        if (veiculo == null) {
            System.out.println("Veiculo com matricula " + matricula + " nao encontrado!");
            return;
        }
        Ticket ticket = buscarTicketActivoPorVeiculo(veiculo.getId());
        if (ticket == null) {
            System.out.println("O veiculo " + matricula + " nao se encontra no parque!");
            return;
        }
        LocalDateTime horaSaida = LocalDateTime.now();
        ticket.setHoraSaida(horaSaida);
        long minutos     = Duration.between(ticket.getHoraEntrada(), horaSaida).toMinutes();
        double horas     = minutos / 60.0;
        if (horas < 1) horas = 1;
        double valorSemIva = horas * TARIFA_POR_HORA;
        double valorIva    = valorSemIva * IVA;
        double valorTotal  = valorSemIva + valorIva;
        ticket.setValorPago(valorTotal);
        ticket.setActivo(false);
        Lugar lugar = gestaoLugares.buscarPorCodigo(ticket.getCodigoLugar());
        if (lugar != null) lugar.setOcupado(false);
        gestor.gravarTickets(tickets);
        gestor.gravarLugares(gestaoLugares.getLugares());
        System.out.println("           RECIBO DE PAGAMENTO          ");
        System.out.println("Ticket ID    : " + ticket.getId());
        System.out.println("Matricula    : " + matricula);
        System.out.println("Veiculo      : " + veiculo.getMarca() + " " + veiculo.getModelo());
        System.out.println("Lugar        : " + ticket.getCodigoLugar());
        System.out.println("Hora entrada : " + ticket.getHoraEntrada().format(FORMATO));
        System.out.println("Hora saida   : " + horaSaida.format(FORMATO));
        System.out.println("Tempo        : " + minutos + " minutos");
        System.out.printf("Valor s/ IVA : %.2f MT%n", valorSemIva);
        System.out.printf("IVA (17%%)   : %.2f MT%n", valorIva);
        System.out.printf("TOTAL        : %.2f MT%n", valorTotal);
    }

    public void listar() {
        System.out.println("LISTA DE TICKETS");
        if (tickets.isEmpty()) {
            System.out.println("Nenhum ticket registado.");
            return;
        }
        for (Ticket t : tickets) {
            System.out.println(t);
        }

    }

    public void contaCorrenteCliente() {
        System.out.println(" CONTA CORRENTE DO CLIENTE ");
        System.out.print("Insira o ID do cliente: ");
        int idCliente = Integer.parseInt(scanner.nextLine());
        double totalGasto = 0;
        boolean encontrou = false;
        for (Ticket t : tickets) {
            if (t.getIdCliente() == idCliente && !t.isActivo()) {
                System.out.println(t);
                totalGasto += t.getValorPago();
                encontrou = true;
            }
        }
        if (!encontrou) {
            System.out.println("Nenhuma visita registada para o cliente ID " + idCliente);
            return;
        }
        System.out.printf("TOTAL GASTO: %.2f MT%n", totalGasto);
    }

    public Ticket buscarTicketActivoPorVeiculo(int idVeiculo) {
        for (Ticket t : tickets) {
            if (t.getIdVeiculo() == idVeiculo && t.isActivo()) return t;
        }
        return null;
    }

    public void setTickets(ArrayList<Ticket> tickets) { this.tickets = tickets; }
    public ArrayList<Ticket> getTickets()             { return tickets; }
}
