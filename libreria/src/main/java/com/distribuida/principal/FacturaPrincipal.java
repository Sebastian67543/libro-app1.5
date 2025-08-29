package com.distribuida.principal;



import com.distribuida.entities.Cliente;
import com.distribuida.entities.Factura;

import java.util.Date;

public class FacturaPrincipal {

    public static void main(String[] args) {

        Factura factura = new Factura();
        Cliente cliente = new Cliente(1, "17434123312","Juan", "Taipa", "Av. Americas", "077899955", "dasdeasd@gmail.com");

        factura.setIdFactura(1);
        factura.setNumFactura("FAC-0001");
        factura.setFecha(new Date());
        factura.setTotalNeto(150.00);
        factura.setIva(165.00);
        factura.setCliente(cliente);


        System.out.println(factura.toString());

    }

}
