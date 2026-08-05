package DAO;

import CONTROLADOR.ConexionDB;
import MODELO.Credito;
import MODELO.Cliente;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

public class CreditoDAO {

    private final ConexionDB c = ConexionDB.getInstancia();

    public List<Credito> listar() {
        List<Credito> respuesta = new ArrayList<>();
        String sql = "select cr.id, cr.id_cliente, cr.saldo, cr.saldo_pendiente, cr.fecha_ultimo_abono from credito cr";
        try (Connection con = c.conectar(); PreparedStatement ps = con.prepareStatement(sql); ResultSet rs = ps.executeQuery()) {
            DAO.ClienteDAO clienteDAO = new DAO.ClienteDAO();
            while (rs.next()) {
                int idCredito = rs.getInt(1);
                int idCliente = rs.getInt(2);
                double saldo = rs.getDouble(3);
                double saldoPendiente = rs.getDouble(4);
                Timestamp fecha = rs.getTimestamp(5);
                Cliente cliente = clienteDAO.buscar(idCliente);
                respuesta.add(new Credito(idCredito, cliente, saldo, saldoPendiente, fecha));
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return respuesta;
    }

    public Credito buscarPorClienteId(int idCliente) {
        Credito credito = null;
        String sql = "select cr.id, cr.id_cliente, cr.saldo, cr.saldo_pendiente, cr.fecha_ultimo_abono from credito cr where cr.id_cliente = ?";
        try (Connection con = c.conectar(); PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, idCliente);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    int idCredito = rs.getInt(1);
                    double saldo = rs.getDouble(3);
                    double saldoPendiente = rs.getDouble(4);
                    Timestamp fecha = rs.getTimestamp(5);
                    Cliente cliente = new DAO.ClienteDAO().buscar(idCliente);
                    credito = new Credito(idCredito, cliente, saldo, saldoPendiente, fecha);
                }
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return credito;
    }

    public boolean actualizarSaldoPorClienteId(int idCliente, double nuevoSaldoPendiente, Timestamp fecha) {
        String sql = "update credito set saldo_pendiente = ?, fecha_ultimo_abono = ? where id_cliente = ?";
        try (Connection con = c.conectar(); PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setDouble(1, nuevoSaldoPendiente);
            ps.setTimestamp(2, fecha);
            ps.setInt(3, idCliente);
            int updated = ps.executeUpdate();
            return updated > 0;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            return false;
        }
    }
}
