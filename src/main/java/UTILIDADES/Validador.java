package UTILIDADES;

import DAO.ClienteDAO;
import java.util.Scanner;
import java.util.regex.Pattern;

public class Validador {

    private static final Scanner sc = new Scanner(System.in);
    private static final Pattern EMAIL_REGEX = Pattern.compile("^[\\w.+-]+@[\\w-]+\\.[a-zA-Z]{2,}$");

    //validaciones unicas
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

    // Validaciones opcionales
    public String validarTextoOpcional(String mensaje, String valorActual) {
        System.out.println(mensaje + " (Enter para mantener: " + valorActual + ")");
        String dato = sc.nextLine();
        if (dato == null || dato.isBlank()) {
            return valorActual;
        }
        return dato;
    }

    public double validarDecimalOpcional(String mensaje, double valorActual) {
        System.out.println(mensaje + " (Enter para mantener: " + valorActual + ")");
        String linea = sc.nextLine();
        if (linea == null || linea.isBlank()) {
            return valorActual;
        }
        try {
            return Double.parseDouble(linea);
        } catch (NumberFormatException e) {
            System.out.println("Valor invalido, se mantiene el actual.");
            return valorActual;
        }
    }

    public int validarEnteroOpcional(String mensaje, int valorActual) {
        System.out.println(mensaje + " (Enter para mantener: " + valorActual + ")");
        String linea = sc.nextLine();
        if (linea == null || linea.isBlank()) {
            return valorActual;
        }
        try {
            return Integer.parseInt(linea);
        } catch (NumberFormatException e) {
            System.out.println("Valor invalido, se mantiene el actual.");
            return valorActual;
        }
    }

    // Validaciones generales
    public int validarEntero(String mensaje) {
        int dato = 0;
        boolean valido = false;
        do {
            System.out.println(mensaje);
            String linea = sc.nextLine();
            try {
                dato = Integer.parseInt(linea.trim());
                valido = dato >= 1;
                if (!valido) {
                    System.out.println("El valor debe ser mayor o igual a 1.");
                }
            } catch (NumberFormatException e) {
                System.out.println("Error de ingreso de datos.");
            }
        } while (!valido);
        return dato;
    }

    public double validarDecimal(String mensaje) {
        double dato = 0;
        boolean valido = false;
        do {
            System.out.println(mensaje);
            String linea = sc.nextLine();
            try {
                dato = Double.parseDouble(linea.trim());
                valido = dato >= 1;
                if (!valido) {
                    System.out.println("El valor debe ser mayor o igual a 1.");
                }
            } catch (NumberFormatException e) {
                System.out.println("Error de ingreso de datos.");
            }
        } while (!valido);
        return dato;
    }

    public int validarEnteroRango(String mensaje, int minimo, int maximo) {
        int dato = 0;
        boolean valido = false;
        do {
            System.out.println(mensaje);
            String linea = sc.nextLine();
            try {
                dato = Integer.parseInt(linea.trim());
                valido = dato >= minimo && dato <= maximo;
                if (!valido) {
                    System.out.println("El valor debe estar entre " + minimo + " y " + maximo + ".");
                }
            } catch (NumberFormatException e) {
                System.out.println("Error de ingreso de datos.");
            }
        } while (!valido);
        return dato;
    }

    public String validarTexto(String mensaje) {
        String dato = "";
        do {
            System.out.println(mensaje);
            dato = sc.nextLine();
        } while (dato == null || dato.isBlank());
        return dato;
    }
}