package com.distribuida.test;

import com.distribuida.entities.Autor;
import com.distribuida.entities.Categoria;
import com.distribuida.entities.Libro;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;

public class LibroTest {
    Categoria categoria = new Categoria(1,"Accion","Informacion Area 51");
    Autor autor = new Autor(1,"Jesus","Perez","Peru","Piura","5314578920","joseperez11@gmail.com");
    private Libro libro;


    @BeforeEach
    public void setup(){

        libro = new Libro(1, "Area 51","Informacion Aera 51", 155, "Cuarta Edicion", "Español",
                new Date(),"Informate acerca del Area 51", "Amarilla", "ISBN", 505, "Conoce del Area 51",
                "Lo que no sabias acerca del Area51", 15.5, categoria, autor);


    }

    @Test
    public void testClienteConstructorandGetter(){
        assertAll("Validar Datos Libro Constructor y Getters",
                () -> assertEquals(1, libro.getIdLibro()),
                () -> assertEquals("Area 51", libro.getTitulo()),
                () -> assertEquals("Informacion Aera 51", libro.getEditorial()),
                () -> assertEquals(155, libro.getNumPaginas()),
                () -> assertEquals("Cuarta Edicion", libro.getEdicion()),
                () -> assertEquals("Español", libro.getIdioma()),
                //() -> assertEquals(new Date(), libro.getFechaPublicacion()),
                () -> assertEquals("Informate acerca del Area 51", libro.getDescripcion()),
                () -> assertEquals("Amarilla", libro.getTipoPasta()),
                () -> assertEquals("ISBN", libro.getIsbn()),
                () -> assertEquals(505, libro.getNumEjemplares()),
                () -> assertEquals("Conoce del Area 51", libro.getPortada()),
                () -> assertEquals("Lo que no sabias acerca del Area51", libro.getPresentacion()),
                () -> assertEquals(15.5, libro.getPrecio()),
                () -> assertEquals(categoria, libro.getCategoria()),
                () -> assertEquals(autor, libro.getAutor())

                );

    }


    @Test
    public void testLibroSetters(){

        libro.setIdLibro(2);
        libro.setTitulo("ISMAC");
        libro.setEditorial("El futuro esta en aqui");
        libro.setNumPaginas(150);
        libro.setEdicion("Segunda edición");
        libro.setIdioma("Español");
        libro.setFechaPublicacion(new Date());
        libro.setDescripcion("Conoce acerca de nuestro instituto");
        libro.setTipoPasta("Bicolor");
        libro.setIsbn("ISBN");
        libro.setNumEjemplares(850);
        libro.setPortada("Conoce el mundo del ismac");
        libro.setPresentacion("Azul y Rojo");
        libro.setPrecio(37.5);
        libro.setCategoria(categoria);
        libro.setAutor(autor);

        assertAll("Validar datos Cliente Setters",
                () -> assertEquals(2, libro.getIdLibro()),
                () -> assertEquals("ISMAC", libro.getTitulo()),
                () -> assertEquals("El futuro esta en aqui", libro.getEditorial()),
                () -> assertEquals(150, libro.getNumPaginas()),
                () -> assertEquals("Segunda edición", libro.getEdicion()),
                () -> assertEquals("Español", libro.getIdioma()),
                //() -> assertEquals(new Date(), libro.getFechaPublicacion()),
                () -> assertEquals("Conoce acerca de nuestro instituto", libro.getDescripcion()),
                () -> assertEquals("Bicolor", libro.getTipoPasta()),
                () -> assertEquals("ISBN", libro.getIsbn()),
                () -> assertEquals(850, libro.getNumEjemplares()),
                () -> assertEquals("Conoce el mundo del ismac", libro.getPortada()),
                () -> assertEquals("Azul y Rojo", libro.getPresentacion()),
                () -> assertEquals(37.5, libro.getPrecio()),
                () -> assertEquals(categoria, libro.getCategoria()),
                () -> assertEquals(autor, libro.getAutor())

                );


    }

    @Test
    public void testLibroToString(){
        String str = libro.toString();

        assertAll("Validar Datos Libro To String",
                () -> assertTrue(str.contains("1")),
                () -> assertTrue(str.contains("Area 51")),
                () -> assertTrue(str.contains("Informacion Aera 51")),
                () -> assertTrue(str.contains("155")),
                () -> assertTrue(str.contains("Cuarta Edicion")),
                () -> assertTrue(str.contains("Español")),
                () -> assertTrue(str.contains("Informate acerca del Area 51")),
                () -> assertTrue(str.contains("Amarilla")),
                () -> assertTrue(str.contains("ISBN")),
                () -> assertTrue(str.contains("505")),
                () -> assertTrue(str.contains("Conoce del Area 51")),
                () -> assertTrue(str.contains("Lo que no sabias acerca del Area51")),
                () -> assertTrue(str.contains("15.5")),
                () -> assertTrue(str.contains("categoria")),
                () -> assertTrue(str.contains("autor"))

                );

    }



}
