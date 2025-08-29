package com.distribuida.principal;

import com.distribuida.entities.Autor;
import com.distribuida.entities.Categoria;
import com.distribuida.entities.Libro;

import java.util.Date;

public class LibroPrincipal {

    public static void main(String[] args) {
        Libro libro = new Libro();

        Categoria categoria = new Categoria(1,"Accion","Informacion Area 51");
        Autor autor = new Autor(1,"Jesus","Perez","Peru","Piura","5314578920","joseperez11@gmail.com");

        libro.setIdLibro(1);
        libro.setTitulo("Area 51");
        libro.setEditorial("Informacion Area 51");
        libro.setNumPaginas(155);
        libro.setEdicion("Cuarta Edicion");
        libro.setIdioma("Español");
        libro.setFechaPublicacion(new Date());
        libro.setDescripcion("Informate acerca del Area 51");
        libro.setTipoPasta("Amarilla");
        libro.setIsbn("ISBN");
        libro.setNumEjemplares(505);
        libro.setPortada("Conoce del Area 51");
        libro.setPresentacion("Lo que no sabias acerca del Area51");
        libro.setPrecio(15.5);
        libro.setCategoria(categoria);
        libro.setAutor(autor);

    }


}
