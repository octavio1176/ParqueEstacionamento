package modelo;

public class Autenticacao {

    private static final String UTILIZADOR = "admin";
    private static final String PASSWORD   = "1234";

    public static boolean autenticar(String utilizador, String password) {
        return UTILIZADOR.equals(utilizador) && PASSWORD.equals(password);
    }
}
