package com.distribuida.dao;

import com.distribuida.model.Autor;
import com.distribuida.model.Categoria;
import com.distribuida.model.Libro;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.Rollback;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Transactional
@Rollback(value = false)
public class LibroRepositorioTestIntegracion {

    @Autowired
    private LibroRepository libroRepository;

    @Autowired
    private CategoriaRepository categoriaRepository;

    @Autowired
    private AutorRepository autorRepository;

    @Test
    public void findAll(){
        List<Libro> libro = libroRepository.findAll();
        assertNotNull(libro);
        assertTrue(libro.size()>0);
        for(Libro item: libro){
            System.out.println(item.toString());
        }
    }

    @Test
    public void findOne(){
        Optional<Libro> libro = libroRepository.findById(1);
        assertTrue(libro.isPresent());
        System.out.println(libro.toString());

    }

    @Test
    public void sava(){
        Libro libro = new Libro();
        Optional<Categoria> categoria = categoriaRepository.findById(1);
        Optional<Autor> autor = autorRepository.findById(1);

        assertTrue(categoria.isPresent());
        assertTrue(autor.isPresent());

        libro.setIdLibro(0);
        libro.setTitulo("Mil aventuras");
        libro.setEditorial("Editorial Ecuador");
        libro.setNumPaginas(750);
        libro.setEdicion("4th");
        libro.setIdioma("Español");
        libro.setFechaPublicacion(new Date());
        libro.setDescripcion("Aventuras del Ecuador");
        libro.setTipoPasta("Pasta dura");
        libro.setIsbn("ISBN-8");
        libro.setNumEjemplares(500);
        libro.setPortada("Portada");
        libro.setPresentacion("Fisica");
        libro.setPrecio(10.50);
        libro.setCategoria(categoria.   orElse(null));
        libro.setAutor(autor.orElse(null));

        Libro libroGuardado = libroRepository.save(libro);
        assertEquals(10.50, libroGuardado.getPrecio());

    }

    @Test
    public void update(){
       Optional<Libro> libroExistente =libroRepository.findById(80);
       Optional<Categoria> categoria = categoriaRepository.findById(12);
       Optional<Autor> autor = autorRepository.findById(3);

       assertTrue(categoria.isPresent());
       assertTrue(autor.isPresent());

        libroExistente.orElse(null).setTitulo("Aventuras Ecuador");
        libroExistente.orElse(null).setPrecio(12.50);
        libroExistente.orElse(null).setNumEjemplares(850);
        libroExistente.orElse(null).setFechaPublicacion(new Date());
        libroExistente.orElse(null).setCategoria(categoria.orElse(null));
        libroExistente.orElse(null).setAutor(autor.orElse(null));

        Libro libroActualizado = libroRepository.save((libroExistente.orElse(null)));
        assertEquals(12.50, libroActualizado.getPrecio());
    }

    @Test
    public void delete(){
        if (libroRepository.existsById(79)){
            libroRepository.deleteById(79);
            Optional<Libro> libroEliminado = libroRepository.findById(79);
            assertFalse(libroEliminado.isPresent());
        }
    }


}
