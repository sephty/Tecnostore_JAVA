/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package UTILIDADES;

import MODELO.Celular;
import MODELO.CelularGamaAlta;
import MODELO.CelularGamaBaja;
import MODELO.CelularGamaMedia;

public class FactoryCelular {

    public static Celular crearCelular(int id, String marca, String modelo, double precio, int stock, String sistemaOperativo, String gama) {
        switch (gama.toUpperCase()) {
            case "ALTA":
                return new CelularGamaAlta(id, marca, modelo, precio, stock, sistemaOperativo);
            case "MEDIA":
                return new CelularGamaMedia(id, marca, modelo, precio, stock, sistemaOperativo);
            case "BAJA":
                return new CelularGamaBaja(id, marca, modelo, precio, stock, sistemaOperativo);
            default:
                System.out.println("Gama no reconocida, se asigna BAJA por defecto.");
                return new CelularGamaBaja(id, marca, modelo, precio, stock, sistemaOperativo);
        }
    }
}
