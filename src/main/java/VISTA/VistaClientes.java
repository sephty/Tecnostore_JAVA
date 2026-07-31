package VISTA;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

import CONTROLADOR.ClienteController;
import MODELO.Cliente;
import UTILIDADES.Validador;
import java.util.ArrayList;

public class VistaClientes {

    Validador validador = new Validador();
    ClienteController clienteController = new ClienteController();

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
}
