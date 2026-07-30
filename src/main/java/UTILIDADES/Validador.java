package UTILIDADES;

import DAO.ClienteDAO;
import java.util.Scanner;
import java.util.regex.Pattern;

public class Validador {
    
    //validaciones unicas
    private static final Pattern EMAIL_REGEX = Pattern.compile("^[\\w.+-]+@[\\w-]+\\.[a-zA-Z]{2,}$");

    public boolean validarCorreo(String correo) {
        if (correo == null) {
            return false;
        }
        return EMAIL_REGEX.matcher(correo).matches();
    }

    public boolean identificacionUnica(String identificacion) {
        ClienteDAO cd = new ClienteDAO();
        return cd.buscarPorIdentificacion(identificacion) == null;
    }

    public boolean precioValido(double precio) {
        return precio > 0;
    }

    public boolean stockValido(int stock) {
        return stock >= 0;
    }
    
    // Validaciones generales
    public int validarEntero(String mensaje) {
        int dato = 0;
        do {
            try {
                System.out.println(mensaje);
                dato = new Scanner(System.in).nextInt();
            } catch (Exception e) {
                System.out.println("Error de ingreso de datos.");
            }
        } while (dato < 1);
        return dato;
    }

    public double validarDecimal(String mensaje) {
        double dato = 0;
        do {
            try {
                System.out.println(mensaje);
                dato = new Scanner(System.in).nextDouble();
            } catch (Exception e) {
                System.out.println("Error de ingreso de datos.");
            }
        } while (dato < 1);
        return dato;
    }

    public int validarEnteroRango(String mensaje, int minimo, int maximo) {
        int dato = 0;
        do {
            try {
                System.out.println(mensaje);
                dato = new Scanner(System.in).nextInt();
            } catch (Exception e) {
                System.out.println("Error de ingreso de datos.");
            }
        } while (dato < minimo || dato > maximo);
        return dato;
    }

    public String validarTexto(String mensaje) {
        String dato = "";
        do {
            try {
                System.out.println(mensaje);
                dato = new Scanner(System.in).nextLine();
            } catch (Exception e) {
                System.out.println("Error de ingreso de datos.");
            }
        } while (dato == null || dato.isBlank());
        return dato;
    }
}
