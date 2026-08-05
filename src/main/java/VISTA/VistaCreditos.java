package VISTA;

import UTILIDADES.CreditoService;
import UTILIDADES.Validador;
import java.util.Optional;
import MODELO.Credito;

public class VistaCreditos {

    private final Validador validador = new Validador();
    private final CreditoService creditoService = new CreditoService();

    public void menu() {
        creditoService.mostrarCreditosConSaldo();
        int id = validador.validarEntero("\nIngrese el ID del cliente para registrar abono:");
        double monto = validador.validarDecimal("Monto del abono:");
        Optional<Credito> opt = creditoService.obtenerCreditoPorClienteId(id);
        if (opt.isEmpty()) {
            System.out.println("Credito no encontrado para el cliente.");
            return;
        }
        boolean ok = creditoService.registrarAbono(id, monto);
        if (ok) {
            System.out.println("Transacción guardada en abonos_clientes.txt");
        }
    }
}
