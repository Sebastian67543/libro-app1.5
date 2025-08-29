package com.distribuida.principal;

import com.distribuida.entities.Cliente;

public class ClientePrincipal {

    public static void main(String[] args){

        Cliente cliente = new Cliente(1, "17434123312","Jesus", "Taipa", "Av. Americas", "077899955", "dasdeasd@gmail.com");

        System.out.println(cliente.toString());
    }

}
