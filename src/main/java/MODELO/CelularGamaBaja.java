package MODELO;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

public class CelularGamaBaja extends Celular {

    public CelularGamaBaja(int id, String marca, String modelo, double precio, int stock, String sistema_operativo) {
        super(id, marca, modelo, precio, stock, sistema_operativo);
    }

    @Override
    public double calcularDescuento() {
        return precio * 0.10;
    }

    @Override
    public String getGama() {
        return "BAJA";
    }
}