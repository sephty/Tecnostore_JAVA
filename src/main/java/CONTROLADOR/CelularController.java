package CONTROLADOR;

import DAO.CelularDAO;
import MODELO.Celular;
import UTILIDADES.Validador;
import java.util.ArrayList;

public class CelularController {

    CelularDAO celularDAO = new CelularDAO();
    Validador validador = new Validador();

    public void registrar(Celular cl) {
        if (!validador.precioValido(cl.getPrecio())) {
            System.out.println("El precio debe ser mayor a 0.");
            return;
        }
        if (!validador.stockValido(cl.getStock())) {
            System.out.println("El stock no puede ser negativo.");
            return;
        }
        celularDAO.crear(cl);
    }

    public void actualizar(Celular cl) {
        if (!validador.precioValido(cl.getPrecio())) {
            System.out.println("El precio debe ser mayor a 0.");
            return;
        }
        if (!validador.stockValido(cl.getStock())) {
            System.out.println("El stock no puede ser negativo.");
            return;
        }
        celularDAO.update(cl);
    }

    public void eliminar(int id) {
        Celular cl = celularDAO.buscar(id);
        celularDAO.delete(cl);
    }

    public ArrayList<Celular> listar() {
        return celularDAO.listar();
    }

    public Celular buscar(int id) {
        return celularDAO.buscar(id);
    }
}
