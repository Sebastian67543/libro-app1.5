package com.distribuida.entities;

public class FacturaDetalle {

    private int idFacturaDetalle;
    private int cantidad;
    private Double subtotal;
    // Inyeccion dependencias
    private Libro libro;
    private Factura factura;


    public FacturaDetalle(int idFacturaDetalle, int cantidad, Double subtotal, Libro libro, Factura factura) {
        this.idFacturaDetalle = idFacturaDetalle;
        this.cantidad = cantidad;
        this.subtotal = subtotal;
        this.libro = libro;
        this.factura = factura;
    }

    public int getIdFacturaDetalle() {
        return idFacturaDetalle;
    }

    public void setIdFacturaDetalle(int idFacturaDetalle) {
        this.idFacturaDetalle = idFacturaDetalle;
    }

    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }

    public Double getSubtotal() {
        return subtotal;
    }

    public void setSubtotal(Double subtotal) {
        this.subtotal = subtotal;
    }

    public Libro getLibro() {
        return libro;
    }

    public void setLibro(Libro libro) {
        this.libro = libro;
    }

    public Factura getFactura() {
        return factura;
    }

    public void setFactura(Factura factura) {
        this.factura = factura;
    }


    @Override
    public String toString() {
        return "FacturaDetalle{" +
                "idFacturaDetalle=" + idFacturaDetalle +
                ", cantidad=" + cantidad +
                ", subtotal=" + subtotal +
                ", libro=" + libro +
                ", factura=" + factura +
                '}';
    }
}
