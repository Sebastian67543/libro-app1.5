package org.distribuida.entities;

import java.util.Date;

public class Factura {

    private int idFactura;
    private String numFactura;
    private Date fecha;
    private Double totalNeto;
    private Double iva;
    private Double total;
    private cliente cliente;

    public Factura() {
    }

    public Factura(int idFactura, Double totalNeto, Double iva, Double total, cliente cliente, Date fecha, String numFactura) {
        this.idFactura = idFactura;
        this.totalNeto = totalNeto;
        this.iva = iva;
        this.total = total;
        this.cliente = cliente;
        this.fecha = fecha;
        this.numFactura = numFactura;
    }

    public int getIdFactura() {
        return idFactura;
    }

    public void setIdFactura(int idFactura) {
        this.idFactura = idFactura;
    }

    public String getNumFactura() {
        return numFactura;
    }

    public void setNumFactura(String numFactura) {
        this.numFactura = numFactura;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public Double getTotalNeto() {
        return totalNeto;
    }

    public void setTotalNeto(Double totalNeto) {
        this.totalNeto = totalNeto;
    }

    public Double getIva() {
        return iva;
    }

    public void setIva(Double iva) {
        this.iva = iva;
    }

    public Double getTotal() {
        return total;
    }

    public void setTotal(Double total) {
        this.total = total;
    }

    public cliente getCliente() {
        return cliente;
    }

    public void setCliente(cliente cliente) {
        this.cliente = cliente;
    }

    @Override
    public String toString() {
        return "Factura{" +
                "idFactura=" + idFactura +
                ", numFactura='" + numFactura + '\'' +
                ", fecha=" + fecha +
                ", totalNeto=" + totalNeto +
                ", iva=" + iva +
                ", total=" + total +
                ", cliente=" + cliente +
                '}';
    }
}
