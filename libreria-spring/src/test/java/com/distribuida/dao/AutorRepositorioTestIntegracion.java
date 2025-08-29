package com.distribuida.dao;

import com.distribuida.model.Autor;
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

public class AutorRepositorioTestIntegracion {

    @Autowired
    private AutorRepository autorRepository;

    @Test
    public void findAll(){
        List<Autor> autor = autorRepository.findAll();
        assertNotNull(autor);
        assertTrue(autor.size() >= 1);
        for (Autor item: autor){
            System.out.println(item.toString());
        }

    }

    @Test
    public void findOne(){

        Optional<Autor> autor = autorRepository.findById(35);
        assertTrue(autor.isPresent(), "El autor con id=35 debería existir");
        System.out.println(autor.toString());

    }

    @Test
    public void save(){
        Autor autor = new Autor(0, "Melisa", "Rosales", "Mexico", "Av. Nuevo Leon y Guadalajara", "1597863201","melirosa@gmail.com");
        autorRepository.save(autor);
        assertNotNull(autor.getIdAutor(), "El cliente guardado debe tener un Id");
        assertEquals("Melisa", autor.getNombre());
        assertEquals("Mexico", autor.getPais());
    }

    @Test
    public void update(){
        Optional<Autor> autor = autorRepository.findById(56);
        assertTrue(autor.isPresent(), "El autor con el ID=56 debería existir");

        autor.orElse(null).setNombre("Melisa");
        autor.orElse(null).setApellido("Rosales");
        autor.orElse(null).setPais("Mexico");
        autor.orElse(null).setDireccion("Av. Nuevo Leon y Guadalajara");
        autor.orElse(null).setTelefono("1597863201");
        autor.orElse(null).setCorreo("melirosa@gmail.com");

        Autor autorActualizado = autorRepository.save(autor.orElse(null));

        assertEquals("Melisa", autorActualizado.getNombre());
        assertEquals("Rosales",autorActualizado.getApellido());
    }

    @Test
    public void delete(){
        if (autorRepository.existsById(55)){
            autorRepository.deleteById(55);
        }

        assertFalse(autorRepository.existsById(55), "El ID=55, debería haberse eliminado");

    }


}
