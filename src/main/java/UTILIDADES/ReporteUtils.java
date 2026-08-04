/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package UTILIDADES;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import DAO.CelularDAO;
import DAO.VentasDAO;
import MODELO.Celular;
import MODELO.ItemVenta;
import MODELO.Venta;

public class ReporteUtils {

    CelularDAO celularDAO = new CelularDAO();
    VentasDAO ventasDAO = new VentasDAO();

    public void mostrarStockBajo() {
        ArrayList<Celular> celulares = celularDAO.listar();
        List<Celular> stockBajo = celulares.stream()
                .filter(c -> c.getStock() < 5)
                .sorted(Comparator.comparingInt(Celular::getStock))
                .collect(Collectors.toList());

        System.out.println("------------------------------------------------------------");
        System.out.println("REPORTE: CELULARES CON STOCK BAJO (menos de 5 unidades)");
        System.out.println("------------------------------------------------------------");
        if (stockBajo.isEmpty()) {
            System.out.println("No hay celulares con stock bajo.");
        } else {
            System.out.printf("%-5s %-12s %-15s %-8s%n", "ID", "MARCA", "MODELO", "STOCK");
            for (Celular c : stockBajo) {
                System.out.printf("%-5d %-12s %-15s %-8d%n", c.getId(), c.getMarca(), c.getModelo(), c.getStock());
            }
        }
        System.out.println("------------------------------------------------------------");
    }

    public void mostrarTopVendidos() {
        ArrayList<ItemVenta> detalles = ventasDAO.listarTodosDetalles();

        Map<String, Integer> ventasPorCelular = detalles.stream()
                .collect(Collectors.groupingBy(
                        i -> i.getCelular().getMarca() + " " + i.getCelular().getModelo(),
                        Collectors.summingInt(ItemVenta::getCantidad)
                ));

        List<Map.Entry<String, Integer>> top3 = ventasPorCelular.entrySet().stream()
                .sorted(Map.Entry.<String, Integer>comparingByValue().reversed())
                .limit(3)
                .collect(Collectors.toList());

        System.out.println("------------------------------------------------------------");
        System.out.println("REPORTE: TOP 3 CELULARES MAS VENDIDOS");
        System.out.println("------------------------------------------------------------");
        if (top3.isEmpty()) {
            System.out.println("Aun no hay ventas registradas.");
        } else {
            int puesto = 1;
            for (Map.Entry<String, Integer> entry : top3) {
                System.out.printf("%d. %-25s %d unidades%n", puesto, entry.getKey(), entry.getValue());
                puesto++;
            }
        }
        System.out.println("------------------------------------------------------------");
    }
    
    public void mostrarSistemasOperativosMasVendidos() {
        ArrayList<ItemVenta> detalles = ventasDAO.listarTodosDetalles();

        Map<String, Integer> ventasPorSO = detalles.stream()
                .collect(Collectors.groupingBy(
                        i -> i.getCelular().getSistema_operativo(),
                        Collectors.summingInt(ItemVenta::getCantidad)
                ));

        List<Map.Entry<String, Integer>> ordenado = ventasPorSO.entrySet().stream()
                .sorted(Map.Entry.<String, Integer>comparingByValue().reversed())
                .collect(Collectors.toList());

        System.out.println("------------------------------------------------------------");
        System.out.println("REPORTE: SISTEMAS OPERATIVOS MAS VENDIDOS");
        System.out.println("------------------------------------------------------------");
        if (ordenado.isEmpty()) {
            System.out.println("Aun no hay ventas registradas.");
        } else {
            for (Map.Entry<String, Integer> entry : ordenado) {
                System.out.printf("%-20s %d unidades%n", entry.getKey(), entry.getValue());
            }
        }
        System.out.println("------------------------------------------------------------");
    }

    public void mostrarVentasPorMes() {
        ArrayList<Venta> ventas = ventasDAO.listar();
        SimpleDateFormat formatoMes = new SimpleDateFormat("MM/yyyy");

        Map<String, Double> totalesPorMes = ventas.stream()
                .collect(Collectors.groupingBy(
                        v -> formatoMes.format(v.getFecha()),
                        Collectors.summingDouble(Venta::getTotal)
                ));

        System.out.println("------------------------------------------------------------");
        System.out.println("REPORTE: VENTAS TOTALES POR MES");
        System.out.println("------------------------------------------------------------");
        if (totalesPorMes.isEmpty()) {
            System.out.println("Aun no hay ventas registradas.");
        } else {
            totalesPorMes.entrySet().stream()
                    .sorted(Map.Entry.comparingByKey())
                    .forEach(e -> System.out.printf("%-10s $ %,.2f%n", e.getKey(), e.getValue()));
        }
        System.out.println("------------------------------------------------------------");
    }
}
