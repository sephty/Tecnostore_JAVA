package CONTROLADOR;

import DAO.CelularDAO;
import DAO.VentasDAO;
import MODELO.Celular;
import MODELO.ItemVenta;
import MODELO.Venta;
import java.util.ArrayList;

public class VentaController {

    private static final double IVA = 0.19;

    VentasDAO ventasDAO = new VentasDAO();
    CelularDAO celularDAO = new CelularDAO();

    public void registrarVenta(Venta venta) {
        ArrayList<ItemVenta> items = venta.getDetalles();

        for (int i = 0; i < items.size(); i++) {
            ItemVenta item = items.get(i);
            if (item.getCantidad() > item.getCelular().getStock()) {
                System.out.println("No hay stock suficiente de " + item.getCelular().getMarca() + " " + item.getCelular().getModelo() + ".");
                return;
            }
        }

        double subtotalGeneral = 0;
        for (int i = 0; i < items.size(); i++) {
            ItemVenta item = items.get(i);
            double subtotal = item.getCelular().getPrecio() * item.getCantidad();
            item.setSubtotal(subtotal);
            subtotalGeneral += subtotal;
        }

        double total = subtotalGeneral * (1 + IVA);
        venta.setTotal(total);

        ventasDAO.crear(venta);
        int idVenta = ventasDAO.obtenerUltimoId();

        for (int i = 0; i < items.size(); i++) {
            ItemVenta item = items.get(i);
            ventasDAO.crearDetalle(idVenta, item);

            Celular cel = item.getCelular();
            cel.setStock(cel.getStock() - item.getCantidad());
            celularDAO.update(cel);
        }

        System.out.println("Venta registrada. Total con IVA (19%): " + total);
    }

    public ArrayList<Venta> listar() {
        return ventasDAO.listar();
    }

    public Venta buscar(int id) {
        return ventasDAO.buscar(id);
    }

    public double totalVentasPorMes(int mes, int anio) {
        return ventasDAO.totalVentasPorMes(mes, anio);
    }
}