package gestao;

import ficheiros.GestorFicheiros;
import modelo.Cliente;
import java.util.ArrayList;
import java.util.Scanner;

public class GestaoClientes implements IGestao {

    private ArrayList<Cliente> clientes;
    private Scanner scanner;
    private GestorFicheiros gestor;

    public GestaoClientes(GestorFicheiros gestor) {
        this.clientes = new ArrayList<>();
        this.scanner  = new Scanner(System.in);
        this.gestor   = gestor;
    }

    @Override
    public void criar() {
        System.out.println(" REGISTAR CLIENTE ");
        System.out.print("Nome     : ");
        String nome = scanner.nextLine();
        System.out.print("Telefone : ");
        String telefone = scanner.nextLine();
        String email = "";
        boolean emailValido = false;
        while (!emailValido) {
            System.out.print("Email    : ");
            email = scanner.nextLine();
            if (email.matches("[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}")) {
                emailValido = true;
            } else {
                System.out.println("Email invalido! Tente novamente.");
            }
        }
        Cliente cliente = new Cliente(nome, telefone, email);
        clientes.add(cliente);
        gestor.gravarClientes(clientes);
        System.out.println("Cliente registado com sucesso! ID: " + cliente.getId());
    }

    @Override
    public void actualizar() {
        System.out.println(" ACTUALIZAR CLIENTE ");
        System.out.print("Insira o ID do cliente: ");
        int id = Integer.parseInt(scanner.nextLine());
        Cliente cliente = buscarPorId(id);
        if (cliente == null) {
            System.out.println("Cliente com ID " + id + " nao encontrado!");
            return;
        }

        System.out.println(cliente);
        System.out.print("Nome [" + cliente.getNome() + "]: ");
        String nome = scanner.nextLine();
        if (!nome.isEmpty()) cliente.setNome(nome);
        System.out.print("Telefone [" + cliente.getTelefone() + "]: ");
        String telefone = scanner.nextLine();
        if (!telefone.isEmpty()) cliente.setTelefone(telefone);
        System.out.print("Email [" + cliente.getEmail() + "]: ");
        String email = scanner.nextLine();
        if (!email.isEmpty()) {
            if (email.matches("[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}")) {
                cliente.setEmail(email);
            } else {
                System.out.println("Email invalido! O email nao foi actualizado.");
            }
        }
        gestor.gravarClientes(clientes);
        System.out.println("Cliente actualizado com sucesso!");
    }

    @Override
    public void remover() {
        System.out.println(" REMOVER CLIENTE ");
        System.out.print("Insira o ID do cliente: ");
        int id = Integer.parseInt(scanner.nextLine());
        Cliente cliente = buscarPorId(id);
        if (cliente == null) {
            System.out.println("Cliente com ID " + id + " nao encontrado!");
            return;
        }
        System.out.println(cliente);
        System.out.print("Tem certeza? (S/N): ");
        String confirmacao = scanner.nextLine();
        if (confirmacao.equalsIgnoreCase("S")) {
            clientes.remove(cliente);
            gestor.gravarClientes(clientes);
            System.out.println("Cliente removido com sucesso!");
        } else {
            System.out.println("Operacao cancelada.");
        }
    }

    @Override
    public void listar() {
        System.out.println(" LISTA DE CLIENTES ");
        if (clientes.isEmpty()) {
            System.out.println("Nenhum cliente registado.");
            return;
        }
        for (Cliente c : clientes) {
            System.out.println(c);
        }
    }

    public Cliente buscarPorId(int id) {
        for (Cliente c : clientes) {
            if (c.getId() == id) return c;
        }
        return null;
    }

    public void setClientes(ArrayList<Cliente> clientes) { this.clientes = clientes; }
    public ArrayList<Cliente> getClientes()              { return clientes; }
}
