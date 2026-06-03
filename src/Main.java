import ficheiros.GestorFicheiros;
import gestao.*;
import modelo.Autenticacao;
import java.util.Scanner;

public class Main {

    static Scanner scanner               = new Scanner(System.in);
    static GestorFicheiros gestor        = new GestorFicheiros();
    static GestaoClientes gestaoClientes = new GestaoClientes(gestor);
    static GestaoVeiculos gestaoVeiculos = new GestaoVeiculos(gestaoClientes, gestor);
    static GestaoLugares  gestaoLugares  = new GestaoLugares(gestor);
    static GestaoTickets  gestaoTickets  = new GestaoTickets(gestaoVeiculos, gestaoLugares, gestor);
    static Relatorios     relatorios     = new Relatorios(gestaoTickets, gestaoVeiculos, gestaoLugares, gestaoClientes);

    public static void main(String[] args) {
        gestaoClientes.setClientes(gestor.carregarClientes());
        gestaoVeiculos.setVeiculos(gestor.carregarVeiculos());
        gestaoLugares.setLugares(gestor.carregarLugares());
        gestaoTickets.setTickets(gestor.carregarTickets());

        boolean correr = true;
        while (correr) {
            System.out.println("   SISTEMA DE GESTAO DE PARQUE          ");
            System.out.println("1. Entrar no sistema");
            System.out.println("0. Sair");
            System.out.print("Escolha: ");
            String opcao = scanner.nextLine();
            switch (opcao) {
                case "1": login(); break;
                case "0":
                    System.out.println("Sistema encerrado. Ate logo!");
                    correr = false;
                    break;
                default: System.out.println("Opcao invalida! Tente novamente.");
            }
        }
    }

    private static void login() {
        System.out.println(" LOGIN ");
        System.out.print("Utilizador : ");
        String utilizador = scanner.nextLine();
        System.out.print("Senha      : ");
        String senha = scanner.nextLine();
        if (Autenticacao.autenticar(utilizador, senha)) {
            System.out.println("Bem vindo, " + utilizador + "!");
            menuPrincipal();
        } else {
            System.out.println("Credenciais incorrectas! Tente novamente.");
        }
    }

    private static void menuPrincipal() {
        boolean correr = true;
        while (correr) {
            System.out.println("    MENU PRINCIPAL   ");
            System.out.println("1. Clientes");
            System.out.println("2. Veiculos");
            System.out.println("3. Lugares");
            System.out.println("4. Tickets");
            System.out.println("5. Relatorios");
            System.out.println("0. Sair");
            System.out.print("Escolha: ");
            String opcao = scanner.nextLine();
            switch (opcao) {
                case "1": menuClientes();   break;
                case "2": menuVeiculos();   break;
                case "3": menuLugares();    break;
                case "4": menuTickets();    break;
                case "5": menuRelatorios(); break;
                case "0": correr = false;   break;
                default: System.out.println("Opcao invalida! Tente novamente.");
            }
        }
    }

    private static void menuClientes() {
        boolean correr = true;
        while (correr) {
            System.out.println(" CLIENTES ");
            System.out.println("1. Registar Cliente");
            System.out.println("2. Actualizar Cliente");
            System.out.println("3. Remover Cliente");
            System.out.println("4. Listar Clientes");
            System.out.println("0. Voltar");
            System.out.println("00. Sair");
            System.out.print("Escolha: ");
            String opcao = scanner.nextLine();
            switch (opcao) {
                case "1":  gestaoClientes.criar();      break;
                case "2":  gestaoClientes.actualizar(); break;
                case "3":  gestaoClientes.remover();    break;
                case "4":  gestaoClientes.listar();     break;
                case "0":  correr = false;              break;
                case "00": sairDoSistema();             return;
                default:   System.out.println("Opcao invalida! Tente novamente.");
            }
        }
    }

    private static void menuVeiculos() {
        boolean correr = true;
        while (correr) {
            System.out.println(" VEICULOS ");
            System.out.println("1. Registar Veiculo");
            System.out.println("2. Actualizar Veiculo");
            System.out.println("3. Remover Veiculo");
            System.out.println("4. Listar Veiculos");
            System.out.println("0. Voltar");
            System.out.println("00. Sair");
            System.out.print("Escolha: ");
            String opcao = scanner.nextLine();
            switch (opcao) {
                case "1":  gestaoVeiculos.criar();      break;
                case "2":  gestaoVeiculos.actualizar(); break;
                case "3":  gestaoVeiculos.remover();    break;
                case "4":  gestaoVeiculos.listar();     break;
                case "0":  correr = false;              break;
                case "00": sairDoSistema();             return;
                default:   System.out.println("Opcao invalida! Tente novamente.");
            }
        }
    }

    private static void menuLugares() {
        boolean correr = true;
        while (correr) {
            System.out.println("  LUGARES ");
            System.out.println("1. Registar Lugar");
            System.out.println("2. Actualizar Lugar");
            System.out.println("3. Remover Lugar");
            System.out.println("4. Listar Lugares");
            System.out.println("0. Voltar");
            System.out.println("00. Sair");
            System.out.print("Escolha: ");
            String opcao = scanner.nextLine();
            switch (opcao) {
                case "1":  gestaoLugares.criar();      break;
                case "2":  gestaoLugares.actualizar(); break;
                case "3":  gestaoLugares.remover();    break;
                case "4":  gestaoLugares.listar();     break;
                case "0":  correr = false;             break;
                case "00": sairDoSistema();            return;
                default:   System.out.println("Opcao invalida! Tente novamente.");
            }
        }
    }

    private static void menuTickets() {
        boolean correr = true;
        while (correr) {
            System.out.println(" TICKETS ");
            System.out.println("1. Registar Entrada");
            System.out.println("2. Registar Saida");
            System.out.println("3. Conta Corrente do Cliente");
            System.out.println("4. Listar Tickets");
            System.out.println("0. Voltar");
            System.out.println("00. Sair");
            System.out.print("Escolha: ");
            String opcao = scanner.nextLine();
            switch (opcao) {
                case "1":  gestaoTickets.registarEntrada();
                break;
                case "2":  gestaoTickets.registarSaida();
                break;
                case "3":  gestaoTickets.contaCorrenteCliente();
                break;
                case "4":  gestaoTickets.listar();
                break;
                case "0":  correr = false;
                break;
                case "00": sairDoSistema();
                return;
                default:   System.out.println("Opcao invalida! Tente novamente.");
            }
        }
    }

    private static void menuRelatorios() {
        boolean correr = true;
        while (correr) {
            relatorios.menuRelatorios();
            String opcao = scanner.nextLine();
            switch (opcao) {
                case "1":  relatorios.relatorioLugares();      break;
                case "2":  relatorios.relatorioReceita();      break;
                case "3":  relatorios.relatorioTop5Veiculos(); break;
                case "0":  correr = false;                     break;
                case "00": sairDoSistema();                    return;
                default:   System.out.println("Opcao invalida! Tente novamente.");
            }
        }
    }

    private static void sairDoSistema() {
        System.out.println("Sistema encerrado. Ate logo!");
        System.exit(0);
    }
}
