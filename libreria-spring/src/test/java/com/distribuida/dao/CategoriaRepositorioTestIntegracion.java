package com.distribuida.dao;


import com.distribuida.model.Categoria;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.Rollback;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Transactional
@Rollback(value = false)


public class CategoriaRepositorioTestIntegracion {

    @Autowired
    private CategoriaRepository categoriaRepository;

    @Test
    public void findAll(){
        List<Categoria> categoria = categoriaRepository.findAll();
        assertNotNull(categoria);
        assertTrue(categoria.size() >0);
        for (Categoria item: categoria){
            System.out.println(item.toString());
        }

    }

    @Test
    public void findOne(){
        Optional<Categoria> categoria = categoriaRepository.findById(35);
        assertTrue(categoria.isPresent(), "El autor con id=35 debería existir");
        System.out.println(categoria.toString());
    }

    @Test
    public void save(){
        Categoria categoria = new Categoria(0, "Accion", "Libros de accion");
        categoriaRepository.save(categoria);
        assertNotNull(categoria.getIdCategoria(), "La categoria guardada debe tener un id");
        assertEquals("Accion", categoria.getCategoria());
    }

    @Test
    public void update(){
        Optional<Categoria> categoria = categoriaRepository.findById(59);
        assertTrue(categoria.isPresent(), "La categoria con ID=59 debería existir");

        categoria.orElse(null).setCategoria("Accion");
        categoria.orElse(null).setDescripcion("Libros de accion");

        Categoria categoriaActualizado = categoriaRepository.save(categoria.orElse(null));

        assertEquals("Accion", categoriaActualizado.getCategoria());
        assertEquals("Libros de accion", categoriaActualizado.getDescripcion());
    }

    @Test
    public void delete(){
        if (categoriaRepository.existsById(58)){
            categoriaRepository.deleteById(58);
        }

        assertFalse(categoriaRepository.existsById(58), "El ID=58, debería haberse eliminado");

    }

}
