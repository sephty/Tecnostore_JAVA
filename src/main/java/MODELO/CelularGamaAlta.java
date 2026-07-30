/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
// MODELO/CelularGamaAlta.java
package MODELO;

public class CelularGamaAlta extends Celular {

    public CelularGamaAlta(int id, String marca, String modelo, double precio, int stock, String sistema_operativo) {
        super(id, marca, modelo, precio, stock, sistema_operativo);
    }

    @Override
    public double calcularDescuento() {
        return 0;
    }

    @Override
    public String getGama() {
        return "ALTA";
    }
}