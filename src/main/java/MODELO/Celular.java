package MODELO;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */


public abstract class Celular {
    protected int id;
    protected String marca;
    protected String modelo;
    protected double precio;
    protected int stock;
    protected String sistema_operativo;

    public Celular(int id, String marca, String modelo, double precio, int stock, String sistema_operativo) {
        this.id = id;
        this.marca = marca;
        this.modelo = modelo;
        this.precio = precio;
        this.stock = stock;
        this.sistema_operativo = sistema_operativo;
    }

    public abstract double calcularDescuento();
    public abstract String getGama();

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getMarca() {
        return marca;
    }

    public void setMarca(String marca) {
        this.marca = marca;
    }

    public String getModelo() {
        return modelo;
    }

    public void setModelo(String modelo) {
        this.modelo = modelo;
    }

    public double getPrecio() {
        return precio;
    }

    public void setPrecio(double precio) {
        this.precio = precio;
    }

    public int getStock() {
        return stock;
    }

    public void setStock(int stock) {
        this.stock = stock;
    }

    public String getSistema_operativo() {
        return sistema_operativo;
    }

    public void setSistema_operativo(String sistema_operativo) {
        this.sistema_operativo = sistema_operativo;
    }

    @Override
    public String toString() {
        return """
                id:                 %d
                marca:              %s
                modelo:             %s
                precio:             %.2f
                stock:              %d
                sistema_operativo:  %s
                gama:               %s
                """.formatted(id, marca, modelo, precio, stock, sistema_operativo, getGama());
    }
}