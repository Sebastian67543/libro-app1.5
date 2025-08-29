package com.distribuida.test;


import com.distribuida.entities.Categoria;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class CategoriaTest {


    private Categoria categoria;

    @BeforeEach
    public void setup(){

        categoria = new Categoria(1, "Ciencia Ficcion", "Libros acerca de ciencia ficcion");

    }

    @Test
    public void testCategoriaConstructorandGetter(){
        assertAll("Validar datos Categoria Constructor y Getters",
                () -> assertEquals(1, categoria.getIdCategoria()),
                () -> assertEquals("Ciencia Ficcion", categoria.getCategoria()),
                () -> assertEquals("Libros acerca de ciencia ficcion", categoria.getDescripcion())
                );


    }

    @Test
    public void testCategoriaSetters(){
        categoria.setIdCategoria(2);
        categoria.setCategoria("Aventura");
        categoria.setDescripcion("Trata de aventuras realizadas por personajes principal");

        assertAll("Validacion datos Categoria Setters",
        () -> assertEquals(2, categoria.getIdCategoria()),
        () -> assertEquals("Aventura", categoria.getCategoria()),
        () -> assertEquals("Trata de aventuras realizadas por personajes principal", categoria.getDescripcion())

        );
    }

    @Test
    public void testAutorToString(){
        String str = categoria.toString();

        assertAll("Validar Datos Categoria To String",
                () -> assertTrue(str.contains("1")),
                () -> assertTrue(str.contains("Ciencia Ficcion")),
                () -> assertTrue(str.contains("Libros acerca de ciencia ficcion"))

                );


    }


}
