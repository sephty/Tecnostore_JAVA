package VISTA;

import CONTROLADOR.CelularController;
import CONTROLADOR.ClienteController;
import CONTROLADOR.VentaController;
import MODELO.Celular;
import MODELO.Cliente;
import MODELO.ItemVenta;
import MODELO.Venta;
import UTILIDADES.ArchivoUtils;
import UTILIDADES.ReporteUtils;
import UTILIDADES.Validador;
import java.util.ArrayList;

public class MenuPrincipal {

    Validador validador = new Validador();
    CelularController celularController = new CelularController();
    ClienteController clienteController = new ClienteController();
    VentaController ventaController = new VentaController();
    ReporteUtils reporteUtils = new ReporteUtils();
    ArchivoUtils archivoUtils = new ArchivoUtils();

    public void Menu() {
        int op;
        do {
            op = validador.validarEnteroRango("""
                                    ------------------------------------------------------------
                                    TECNOSTORE - MENU PRINCIPAL
                                    ------------------------------------------------------------
                                    1. Celulares
                                    2. Clientes
                                    3. Ventas
                                    4. Reportes
                                    5. Salir
                                    ------------------------------------------------------------
                                    """, 1, 5);
            switch (op) {
                case 1:
                    menuCelulares();
                    break;
                case 2:
                    menuClientes();
                    break;
                case 3:
                    menuVentas();
                    break;
                case 4:
                    menuReportes();
                    break;
                case 5:
                    System.out.println("Gracias por usar TecnoStore.");
                    break;
            }
        } while (op != 5);
    }

    private void menuCelulares() {
        int op;
        do {
            op = validador.validarEnteroRango("""
                                    ------------------------------------------------------------
                                    GESTION DE CELULARES
                                    ------------------------------------------------------------
                                    1. Registrar celular
                                    2. Actualizar celular
                                    3. Eliminar celular
                                    4. Listar celulares
                                    5. Volver
                                    ------------------------------------------------------------
                                    """, 1, 5);
            switch (op) {
                case 1: {
                    String marca = validador.validarTexto("Marca:");
                    String modelo = validador.validarTexto("Modelo:");
                    double precio = validador.validarDecimal("Precio:");
                    int stock = validador.validarEntero("Stock:");
                    String so = validador.validarTexto("Sistema operativo:");
                    String gama = validador.validarTexto("Gama (ALTA, MEDIA, BAJA):");
                    celularController.registrar(marca, modelo, precio, stock, so, gama);
                    break;
                }
                case 2: {
                    int id = validador.validarEntero("ID del celular a actualizar:");
                    Celular actual = celularController.buscar(id);
                    if (actual == null) {
                        System.out.println("No existe un celular con ese ID.");
                        break;
                    }
                    String marca = validador.validarTexto("Nueva marca:");
                    String modelo = validador.validarTexto("Nuevo modelo:");
                    double precio = validador.validarDecimal("Nuevo precio:");
                    int stock = validador.validarEntero("Nuevo stock:");
                    String so = validador.validarTexto("Nuevo sistema operativo:");
                    String gama = validador.validarTexto("Nueva gama (ALTA, MEDIA, BAJA):");
                    celularController.actualizar(id, marca, modelo, precio, stock, so, gama);
                    break;
                }
                case 3: {
                    int id = validador.validarEntero("ID del celular a eliminar:");
                    celularController.eliminar(id);
                    break;
                }
                case 4: {
                    ArrayList<Celular> celulares = celularController.listar();
                    System.out.println("------------------------------------------------------------");
                    if (celulares.isEmpty()) {
                        System.out.println("No hay celulares registrados.");
                    } else {
                        for (int i = 0; i < celulares.size(); i++) {
                            System.out.println(celulares.get(i));
                        }
                    }
                    System.out.println("------------------------------------------------------------");
                    break;
                }
            }
        } while (op != 5);
    }

    private void menuClientes() {
        int op;
        do {
            op = validador.validarEnteroRango("""
                                    ------------------------------------------------------------
                                    GESTION DE CLIENTES
                                    ------------------------------------------------------------
                                    1. Registrar cliente
                                    2. Actualizar cliente
                                    3. Eliminar cliente
                                    4. Listar clientes
                                    5. Volver
                                    ------------------------------------------------------------
                                    """, 1, 5);
            switch (op) {
                case 1: {
                    String nombre = validador.validarTexto("Nombre:");
                    String identificacion = validador.validarTexto("Identificacion:");
                    String correo = validador.validarTexto("Correo:");
                    String telefono = validador.validarTexto("Telefono:");
                    Cliente cli = new Cliente(0, nombre, identificacion, correo, telefono);
                    clienteController.registrar(cli);
                    break;
                }
                case 2: {
                    int id = validador.validarEntero("ID del cliente a actualizar:");
                    Cliente cli = clienteController.buscar(id);
                    if (cli == null) {
                        System.out.println("No existe un cliente con ese ID.");
                        break;
                    }
                    cli.setNombre(validador.validarTexto("Nuevo nombre:"));
                    cli.setCorreo(validador.validarTexto("Nuevo correo:"));
                    cli.setTelefono(validador.validarTexto("Nuevo telefono:"));
                    clienteController.actualizar(cli);
                    break;
                }
                case 3: {
                    int id = validador.validarEntero("ID del cliente a eliminar:");
                    clienteController.eliminar(id);
                    break;
                }
                case 4: {
                    ArrayList<Cliente> clientes = clienteController.listar();
                    System.out.println("------------------------------------------------------------");
                    if (clientes.isEmpty()) {
                        System.out.println("No hay clientes registrados.");
                    } else {
                        for (int i = 0; i < clientes.size(); i++) {
                            System.out.println(clientes.get(i));
                        }
                    }
                    System.out.println("------------------------------------------------------------");
                    break;
                }
            }
        } while (op != 5);
    }

    private void menuVentas() {
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
                    int idCliente = validador.validarEntero("ID del cliente:");
                    Cliente cliente = clienteController.buscar(idCliente);
                    if (cliente == null) {
                        System.out.println("No existe un cliente con ese ID.");
                        break;
                    }

                    ArrayList<ItemVenta> items = new ArrayList<>();
                    String continuar;
                    do {
                        int idCelular = validador.validarEntero("ID del celular:");
                        Celular celular = celularController.buscar(idCelular);
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

    private void menuReportes() {
        int op;
        do {
            op = validador.validarEnteroRango("""
                                    ------------------------------------------------------------
                                    REPORTES
                                    ------------------------------------------------------------
                                    1. Stock bajo
                                    2. Top 3 mas vendidos
                                    3. Ventas totales por mes
                                    4. Generar archivo reporte_ventas.txt
                                    5. Volver
                                    ------------------------------------------------------------
                                    """, 1, 5);
            switch (op) {
                case 1:
                    reporteUtils.mostrarStockBajo();
                    break;
                case 2:
                    reporteUtils.mostrarTopVendidos();
                    break;
                case 3:
                    reporteUtils.mostrarVentasPorMes();
                    break;
                case 4:
                    archivoUtils.generarReporteVentas();
                    break;
            }
        } while (op != 5);
    }
}
