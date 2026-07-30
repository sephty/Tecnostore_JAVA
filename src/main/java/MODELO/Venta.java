package MODELO;
import java.sql.Timestamp;
import java.util.ArrayList;

public class Venta {
    private int id;
    private Cliente cliente;
    private Timestamp fecha;
    private double total;
    private ArrayList<ItemVenta> detalles;

    public Venta() {
        this.detalles = new ArrayList<>();
    }

    public Venta(int id, Cliente cliente, Timestamp fecha, double total) {
        this.id = id;
        this.cliente = cliente;
        this.fecha = fecha;
        this.total = total;
        this.detalles = new ArrayList<>();
    }

    public Venta(Cliente cliente, ArrayList<ItemVenta> detalles) {
        this.cliente = cliente;
        this.detalles = detalles;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    public Timestamp getFecha() {
        return fecha;
    }

    public void setFecha(Timestamp fecha) {
        this.fecha = fecha;
    }

    public double getTotal() {
        return total;
    }

    public void setTotal(double total) {
        this.total = total;
    }

    public ArrayList<ItemVenta> getDetalles() {
        return detalles;
    }

    public void setDetalles(ArrayList<ItemVenta> detalles) {
        this.detalles = detalles;
    }
}