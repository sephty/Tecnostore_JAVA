package VISTA;

public class MenuPrincipal {

    public void Menu() {
        Validaciones v = new Validaciones();
        int op;
        do {
            op = v.validarEnteroRango("""
                                    BIENVENIDO A NUESTRO SISTEMA
                                    Digite la opcion a escoger:
                                    1. Categorias.
                                    2. Productos.
                                    3. Salir
                                    """, 1, 3);
            switch (op) {
                case 1:
                    MenuCategoria m = new MenuCategoria();
                    m.Menu();
                    break;
                case 2:
                    MenuProducto mp = new MenuProducto();
                    mp.Menu();
                    break;
                case 3:
                    System.out.println("Gracias por usar nuestra aplicacion.");
                    break;
            }
        } while (op != 3);
    }
}
