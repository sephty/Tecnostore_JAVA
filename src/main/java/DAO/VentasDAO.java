package DAO;

import CONTROLADOR.ConexionDB;
import MODELO.Celular;
import MODELO.Cliente;
import MODELO.ItemVenta;
import MODELO.Venta;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;

public class VentasDAO {

    ConexionDB c = ConexionDB.getInstancia();

    public void crear(Venta venta) {
        try (Connection con = c.conectar()) {
            String sql = "insert into ventas(id_cliente, fecha, total) values (?,?,?)";
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setInt(1, venta.getCliente().getId());
            ps.setTimestamp(2, new Timestamp(System.currentTimeMillis()));
            ps.setDouble(3, venta.getTotal());
            ps.executeUpdate();
            System.out.println("Venta registrada correctamente!");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public int obtenerUltimoId() {
        int id = 0;
        try (Connection con = c.conectar()) {
            PreparedStatement ps = con.prepareStatement("select max(id) from ventas");
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                id = rs.getInt(1);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return id;
    }

    public void crearDetalle(int idVenta, ItemVenta item) {
        try (Connection con = c.conectar()) {
            String sql = "insert into detalle_ventas(id_venta, id_celular, cantidad, subtotal) values (?,?,?,?)";
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setInt(1, idVenta);
            ps.setInt(2, item.getCelular().getId());
            ps.setInt(3, item.getCantidad());
            ps.setDouble(4, item.getSubtotal());
            ps.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public ArrayList<Venta> listar() {
        ArrayList<Venta> respuesta = new ArrayList<>();
        try (Connection con = c.conectar()) {
            String sql = "select v.id, v.id_cliente, v.fecha, v.total, cl.nombre, cl.identificacion, cl.correo, cl.telefono "
                    + "from ventas v inner join clientes cl on v.id_cliente = cl.id";
            PreparedStatement ps = con.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Cliente cliente = new Cliente(rs.getInt(2), rs.getString(5), rs.getString(6), rs.getString(7), rs.getString(8));
                respuesta.add(new Venta(rs.getInt(1), cliente, rs.getTimestamp(3), rs.getDouble(4)));
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return respuesta;
    }

    public ArrayList<ItemVenta> listarDetalles(int idVenta) {
        ArrayList<ItemVenta> respuesta = new ArrayList<>();
        try (Connection con = c.conectar()) {
            String sql = "select dv.id, dv.id_venta, dv.cantidad, dv.subtotal, c.id, c.marca, c.modelo, c.sistema_operativo, c.gama, c.precio, c.stock "
                    + "from detalle_ventas dv inner join celulares c on dv.id_celular = c.id where dv.id_venta = ?";
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setInt(1, idVenta);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Celular celular = new Celular(rs.getInt(5), rs.getString(6), rs.getString(7), rs.getDouble(10), rs.getInt(11), rs.getString(8), rs.getString(9));
                respuesta.add(new ItemVenta(rs.getInt(1), rs.getInt(2), celular, rs.getInt(3), rs.getDouble(4)));
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return respuesta;
    }

    public ArrayList<ItemVenta> listarTodosDetalles() {
        ArrayList<ItemVenta> respuesta = new ArrayList<>();
        try (Connection con = c.conectar()) {
            String sql = "select dv.id, dv.id_venta, dv.cantidad, dv.subtotal, c.id, c.marca, c.modelo, c.sistema_operativo, c.gama, c.precio, c.stock "
                    + "from detalle_ventas dv inner join celulares c on dv.id_celular = c.id";
            PreparedStatement ps = con.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Celular celular = new Celular(rs.getInt(5), rs.getString(6), rs.getString(7), rs.getDouble(10), rs.getInt(11), rs.getString(8), rs.getString(9));
                respuesta.add(new ItemVenta(rs.getInt(1), rs.getInt(2), celular, rs.getInt(3), rs.getDouble(4)));
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return respuesta;
    }

    public Venta buscar(int id) {
        Venta venta = null;
        try (Connection con = c.conectar()) {
            String sql = "select v.id, v.id_cliente, v.fecha, v.total, cl.nombre, cl.identificacion, cl.correo, cl.telefono "
                    + "from ventas v inner join clientes cl on v.id_cliente = cl.id where v.id = ?";
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                Cliente cliente = new Cliente(rs.getInt(2), rs.getString(5), rs.getString(6), rs.getString(7), rs.getString(8));
                venta = new Venta(rs.getInt(1), cliente, rs.getTimestamp(3), rs.getDouble(4));
                venta.setDetalles(listarDetalles(id));
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return venta;
    }

    public double totalVentasPorMes(int mes, int anio) {
        double total = 0;
        try (Connection con = c.conectar()) {
            String sql = "select sum(total) from ventas where month(fecha)=? and year(fecha)=?";
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setInt(1, mes);
            ps.setInt(2, anio);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                total = rs.getDouble(1);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return total;
    }
}
