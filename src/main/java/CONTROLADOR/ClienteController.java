package CONTROLADOR;

import DAO.ClienteDAO;
import MODELO.Cliente;
import UTILIDADES.Validador;
import java.util.ArrayList;

public class ClienteController {

    ClienteDAO clienteDAO = new ClienteDAO();
    Validador validador = new Validador();

    public void registrar(Cliente cli) {
        if (!validador.validarCorreo(cli.getCorreo())) {
            System.out.println("El correo no tiene un formato valido.");
            return;
        }
        if (!validador.identificacionUnica(cli.getIdentificacion())) {
            System.out.println("Ya existe un cliente con esa identificacion.");
            return;
        }
        clienteDAO.crear(cli);
    }

    public void actualizar(Cliente cli) {
        if (!validador.validarCorreo(cli.getCorreo())) {
            System.out.println("El correo no tiene un formato valido.");
            return;
        }
        clienteDAO.update(cli);
    }

    public void eliminar(int id) {
        Cliente cli = clienteDAO.buscar(id);
        clienteDAO.delete(cli);
    }

    public ArrayList<Cliente> listar() {
        return clienteDAO.listar();
    }

    public Cliente buscar(int id) {
        return clienteDAO.buscar(id);
    }
}
