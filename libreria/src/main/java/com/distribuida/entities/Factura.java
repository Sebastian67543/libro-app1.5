package com.distribuida.entities;

import java.util.Date;

public class Factura {



        private int idFactura;
        private String numFactura;
        private Date fecha;
        private Double totalNeto;
        private Double iva;
        private Double total;
        private Cliente cliente;

        public Factura() {
            this.idFactura = idFactura;
            this.fecha = fecha;
            this.numFactura = numFactura;
            this.totalNeto = totalNeto;
            this.iva = iva;
            this.total = total;
            this.cliente = cliente;
        }

        public int getIdFactura() {
            return idFactura;
        }

        public void setIdFactura(int idFactura) {
            this.idFactura = idFactura;
        }

        public Cliente getCliente() {
            return cliente;
        }

        public void setCliente(Cliente cliente) {
            this.cliente = cliente;
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

        public String getNumFactura() {
            return numFactura;
        }

        public void setNumFactura(String numFactura) {
            this.numFactura = numFactura;
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

