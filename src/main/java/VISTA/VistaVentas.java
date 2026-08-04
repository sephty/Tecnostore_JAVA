package VISTA;

import CONTROLADOR.ClienteController;
import CONTROLADOR.VentaController;
import MODELO.Celular;
import MODELO.Cliente;
import MODELO.ItemVenta;
import MODELO.Venta;
import UTILIDADES.ArchivoUtils;
import UTILIDADES.Validador;
import java.util.ArrayList;

public class VistaVentas {

    Validador validador = new Validador();
    ClienteController clienteController = new ClienteController();
    VentaController ventaController = new VentaController();
    ArchivoUtils archivoUtils = new ArchivoUtils();
    VistaCelulares vistaCelulares = new VistaCelulares();
    VistaClientes vistaClientes = new VistaClientes();

    public void menu() {
        int op;
        do {
            op = validador.validarEnteroRango("""
                                    ------------------------------------------------------------
                                    GESTION DE VENTAS
                                    ------------------------------------------------------------
                                    1. Registrar venta
                                    2. Listar ventas
                                    3. Volver
                                    ------------------------------------------------------------
                                    """, 1, 3);
            switch (op) {
                case 1: {
                    if (!vistaClientes.mostrarClientesDisponibles()) {
                        break;
                    }
                    int idCliente = validador.validarEntero("ID del cliente:");
                    Cliente cliente = clienteController.buscar(idCliente);
                    if (cliente == null) {
                        System.out.println("No existe un cliente con ese ID.");
                        break;
                    }

                    if (!vistaCelulares.mostrarCelularesDisponibles()) {
                        break;
                    }

                    ArrayList<ItemVenta> items = new ArrayList<>();
                    String continuar = null;
                    do {
                        System.out.println();
                        int idCelular = validador.validarEntero("ID del celular:");
                        Celular celular = vistaCelulares.buscarCelular(idCelular);
                        if (celular == null) {
                            System.out.println("No existe un celular con ese ID.");
                            continue;
                        }
                        int cantidad = validador.validarEntero("Cantidad:");
                        items.add(new ItemVenta(celular, cantidad, 0));
                        continuar = validador.validarTexto("Agregar otro celular? (S/N):");
                    } while (continuar.equalsIgnoreCase("S"));

                    Venta venta = new Venta(cliente, items);
                    ventaController.registrarVenta(venta);
                    archivoUtils.generarReporteVentas();
                    break;
                }
                case 2: {
                    ArrayList<Venta> ventas = ventaController.listar();
                    System.out.println("------------------------------------------------------------");
                    if (ventas.isEmpty()) {
                        System.out.println("No hay ventas registradas.");
                    } else {
                        for (int i = 0; i < ventas.size(); i++) {
                            Venta v = ventas.get(i);
                            System.out.println("Venta #" + v.getId() + " - " + v.getCliente().getNombre() + " - Total: $ " + v.getTotal());
                        }
                    }
                    System.out.println("------------------------------------------------------------");
                    break;
                }
            }
        } while (op != 3);
    }
}