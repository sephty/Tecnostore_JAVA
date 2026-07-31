/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author garci
 */
package VISTA;

import UTILIDADES.ReporteUtils;
import UTILIDADES.Validador;

public class VistaReportes {

    Validador validador = new Validador();
    ReporteUtils reporteUtils = new ReporteUtils();

    public void menu() {
        int op;
        do {
            op = validador.validarEnteroRango("""
                                    ------------------------------------------------------------
                                    REPORTES
                                    ------------------------------------------------------------
                                    1. Stock bajo
                                    2. Top 3 mas vendidos
                                    3. Ventas totales por mes
                                    4. Volver
                                    ------------------------------------------------------------
                                    """, 1, 4);
            switch (op) {
                case 1:
                    reporteUtils.mostrarStockBajo();
                    break;
                case 2:
                    reporteUtils.mostrarTopVendidos();
                    break;
                case 3:
                    reporteUtils.mostrarVentasPorMes();
                    break;
            }
        } while (op != 4);
    }
}
