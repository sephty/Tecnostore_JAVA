package UTILIDADES;

import DAO.ClienteDAO;
import DAO.CreditoDAO;
import MODELO.Cliente;
import MODELO.Credito;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class CreditoService {

    private final CreditoDAO creditoDAO = new CreditoDAO();
    private final ClienteDAO clienteDAO = new ClienteDAO();
    private final SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    public List<Credito> listarCreditosConSaldo() {
        return creditoDAO.listar().stream()
                .filter(cr -> cr.getSaldo_pendiente() > 0)
                .sorted(Comparator.comparingDouble(Credito::getSaldo_pendiente).reversed())
                .collect(Collectors.toList());
    }

    public void mostrarCreditosConSaldo() {
        List<Credito> lista = listarCreditosConSaldo();
        System.out.println("--- CLIENTES CON SALDO PENDIENTE ---");
        if (lista.isEmpty()) {
            System.out.println("No hay clientes con saldo pendiente.");
            return;
        }
        int i = 1;
        for (Credito cr : lista) {
            Cliente cli = cr.getCliente();
            String nombre = cli != null ? cli.getNombre() : "(cliente desconocido)";
            System.out.printf("%d. %s → $%,.0f%n", i, nombre, cr.getSaldo_pendiente());
            i++;
        }
    }

    public Optional<Credito> obtenerCreditoPorClienteId(int idCliente) {
        return Optional.ofNullable(creditoDAO.buscarPorClienteId(idCliente));
    }

    public boolean registrarAbono(int idCliente, double monto) {
        if (monto <= 0) {
            System.out.println("El monto del abono debe ser mayor a 0.");
            return false;
        }
        Credito credito = creditoDAO.buscarPorClienteId(idCliente);
        if (credito == null) {
            System.out.println("No existe credito para el cliente especificado.");
            return false;
        }
        double saldoPendienteActual = credito.getSaldo_pendiente();
        if (monto > saldoPendienteActual) {
            System.out.println("El monto del abono no puede ser mayor al saldo pendiente actual.");
            return false;
        }
        double nuevoSaldoPendiente = saldoPendienteActual - monto;
        Timestamp ahora = new Timestamp(System.currentTimeMillis());
        boolean actualizado = creditoDAO.actualizarSaldoPorClienteId(idCliente, nuevoSaldoPendiente, ahora);
        if (!actualizado) {
            System.out.println("Error al actualizar el saldo en la base de datos.");
            return false;
        }

        // Registrar en archivo
        try (FileWriter fw = new FileWriter("abonos_clientes.txt", true); PrintWriter pw = new PrintWriter(fw)) {
            Cliente cli = credito.getCliente();
            String nombre = cli != null ? cli.getNombre() : "(cliente desconocido)";
            String linea = String.format("%s | idCredito=%d | idCliente=%d | %s | abono=%,.0f | nuevoSaldo=%,.0f", sdf.format(ahora), credito.getId(), idCliente, nombre, monto, nuevoSaldoPendiente);
            pw.println(linea);
        } catch (IOException e) {
            System.out.println("Error al escribir el archivo de abonos: " + e.getMessage());
            return false;
        }

        System.out.println("Abono registrado exitosamente.");
        System.out.printf("Nuevo saldo de %s: $%,.0f%n", credito.getCliente() != null ? credito.getCliente().getNombre() : "(cliente)", nuevoSaldoPendiente);
        return true;
    }
}
