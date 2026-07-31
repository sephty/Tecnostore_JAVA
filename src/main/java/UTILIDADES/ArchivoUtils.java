/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package UTILIDADES;

import DAO.VentasDAO;
import MODELO.ItemVenta;
import MODELO.Venta;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

public class ArchivoUtils {

    VentasDAO ventasDAO = new VentasDAO();

    public void generarReporteVentas() {
        ArrayList<Venta> ventas = ventasDAO.listar();
        SimpleDateFormat formato = new SimpleDateFormat("dd/MM/yyyy HH:mm");

        try (BufferedWriter bw = new BufferedWriter(new FileWriter("reporte_ventas.txt"))) {
            bw.write("REPORTE GENERAL DE VENTAS - TECNOSTORE");
            bw.newLine();
            bw.write("============================================================");
            bw.newLine();

            if (ventas.isEmpty()) {
                bw.write("No hay ventas registradas.");
                bw.newLine();
            } else {
                for (int i = 0; i < ventas.size(); i++) {
                    Venta v = ventas.get(i); 
                    bw.write("Venta #" + v.getId());
                    bw.newLine();
                    bw.write("Cliente:  " + v.getCliente().getNombre());
                    bw.newLine();
                    bw.write("Fecha:    " + formato.format(v.getFecha()));
                    bw.newLine();

                    ArrayList<ItemVenta> detalles = ventasDAO.listarDetalles(v.getId());
                    for (int j = 0; j < detalles.size(); j++) {
                        ItemVenta item = detalles.get(j);
                        bw.write(String.format("  - %s %s x%d  $ %,.2f",
                                item.getCelular().getMarca(), item.getCelular().getModelo(),
                                item.getCantidad(), item.getSubtotal()));
                        bw.newLine();
                    }

                    bw.write(String.format("Total (IVA incluido): $ %,.2f", v.getTotal()));
                    bw.newLine();
                    bw.write("------------------------------------------------------------");
                    bw.newLine();
                }
            }

            System.out.println("Archivo reporte_ventas.txt generado correctamente.");
        } catch (IOException e) {
            System.out.println("Error al generar el archivo: " + e.getMessage());
        }
    }
}
