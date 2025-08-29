package com.distribuida.test;


import org.distribuida.entities.cliente;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class ClienteTest {

    private cliente cliente;


    @BeforeEach
    public void setup(){

        cliente = new cliente(1,"1753352515", "Ara","Chupin",
                "Tumbaco" ,"911","pepe@gmail.com");
    }

    @Test
    public void testClienteConstructorandGetter(){
        assertAll("Validar datos Cliente, Constructor y Getters",
                () -> assertEquals(1, cliente.getIdCliente()),
                () -> assertEquals("17434123312", cliente.getCedula()),
                () -> assertEquals("Jesus", cliente.getNombre()),
                () -> assertEquals("Taipa", cliente.getApellido()),
                () -> assertEquals("Av. Americas", cliente.getDireccion()),
                () -> assertEquals("077899955", cliente.getTelefono()),
                () -> assertEquals("dasdeasd@gmail.com", cliente.getCorreo())
        );
    }

    @Test
    public void testClienteSetters(){

        cliente.setIdCliente(2);
        cliente.setCedula("17533525152");
        cliente.setNombre("Juan");
        cliente.setApellido("Tipan");
        cliente.setDireccion("Pifo");
        cliente.setTelefono("1800");
        cliente.setCorreo("magyar@gmail.com");

        assertAll("Validar Datos Cliente Setters",
                () -> assertEquals(2, cliente.getIdCliente()),
                () -> assertEquals("17533525152", cliente.getCedula()),
                () -> assertEquals("Juan", cliente.getNombre()),
                () -> assertEquals("Tipan", cliente.getApellido()),
                () -> assertEquals("Pifo", cliente.getDireccion()),
                () -> assertEquals("1800", cliente.getTelefono()),
                () -> assertEquals("magyar@gmail.com", cliente.getCorreo())
        );
    }

    @Test
    public void testClienteToString(){
        String str = cliente.toString();

        assertAll("Validar Datos Cliente To String",
                () -> assertTrue(str.contains("1")),
                () -> assertTrue(str.contains("1753352515")),
                () -> assertTrue(str.contains("Ara")),
                () -> assertTrue(str.contains("Chupin")),
                () -> assertTrue(str.contains("Tumbaco" )),
                () -> assertTrue(str.contains("911")),
                () -> assertTrue(str.contains("pepe@gmail.com"))
        );

    }
}