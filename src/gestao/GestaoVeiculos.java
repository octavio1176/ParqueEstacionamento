package gestao;

import ficheiros.GestorFicheiros;
import modelo.Cliente;
import modelo.Veiculo;
import java.util.ArrayList;
import java.util.Scanner;

public class GestaoVeiculos implements IGestao {

    private ArrayList<Veiculo> veiculos;
    private GestaoClientes gestaoClientes;
    private Scanner scanner;
    private GestorFicheiros gestor;

    public GestaoVeiculos(GestaoClientes gestaoClientes, GestorFicheiros gestor) {
        this.veiculos       = new ArrayList<>();
        this.gestaoClientes = gestaoClientes;
        this.scanner        = new Scanner(System.in);
        this.gestor         = gestor;
    }

    @Override
    public void criar() {
        System.out.println(" REGISTAR VEICULO ");
        System.out.print("ID do Cliente: ");
        int idCliente = Integer.parseInt(scanner.nextLine());
        Cliente cliente = gestaoClientes.buscarPorId(idCliente);
        if (cliente == null) {
            System.out.println("Cliente com ID " + idCliente + " nao encontrado!");
            return;
        }
        System.out.print("Matricula : ");
        String matricula = scanner.nextLine();
        if (buscarPorMatricula(matricula) != null) {
            System.out.println("Ja existe um veiculo com a matricula " + matricula + "!");
            return;
        }
        System.out.print("Marca     : ");
        String marca = scanner.nextLine();
        System.out.print("Modelo    : ");
        String modelo = scanner.nextLine();
        Veiculo veiculo = new Veiculo(matricula, marca, modelo, idCliente);
        veiculos.add(veiculo);
        gestor.gravarVeiculos(veiculos);
        System.out.println("Veiculo registado com sucesso! ID: " + veiculo.getId());
    }

    @Override
    public void actualizar() {
        System.out.println(" ACTUALIZAR VEICULO ");
        System.out.print("Insira o ID do veiculo: ");
        int id = Integer.parseInt(scanner.nextLine());
        Veiculo veiculo = buscarPorId(id);
        if (veiculo == null) {
            System.out.println("Veiculo com ID " + id + " nao encontrado!");
            return;
        }
        System.out.println(veiculo);
        System.out.print("Matricula [" + veiculo.getMatricula() + "]: ");
        String matricula = scanner.nextLine();
        if (!matricula.isEmpty()) veiculo.setMatricula(matricula);
        System.out.print("Marca [" + veiculo.getMarca() + "]: ");
        String marca = scanner.nextLine();
        if (!marca.isEmpty()) veiculo.setMarca(marca);
        System.out.print("Modelo [" + veiculo.getModelo() + "]: ");
        String modelo = scanner.nextLine();
        if (!modelo.isEmpty()) veiculo.setModelo(modelo);
        gestor.gravarVeiculos(veiculos);
        System.out.println("Veiculo actualizado com sucesso!");
    }

    @Override
    public void remover() {
        System.out.println("REMOVER VEICULO ");
        System.out.print("Insira o ID do veiculo: ");
        int id = Integer.parseInt(scanner.nextLine());
        Veiculo veiculo = buscarPorId(id);
        if (veiculo == null) {
            System.out.println("Veiculo com ID " + id + " nao encontrado!");
            return;
        }
        System.out.println(veiculo);
        System.out.print("Tem certeza? (S/N): ");
        String confirmacao = scanner.nextLine();
        if (confirmacao.equalsIgnoreCase("S")) {
            veiculos.remove(veiculo);
            gestor.gravarVeiculos(veiculos);
            System.out.println("Veiculo removido com sucesso!");
        } else {
            System.out.println("Operacao cancelada.");
        }
    }

    @Override
    public void listar() {
        System.out.println(" LISTA DE VEICULOS ");
        if (veiculos.isEmpty()) {
            System.out.println("Nenhum veiculo registado.");
            return;
        }
        for (Veiculo v : veiculos) {
            System.out.println(v);
        }
    }

    public Veiculo buscarPorId(int id) {
        for (Veiculo v : veiculos) {
            if (v.getId() == id) return v;
        }
        return null;
    }

    public Veiculo buscarPorMatricula(String matricula) {
        for (Veiculo v : veiculos) {
            if (v.getMatricula().equalsIgnoreCase(matricula)) return v;
        }
        return null;
    }

    public void setVeiculos(ArrayList<Veiculo> veiculos) {

        this.veiculos = veiculos;
    }
    public ArrayList<Veiculo> getVeiculos(){
        return veiculos;
    }
}
