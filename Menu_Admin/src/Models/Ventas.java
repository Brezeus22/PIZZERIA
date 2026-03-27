package Models;

public class Ventas {
    private int codigo;
    private int cantidad;
    private double preciounitario;
    private String nombre;
    private  String descripcion;

    public Ventas(int codigo, int cantidad, double preciounitario, String nombre, String descripcion) {
        this.codigo = codigo;
        this.cantidad = cantidad;
        this.preciounitario = preciounitario;
        this.nombre = nombre;
        this.descripcion = descripcion;
    }

    public Ventas() {
    }

    public int getCodigo() {
        return codigo;
    }

    public void setCodigo(int codigo) {
        this.codigo = codigo;
    }

    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }

    public double getPreciounitario() {
        return preciounitario;
    }

    public void setPreciounitario(double preciounitario) {
        this.preciounitario = preciounitario;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }
    
}

