package gestao;

import modelo.Cliente;
import modelo.Lugar;
import modelo.Ticket;
import modelo.Veiculo;
import java.util.ArrayList;

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
        System.out.println("        RELATORIO DE RECEITA            ");
        double totalGeral = 0;
        int totalVisitas  = 0;
        for (Ticket t : gestaoTickets.getTickets()) {
            if (!t.isActivo()) {
                totalGeral += t.getValorPago();
                totalVisitas++;
            }
        }
        System.out.println("Total de visitas : " + totalVisitas);
        System.out.printf("RECEITA TOTAL    : %.2f MT%n", totalGeral);
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
        System.out.println("RELATORIOS ");
        System.out.println("1. Relatorio de Lugares");
        System.out.println("2. Relatorio de Receita");
        System.out.println("3. Top 5 Veiculos mais frequentes");
        System.out.println("0. Voltar");
        System.out.print("Escolha: ");
    }
}
