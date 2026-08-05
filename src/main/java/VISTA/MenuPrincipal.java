package VISTA;

import UTILIDADES.Validador;

public class MenuPrincipal {

    Validador validador = new Validador();
    VistaCelulares vistaCelulares = new VistaCelulares();
    VistaClientes vistaClientes = new VistaClientes();
    VistaVentas vistaVentas = new VistaVentas();
    VistaReportes vistaReportes = new VistaReportes();
    VistaCreditos vistaCreditos = new VistaCreditos();

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
                                    5. Creditos
                                    6. Salir
                                    ------------------------------------------------------------
                                    """, 1, 6);
            switch (op) {
                case 1:
                    vistaCelulares.menu();
                    break;
                case 2:
                    vistaClientes.menu();
                    break;
                case 3:
                    vistaVentas.menu();
                    break;
                case 4:
                    vistaReportes.menu();
                    break;
                case 5:
                    vistaCreditos.menu();
                    break;
                case 6:
                    System.out.println("Gracias por usar TecnoStore! Vuelva Pronto!");
                    break;
            }
        } while (op != 6);
    }
}