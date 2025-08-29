package com.distribuida.test;

import com.distribuida.entities.Autor;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class AutorTest {

    private Autor autor;

    @BeforeEach
    public void setup(){

        autor = new Autor(1, "Jesus", "Flores", "Ecuador", "Quito, Av. Simon Bolivar", "0914768952", "jesusfl111@gmail.com.ec" );

    }

    @Test
    public void testAutorConstructorandGetter(){
        assertAll("Validar datos Cliente, Constructor y Getters",
                () -> assertEquals(1, autor.getIdAutor()),
                () -> assertEquals("Jesus", autor.getNombre()),
                () -> assertEquals("Flores", autor.getApellido()),
                () -> assertEquals("Ecuador", autor.getPais()),
                () -> assertEquals("Quito, Av. Simon Bolivar", autor.getDireccion()),
                () -> assertEquals("0914768952", autor.getTelefono()),
                () -> assertEquals("jesusfl111@gmail.com.ec", autor.getCorreo())

                );


    }

    @Test
    public void testClienteSetters(){

        autor.setIdAutor(2);
        autor.setNombre("Jose");
        autor.setApellido("Balseca");
        autor.setPais("Ecuador");
        autor.setDireccion("Quito, Av. Del ciclista");
        autor.setTelefono("0914789352");
        autor.setCorreo("josebalssfl111@gmail.com.ec");

        assertAll("Validar Datos Cliente Setters",
                () -> assertEquals(2, autor.getIdAutor()),
                () -> assertEquals("Jose", autor.getNombre()),
                () -> assertEquals("Balseca", autor.getApellido()),
                () -> assertEquals("Ecuador", autor.getPais()),
                () -> assertEquals("Quito, Av. Del ciclista", autor.getDireccion()),
                () -> assertEquals("0914789352", autor.getTelefono()),
                () -> assertEquals("josebalssfl111@gmail.com.ec", autor.getCorreo())


                );



    }


    @Test
    public void testAutorToString(){
        String str = autor.toString();

        assertAll("Validar Datos Cliente To String",
                () -> assertTrue(str.contains("1")),
                () -> assertTrue(str.contains("Jesus")),
                () -> assertTrue(str.contains("Flores")),
                () -> assertTrue(str.contains("Ecuador")),
                () -> assertTrue(str.contains("Quito, Av. Simon Bolivar")),
                () -> assertTrue(str.contains("0914768952")),
                () -> assertTrue(str.contains("jesusfl111@gmail.com.ec"))

                );

    }





}
