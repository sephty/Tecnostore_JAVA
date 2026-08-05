package VISTA;

import UTILIDADES.CreditoService;
import UTILIDADES.Validador;
import java.util.Optional;
import MODELO.Credito;

public class VistaCreditos {

    private final Validador validador = new Validador();
    private final CreditoService creditoService = new CreditoService();

    public void menu() {
        int op;
        do {
            op = validador.validarEnteroRango("""
                    -----------------------------
                    GESTION DE CREDITOS
                    -----------------------------
                    1. Mostrar clientes con saldo pendiente
                    2. Registrar nuevo credito
                    3. Registrar abono
                    4. Volver
                    -----------------------------
                    """, 1, 4);
            switch (op) {
                case 1:
                    creditoService.mostrarCreditosConSaldo();
                    break;
                case 2:
                    int idCliente = validador.validarEntero("Ingrese el ID del cliente para crear credito:");
                    double saldoInicial = validador.validarDecimal("Ingrese el saldo pendiente inicial:");
                    creditoService.crearCredito(idCliente, saldoInicial);
                    break;
                case 3:
                    creditoService.mostrarCreditosConSaldo();
                    int id = validador.validarEntero("\nIngrese el ID del cliente para registrar abono:");
                    double monto = validador.validarDecimal("Monto del abono:");
                    Optional<Credito> opt = creditoService.obtenerCreditoPorClienteId(id);
                    if (opt.isEmpty()) {
                        System.out.println("Credito no encontrado para el cliente.");
                        break;
                    }
                    boolean ok = creditoService.registrarAbono(id, monto);
                    if (ok) {
                        System.out.println("Transacción guardada en abonos_clientes.txt");
                    }
                    break;
                case 4:
                    break;
            }
        } while (op != 4);
    }
}
