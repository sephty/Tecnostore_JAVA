package VISTA;

import CONTROLADOR.CelularController;
import MODELO.Celular;
import UTILIDADES.Validador;
import java.util.ArrayList;

public class VistaCelulares {

    private Validador validador = new Validador();
    private CelularController celularController = new CelularController();

    public Celular buscarCelular(int id) {
        return celularController.buscar(id);
    }

    public boolean mostrarCelularesDisponibles() {
        ArrayList<Celular> disponibles = celularController.listar();
        System.out.println("CELULARES DISPONIBLES");
        if (disponibles.isEmpty()) {
            System.out.println("No hay celulares registrados.");
            return false;
        } else {
            System.out.printf("%-5s %-25s %-8s%n", "ID", "MARCA / MODELO", "STOCK");
            for (int i = 0; i < disponibles.size(); i++) {
                Celular c = disponibles.get(i);
                System.out.printf("%-5d %-25s %-8d%n", c.getId(), c.getMarca() + " " + c.getModelo(), c.getStock());
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
                                    GESTION DE CELULARES
                                    ------------------------------------------------------------
                                    1. Registrar celular
                                    2. Actualizar celular
                                    3. Eliminar celular
                                    4. Listar celulares
                                    5. Reponer stock
                                    6. Volver
                                    ------------------------------------------------------------
                                    """, 1, 6);
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
                    if (!mostrarCelularesDisponibles()) {
                        break;
                    }
                    int id = validador.validarEntero("ID del celular a actualizar:");
                    Celular actual = celularController.buscar(id);
                    if (actual == null) {
                        System.out.println("No existe un celular con ese ID.");
                        break;
                    }
                    String marca = validador.validarTextoOpcional("Nueva marca:", actual.getMarca());
                    String modelo = validador.validarTextoOpcional("Nuevo modelo:", actual.getModelo());
                    double precio = validador.validarDecimalOpcional("Nuevo precio:", actual.getPrecio());
                    int stock = validador.validarEnteroOpcional("Nuevo stock:", actual.getStock());
                    String so = validador.validarTextoOpcional("Nuevo sistema operativo:", actual.getSistema_operativo());
                    String gama = validador.validarTextoOpcional("Nueva gama (ALTA, MEDIA, BAJA):", actual.getGama());
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
                case 5: {
                    if (!mostrarCelularesDisponibles()) {
                        break;
                    }
                    int id = validador.validarEntero("ID del celular a reponer stock:");
                    Celular actual = celularController.buscar(id);
                    if (actual == null) {
                        System.out.println("No existe un celular con ese ID.");
                        break;
                    }
                    int cantidadAgregar = validador.validarEntero("Cantidad a agregar al stock actual (" + actual.getStock() + "):");
                    int nuevoStock = actual.getStock() + cantidadAgregar;
                    celularController.actualizar(id, actual.getMarca(), actual.getModelo(), actual.getPrecio(), nuevoStock, actual.getSistema_operativo(), actual.getGama());
                    System.out.println("Stock actualizado. Nuevo stock: " + nuevoStock);
                    break;
                }
            }
        } while (op != 6);
    }
}