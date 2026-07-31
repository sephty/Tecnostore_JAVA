package DAO;

import CONTROLADOR.ConexionDB;
import MODELO.Cliente;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;
import java.util.ArrayList;

public class ClienteDAO {

    ConexionDB c = ConexionDB.getInstancia();

    public void crear(Cliente cli) {
        try (Connection con = c.conectar()) {
            String sql = "insert into clientes(nombre, identificacion, correo, telefono) values (?,?,?,?)";
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1, cli.getNombre());
            ps.setString(2, cli.getIdentificacion());
            ps.setString(3, cli.getCorreo());
            ps.setString(4, cli.getTelefono());
            ps.executeUpdate();
            System.out.println("Cliente creado correctamente!");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public void update(Cliente cli) {
        try (Connection con = c.conectar()) {
            String sql = "update clientes set nombre=?, identificacion=?, correo=?, telefono=? where id=?";
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1, cli.getNombre());
            ps.setString(2, cli.getIdentificacion());
            ps.setString(3, cli.getCorreo());
            ps.setString(4, cli.getTelefono());
            ps.setInt(5, cli.getId());
            ps.executeUpdate();
            System.out.println("Cliente actualizado correctamente!");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public ArrayList<Cliente> listar() {
        ArrayList<Cliente> respuesta = new ArrayList<>();
        try (Connection con = c.conectar()) {
            PreparedStatement ps = con.prepareStatement("select * from clientes");
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                respuesta.add(new Cliente(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getString(5)));
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return respuesta;
    }

    public Cliente buscar(int id) {
        Cliente cliente = null;
        try (Connection con = c.conectar()) {
            PreparedStatement ps = con.prepareStatement("select * from clientes where id=?");
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                cliente = new Cliente(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getString(5));
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return cliente;
    }

    public Cliente buscarPorIdentificacion(String identificacion) {
        Cliente cliente = null;
        try (Connection con = c.conectar()) {
            PreparedStatement ps = con.prepareStatement("select * from clientes where identificacion=?");
            ps.setString(1, identificacion);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                cliente = new Cliente(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getString(5));
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return cliente;
    }

    public void delete(Cliente cliente) {
        if (cliente == null) {
            System.out.println("NO EXISTE EL CLIENTE!");
            return;
        }
        try (Connection con = c.conectar()) {
            PreparedStatement ps = con.prepareStatement("delete from clientes where id=?");
            ps.setInt(1, cliente.getId());
            ps.executeUpdate();
            System.out.println("Cliente " + cliente.getNombre() + " eliminado con exito!");
        } catch (SQLIntegrityConstraintViolationException e) {
            System.out.println("No se puede eliminar " + cliente.getNombre() + ": tiene ventas registradas asociadas.");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
}