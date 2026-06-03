package gestao;

import ficheiros.GestorFicheiros;
import modelo.Lugar;
import java.util.ArrayList;
import java.util.Scanner;

public class GestaoLugares implements IGestao {

    private ArrayList<Lugar> lugares;
    private Scanner scanner;
    private GestorFicheiros gestor;
    private static final int PONTO_ENCOMENDA = 5;

    public GestaoLugares(GestorFicheiros gestor) {
        this.lugares = new ArrayList<>();
        this.scanner = new Scanner(System.in);
        this.gestor  = gestor;
    }

    @Override
    public void criar() {
        System.out.println(" REGISTAR LUGAR ");
        System.out.print("Codigo do lugar (ex: A1, B2): ");
        String codigo = scanner.nextLine().toUpperCase();
        if (buscarPorCodigo(codigo) != null) {
            System.out.println("Ja existe um lugar com o codigo " + codigo + "!");
            return;
        }
        Lugar lugar = new Lugar(codigo);
        lugares.add(lugar);
        gestor.gravarLugares(lugares);
        System.out.println("Lugar " + codigo + " registado com sucesso!");
    }

    @Override
    public void actualizar() {
        System.out.println(" ACTUALIZAR LUGAR ");
        System.out.print("Codigo do lugar: ");
        String codigo = scanner.nextLine().toUpperCase();
        Lugar lugar = buscarPorCodigo(codigo);
        if (lugar == null) {
            System.out.println("Lugar " + codigo + " nao encontrado!");
            return;
        }
        System.out.println(lugar);
        System.out.print("Novo codigo [" + lugar.getCodigo() + "]: ");
        String novoCodigo = scanner.nextLine().toUpperCase();
        if (!novoCodigo.isEmpty()) lugar.setCodigo(novoCodigo);
        gestor.gravarLugares(lugares);
        System.out.println("Lugar actualizado com sucesso!");
    }

    @Override
    public void remover() {
        System.out.println(" REMOVER LUGAR ");
        System.out.print("Codigo do lugar: ");
        String codigo = scanner.nextLine().toUpperCase();
        Lugar lugar = buscarPorCodigo(codigo);
        if (lugar == null) {
            System.out.println("Lugar " + codigo + " nao encontrado!");
            return;
        }
        if (lugar.isOcupado()) {
            System.out.println("Nao e possivel remover o lugar " + codigo + " porque esta ocupado!");
            return;
        }
        System.out.println(lugar);
        System.out.print("Tem certeza? (S/N): ");
        String confirmacao = scanner.nextLine();
        if (confirmacao.equalsIgnoreCase("S")) {
            lugares.remove(lugar);
            gestor.gravarLugares(lugares);
            System.out.println("Lugar removido com sucesso!");
        } else {
            System.out.println("Operacao cancelada.");
        }
    }

    @Override
    public void listar() {
        System.out.println(" LISTA DE LUGARES ");
        if (lugares.isEmpty()) {
            System.out.println("Nenhum lugar registado.");
            return;
        }
        for (Lugar l : lugares) {
            System.out.println(l);
        }
        System.out.println("Total    : " + lugares.size());
        System.out.println("Ocupados : " + contarOcupados());
        System.out.println("Livres   : " + contarLivres());
        if (contarLivres() <= PONTO_ENCOMENDA) {
            System.out.println("ALERTA: Apenas " + contarLivres() + " lugares livres!");
        }
    }

    public Lugar buscarPorCodigo(String codigo) {
        for (Lugar l : lugares) {
            if (l.getCodigo().equalsIgnoreCase(codigo)) return l;
        }
        return null;
    }

    public Lugar buscarPrimeiroLivre() {
        for (Lugar l : lugares) {
            if (!l.isOcupado()) return l;
        }
        return null;
    }

    public int contarOcupados() {
        int count = 0;
        for (Lugar l : lugares) if (l.isOcupado()) count++;
        return count;
    }

    public int contarLivres() {
        int count = 0;
        for (Lugar l : lugares) if (!l.isOcupado()) count++;
        return count;
    }

    public void setLugares(ArrayList<Lugar> lugares) { this.lugares = lugares; }
    public ArrayList<Lugar> getLugares()             { return lugares; }
}
