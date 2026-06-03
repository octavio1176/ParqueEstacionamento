package ficheiros;

import modelo.Cliente;
import modelo.Lugar;
import modelo.Ticket;
import modelo.Veiculo;
import java.io.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.StringTokenizer;

public class GestorFicheiros {

    private static final String FICHEIRO_CLIENTES = "dados/clientes.txt";
    private static final String FICHEIRO_VEICULOS = "dados/veiculos.txt";
    private static final String FICHEIRO_LUGARES  = "dados/lugares.txt";
    private static final String FICHEIRO_TICKETS  = "dados/tickets.txt";

    private static final DateTimeFormatter FORMATO = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");

    public GestorFicheiros() {
        new File("dados").mkdir();
    }

    public void gravarClientes(ArrayList<Cliente> clientes) {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(FICHEIRO_CLIENTES))) {
            for (Cliente c : clientes) { bw.write(c.toFicheiro()); bw.newLine(); }
        } catch (IOException e) {
            System.out.println("Erro ao gravar clientes: " + e.getMessage());
        }
    }

    public ArrayList<Cliente> carregarClientes() {
        ArrayList<Cliente> clientes = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(FICHEIRO_CLIENTES))) {
            String linha;
            int maiorId = 0;
            while ((linha = br.readLine()) != null) {
                if (linha.trim().isEmpty()) continue;
                StringTokenizer st = new StringTokenizer(linha, ";");
                if (st.countTokens() < 4) continue;
                int id          = Integer.parseInt(st.nextToken());
                String nome     = st.nextToken();
                String telefone = st.nextToken();
                String email    = st.nextToken();
                clientes.add(new Cliente(id, nome, telefone, email));
                if (id > maiorId) maiorId = id;
            }
            Cliente.setContadorId(maiorId + 1);
        } catch (FileNotFoundException e) {
        } catch (IOException e) {
            System.out.println("Erro ao carregar clientes: " + e.getMessage());
        }
        return clientes;
    }

    public void gravarVeiculos(ArrayList<Veiculo> veiculos) {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(FICHEIRO_VEICULOS))) {
            for (Veiculo v : veiculos) { bw.write(v.toFicheiro()); bw.newLine(); }
        } catch (IOException e) {
            System.out.println("Erro ao gravar veiculos: " + e.getMessage());
        }
    }

    public ArrayList<Veiculo> carregarVeiculos() {
        ArrayList<Veiculo> veiculos = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(FICHEIRO_VEICULOS))) {
            String linha;
            int maiorId = 0;
            while ((linha = br.readLine()) != null) {
                if (linha.trim().isEmpty()) continue;
                StringTokenizer st = new StringTokenizer(linha, ";");
                if (st.countTokens() < 5) continue;
                int id           = Integer.parseInt(st.nextToken());
                String matricula = st.nextToken();
                String marca     = st.nextToken();
                String modelo    = st.nextToken();
                int idCliente    = Integer.parseInt(st.nextToken());
                veiculos.add(new Veiculo(id, matricula, marca, modelo, idCliente));
                if (id > maiorId) maiorId = id;
            }
            Veiculo.setContadorId(maiorId + 1);
        } catch (FileNotFoundException e) {
        } catch (IOException e) {
            System.out.println("Erro ao carregar veiculos: " + e.getMessage());
        }
        return veiculos;
    }

    public void gravarLugares(ArrayList<Lugar> lugares) {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(FICHEIRO_LUGARES))) {
            for (Lugar l : lugares) { bw.write(l.toFicheiro()); bw.newLine(); }
        } catch (IOException e) {
            System.out.println("Erro ao gravar lugares: " + e.getMessage());
        }
    }

    public ArrayList<Lugar> carregarLugares() {
        ArrayList<Lugar> lugares = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(FICHEIRO_LUGARES))) {
            String linha;
            while ((linha = br.readLine()) != null) {
                if (linha.trim().isEmpty()) continue;
                StringTokenizer st = new StringTokenizer(linha, ";");
                if (st.countTokens() < 2) continue;
                String codigo   = st.nextToken();
                boolean ocupado = Boolean.parseBoolean(st.nextToken());
                lugares.add(new Lugar(codigo, ocupado));
            }
        } catch (FileNotFoundException e) {
        } catch (IOException e) {
            System.out.println("Erro ao carregar lugares: " + e.getMessage());
        }
        return lugares;
    }

    public void gravarTickets(ArrayList<Ticket> tickets) {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(FICHEIRO_TICKETS))) {
            for (Ticket t : tickets) { bw.write(t.toFicheiro()); bw.newLine(); }
        } catch (IOException e) {
            System.out.println("Erro ao gravar tickets: " + e.getMessage());
        }
    }

    public ArrayList<Ticket> carregarTickets() {
        ArrayList<Ticket> tickets = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(FICHEIRO_TICKETS))) {
            String linha;
            int maiorId = 0;
            while ((linha = br.readLine()) != null) {
                if (linha.trim().isEmpty()) continue;
                StringTokenizer st = new StringTokenizer(linha, ";");
                if (st.countTokens() < 8) continue;
                int id                = Integer.parseInt(st.nextToken());
                int idCliente         = Integer.parseInt(st.nextToken());
                int idVeiculo         = Integer.parseInt(st.nextToken());
                String codigoLugar    = st.nextToken();
                LocalDateTime entrada = LocalDateTime.parse(st.nextToken(), FORMATO);
                String saidaStr       = st.nextToken();
                LocalDateTime saida   = saidaStr.equals("null") ? null : LocalDateTime.parse(saidaStr, FORMATO);
                double valorPago      = Double.parseDouble(st.nextToken());
                boolean activo        = Boolean.parseBoolean(st.nextToken());
                tickets.add(new Ticket(id, idCliente, idVeiculo, codigoLugar, entrada, saida, valorPago, activo));
                if (id > maiorId) maiorId = id;
            }
            Ticket.setContadorId(maiorId + 1);
        } catch (FileNotFoundException e) {
        } catch (IOException e) {
            System.out.println("Erro ao carregar tickets: " + e.getMessage());
        }
        return tickets;
    }
}
