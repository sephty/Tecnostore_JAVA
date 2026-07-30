/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAO;

import CONTROLADOR.ConexionDB;
import MODELO.Celular;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import javax.swing.JOptionPane;

/**
 *
 * @author garci
 */
public class CelularDAO {

    ConexionDB c = new ConexionDB();

    public void crear(Celular cl) {
        try (Connection con = c.conectar()) {
            String sql = "insert into celulares(marca, modelo, sistema_operativo, gama, precio, stock) values (?,?,?,?,?,?)";
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1, cl.getMarca());
            ps.setString(2, cl.getModelo());
            ps.setString(3, cl.getSistema_operativo());
            ps.setString(4, cl.getGama());
            ps.setDouble(5, cl.getPrecio());
            ps.setInt(6, cl.getStock());
            ps.executeUpdate();
            System.out.println("Celular añadido correctamente!");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public void update(Celular cl) {
        try (Connection con = c.conectar()) {
            String sql = "update celulares set marca=?, modelo=?, sistema_operativo=?, gama=?, precio=?, stock=? where id=?";
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1, cl.getMarca());
            ps.setString(2, cl.getModelo());
            ps.setString(3, cl.getSistema_operativo());
            ps.setString(4, cl.getGama());
            ps.setDouble(5, cl.getPrecio());
            ps.setInt(6, cl.getStock());
            ps.setInt(7, cl.getId());
            ps.executeUpdate();
            System.out.println("Celular actualizado correctamente!");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public ArrayList<Celular> listar() {
        ArrayList<Celular> respuesta = new ArrayList<>();
        try (Connection con = c.conectar()) {
            PreparedStatement ps = con.prepareStatement("select * from celulares");
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                respuesta.add(new Celular(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getDouble(6), rs.getInt(7), rs.getString(4), rs.getString(5)));
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return respuesta;
    }

    public Celular buscar(int id) {
        Celular celular = null;
        try (Connection con = c.conectar()) {
            PreparedStatement ps = con.prepareStatement("select * from celulares where id=?");
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                celular = new Celular(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getDouble(6), rs.getInt(7), rs.getString(4), rs.getString(5));
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return celular;
    }

    public void delete(Celular celular) {
        if (celular == null) {
            System.out.println("NO EXISTE CELULAR CON LAS ESPICIFICACIONES DADAS!");
        } else {
            int op = JOptionPane.showConfirmDialog(null, "¿Esta segur@ de eliminar " + celular.getMarca() + " " + celular.getModelo() + "?", null, JOptionPane.YES_NO_OPTION);
            if (op == 0) {
                try (Connection con = c.conectar()) {
                    PreparedStatement ps = con.prepareStatement("delete from celulares where id=?");
                    ps.setInt(1, celular.getId());
                    ps.executeUpdate();
                    System.out.println("Celular " + celular.getMarca() + " " + celular.getModelo() + " eliminado con exito!");
                } catch (SQLException e) {
                    System.out.println(e.getMessage());
                }
            } else {
                System.out.println("Operacion cancelada!");
            }
        }
    } 
}
