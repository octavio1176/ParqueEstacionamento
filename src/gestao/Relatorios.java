package gestao;
import modelo.Lugar;
import modelo.Ticket;
import modelo.Veiculo;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Scanner;

public class Relatorios {

    private GestaoTickets gestaoTickets;
    private GestaoVeiculos gestaoVeiculos;
    private GestaoLugares gestaoLugares;
    private GestaoClientes gestaoClientes;

    public Relatorios(GestaoTickets gestaoTickets, GestaoVeiculos gestaoVeiculos,
                      GestaoLugares gestaoLugares, GestaoClientes gestaoClientes) {
        this.gestaoTickets  = gestaoTickets;
        this.gestaoVeiculos = gestaoVeiculos;
        this.gestaoLugares  = gestaoLugares;
        this.gestaoClientes = gestaoClientes;
    }

    public void relatorioLugares() {
        System.out.println("        RELATORIO DE LUGARES            ");
        ArrayList<Lugar> lugares = gestaoLugares.getLugares();
        if (lugares.isEmpty()) {
            System.out.println("Nenhum lugar registado.");
            return;
        }
        for (Lugar l : lugares) {
            System.out.println("Lugar " + l.getCodigo() + " -> " + (l.isOcupado() ? "Ocupado" : "Livre"));
        }
        System.out.println("Total    : " + lugares.size());
        System.out.println("Ocupados : " + gestaoLugares.contarOcupados());
        System.out.println("Livres   : " + gestaoLugares.contarLivres());
        if (gestaoLugares.contarLivres() <= 5) {
            System.out.println("ALERTA: Apenas " + gestaoLugares.contarLivres() + " lugares livres!");
        }

    }

    public void relatorioReceita() {
        Scanner scanner = new Scanner(System.in);
        boolean correr  = true;
        while (correr) {
            System.out.println("        RELATORIO DE RECEITA            ");
            System.out.println("1. Receita de hoje");
            System.out.println("2. Receita desta semana");
            System.out.println("3. Receita deste mes");
            System.out.println("4. Receita deste ano");
            System.out.println("5. Receita total");
            System.out.println("0. Voltar");
            System.out.print("Escolha: ");
            String opcao = scanner.nextLine();
            switch (opcao) {
                case "1": calcularReceita("hoje");   break;
                case "2": calcularReceita("semana"); break;
                case "3": calcularReceita("mes");    break;
                case "4": calcularReceita("ano");    break;
                case "5": calcularReceita("total");  break;
                case "0": correr = false;            break;
                default:  System.out.println("Opcao invalida!");
            }
        }
    }

    private void calcularReceita(String periodo) {
        LocalDateTime agora = LocalDateTime.now();
        double total        = 0;
        int totalVisitas    = 0;

        for (Ticket t : gestaoTickets.getTickets()) {
            if (t.isActivo() || t.getHoraSaida() == null) continue;

            long dias = Duration.between(t.getHoraSaida(), agora).toDays();

            boolean incluir = false;
            switch (periodo) {
                case "hoje":   incluir = dias == 0;   break;
                case "semana": incluir = dias <= 7;   break;
                case "mes":    incluir = dias <= 30;  break;
                case "ano":    incluir = dias <= 365; break;
                case "total":  incluir = true;        break;
            }
            if (incluir) {
                total += t.getValorPago();
                totalVisitas++;
            }
        }

        String nomePeriodo = "";
        switch (periodo) {
            case "hoje":   nomePeriodo = "HOJE";        break;
            case "semana": nomePeriodo = "ESTA SEMANA"; break;
            case "mes":    nomePeriodo = "ESTE MES";    break;
            case "ano":    nomePeriodo = "ESTE ANO";    break;
            case "total":  nomePeriodo = "TOTAL GERAL"; break;
        }

        System.out.println("  RECEITA " + nomePeriodo);
        if (totalVisitas == 0) {
            System.out.println("Nenhuma visita registada para este periodo.");
        } else {
            System.out.println("Total de visitas : " + totalVisitas);
            System.out.printf("RECEITA          : %.2f MT%n", total);
        }
    }

    public void relatorioTop5Veiculos() {
        System.out.println("     TOP 5 VEICULOS MAIS FREQUENTES     ");
        ArrayList<Veiculo> veiculos = gestaoVeiculos.getVeiculos();
        ArrayList<Ticket> tickets   = gestaoTickets.getTickets();
        if (tickets.isEmpty()) {
            System.out.println("Nenhum ticket registado.");
            return;
        }
        int[] frequencias = new int[veiculos.size()];
        for (int i = 0; i < veiculos.size(); i++) {
            int count = 0;
            for (Ticket t : tickets) {
                if (t.getIdVeiculo() == veiculos.get(i).getId()) count++;
            }
            frequencias[i] = count;
        }
        for (int i = 0; i < veiculos.size() - 1; i++) {
            for (int j = 0; j < veiculos.size() - i - 1; j++) {
                if (frequencias[j] > frequencias[j + 1]) {
                    int tempFreq        = frequencias[j];
                    frequencias[j]      = frequencias[j + 1];
                    frequencias[j + 1]  = tempFreq;
                    Veiculo tempVeiculo = veiculos.get(j);
                    veiculos.set(j, veiculos.get(j + 1));
                    veiculos.set(j + 1, tempVeiculo);
                }
            }
        }
        int limite = Math.min(5, veiculos.size());
        for (int i = 0; i < limite; i++) {
            Veiculo v = veiculos.get(i);
            System.out.println((i + 1) + ". " + v.getMarca() + " " + v.getModelo() +
                    " (" + v.getMatricula() + ") - " + frequencias[i] + " visitas");
        }
    }

    public void menuRelatorios() {
        System.out.println("  RELATORIOS ");
        System.out.println("1. Relatorio de Lugares");
        System.out.println("2. Relatorio de Receita");
        System.out.println("3. Top 5 Veiculos mais frequentes");
        System.out.println("0. Voltar");
        System.out.print("Escolha: ");
    }
}