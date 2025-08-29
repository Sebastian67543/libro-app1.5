package com.distribuida.model;

import jakarta.persistence.*;

@Entity
@Table(name = "factura_detalle")
public class FacturaDetalle {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_factura_detalle")
    private int idFacturaDetalle;
    @Column(name = "cantidad")
    private int cantidad;
    @Column(name = "subtotal")
    private Double subtotal;
    // Inyeccion dependencias
    @ManyToOne
    @JoinColumn(name = "id_libro")
    private Libro libro;
    @ManyToOne
    @JoinColumn(name = "id_factura")
    private Factura factura;

    public FacturaDetalle() {
    }

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
