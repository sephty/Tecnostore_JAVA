
/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package MODELO;

public class CelularGamaMedia extends Celular {

    public CelularGamaMedia(int id, String marca, String modelo, double precio, int stock, String sistema_operativo) {
        super(id, marca, modelo, precio, stock, sistema_operativo);
    }

    @Override
    public double calcularDescuento() {
        return precio * 0.05;
    }

    @Override
    public String getGama() {
        return "MEDIA";
    }
}