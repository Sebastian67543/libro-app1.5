package org.distribuida.principal;

import org.distribuida.entities.Factura;
import org.distribuida.entities.cliente;

import java.util.Date;

public class FacturaPrincipal {

    public static void main(String[] args){

        Factura factura = new Factura();

        cliente cliente = new cliente(1,"1753523","Aracely","Chupin","Tababela","911","correo@gmail.com");

        factura.setIdFactura(1);
        factura.setNumFactura("FAC -00012");
        factura.setFecha(new Date());
        factura.setTotalNeto(100.00);
        factura.setIva(12.00);
        factura.setTotal(120.00);
        factura.setCliente(cliente);

        System.out.println(factura.toString());

    }

}
