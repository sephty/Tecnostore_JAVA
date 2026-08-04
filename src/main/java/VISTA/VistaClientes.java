package VISTA;

import CONTROLADOR.ClienteController;
import MODELO.Cliente;
import UTILIDADES.Validador;
import java.util.ArrayList;

public class VistaClientes {

    private Validador validador = new Validador();
    private ClienteController clienteController = new ClienteController();

    public Cliente buscarCliente(int id) {
        return clienteController.buscar(id);
    }

    public boolean mostrarClientesDisponibles() {
        ArrayList<Cliente> disponibles = clienteController.listar();
        System.out.println("CLIENTES REGISTRADOS");
        if (disponibles.isEmpty()) {
            System.out.println("No hay clientes registrados.");
            return false;
        } else {
            System.out.printf("%-5s %-20s %-15s%n", "ID", "NOMBRE", "IDENTIFICACION");
            for (int i = 0; i < disponibles.size(); i++) {
                Cliente c = disponibles.get(i);
                System.out.printf("%-5d %-20s %-15s%n", c.getId(), c.getNombre(), c.getIdentificacion());
            }
        }
        System.out.println("------------------------------------------------------------");
        return true;
    }

    public void menu() {
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
                                    5. Buscar cliente
                                    6. Volver
                                    ------------------------------------------------------------
                                    """, 1, 6);
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
                    if (!mostrarClientesDisponibles()) {
                        break;
                    }
                    int id = validador.validarEntero("ID del cliente a actualizar:");
                    Cliente actual = clienteController.buscar(id);
                    if (actual == null) {
                        System.out.println("No existe un cliente con ese ID.");
                        break;
                    }
                    String nombre = validador.validarTextoOpcional("Nuevo nombre:", actual.getNombre());
                    String correo = validador.validarTextoOpcional("Nuevo correo:", actual.getCorreo());
                    String telefono = validador.validarTextoOpcional("Nuevo telefono:", actual.getTelefono());
                    actual.setNombre(nombre);
                    actual.setCorreo(correo);
                    actual.setTelefono(telefono);
                    clienteController.actualizar(actual);
                    break;
                }
                case 3: {
                    if (!mostrarClientesDisponibles()) {
                        break;
                    }
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
                case 5: {
                    int id = validador.validarEntero("ID del cliente a buscar:");
                    Cliente cliente = clienteController.buscar(id);
                    System.out.println("------------------------------------------------------------");
                    if (cliente == null) {
                        System.out.println("No existe un cliente con ese ID.");
                    } else {
                        System.out.println(cliente);
                    }
                    System.out.println("------------------------------------------------------------");
                    break;
                }
            }
        } while (op != 6);
    }
}