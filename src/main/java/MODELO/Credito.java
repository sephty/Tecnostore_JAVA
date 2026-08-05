package MODELO;

import java.sql.Timestamp;

public class Credito {
    private int id;
    private Cliente cliente;
    private double saldo;
    private double saldo_pendiente;
    private Timestamp fecha_ultimo_abono;

    public Credito(int id, Cliente cliente, double saldo, double saldo_pendiente, Timestamp fecha_ultimo_abono) {
        this.id = id;
        this.cliente = cliente;
        this.saldo = saldo;
        this.saldo_pendiente = saldo_pendiente;
        this.fecha_ultimo_abono = fecha_ultimo_abono;
    }

    public Credito(Cliente cliente, double saldo, Timestamp fecha_ultimo_abono) {
        this.cliente = cliente;
        this.saldo = saldo;
        this.fecha_ultimo_abono = fecha_ultimo_abono;
    }

    public int getId() {
        return id;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    public double getSaldo() {
        return saldo;
    }

    public void setSaldo(double saldo) {
        this.saldo = saldo;
    }

    public double getSaldo_pendiente() {
        return saldo_pendiente;
    }

    public void setSaldo_pendiente(double saldo_pendiente) {
        this.saldo_pendiente = saldo_pendiente;
    }

    public Timestamp getFecha_ultimo_abono() {
        return fecha_ultimo_abono;
    }

    public void setFecha_ultimo_abono(Timestamp fecha_ultimo_abono) {
        this.fecha_ultimo_abono = fecha_ultimo_abono;
    }
}
