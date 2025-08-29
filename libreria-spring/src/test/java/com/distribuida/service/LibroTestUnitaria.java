package com.distribuida.service;

import com.distribuida.dao.*;
import com.distribuida.model.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class LibroTestUnitaria {

    @Mock
    private FacturaRepository facturaRepository;

    @Mock
    private LibroRepository libroRepository;

    @Mock
    private ClienteRepository clienteRepository;

    @Mock
    private CategoriaRepository categoriaRepository;

    @Mock
    private AutorRepository autorRepository;

    @InjectMocks
    private AutorServiceImpl autorService;

    @InjectMocks
    private CategoriaServiceImpl categoriaService;

    @InjectMocks
    private FacturaServiceImpl facturaService;

    @InjectMocks
    private LibroServiceImpl libroService;

    @InjectMocks
    private ClienteServiceImpl clienteService;


    private Factura factura;
    private Libro libro;
    private Cliente cliente;
    private Categoria categoria;
    private Autor autor;

    @BeforeEach
    public void setUp(){
        MockitoAnnotations.openMocks(this);
        autor = new Autor(1, "Jose", "Flores", "Ecuador", "Av. por ahi y mas alla", "0947894561", "josefff@gmail.com");
        categoria = new Categoria(1,"Accion", "Luchas, guerras, choques y explosion");
        cliente = new Cliente(1, "1756325895", "Elmer", "Av Leones", "Gonzales", "0965321456","elmer212@gmail.com");
        libro =  new Libro(1, "Area 51", "Ecuadorian", 550, "Limitada", "Español", new Date(),"Conoce el area 51","Pasta dura","ISBN-005", 9500,"Amarilla", "Nueva",85.50,categoria, autor);
        factura = new Factura(1, "FAC-001", new Date(), 850.20,15.00,875.20,cliente);
        }
    @Test
    public void testFindAll(){
        when(libroRepository.findAll()).thenReturn(Arrays.asList(libro));
        List<Libro> libros = libroService.findAll();
        assertNotNull(libros);
        assertEquals(1, libros.size());
        verify(libroRepository, times(1)).findAll();
    }

    @Test
    public void testFindOne(){
        when(libroRepository.findById(1)).thenReturn(Optional.of(libro));
        Libro libro = libroService.findOne(1);
        assertEquals("Area 51", libro.getTitulo());
        verify(libroRepository, times(1)).findById(1);
    }

    @Test
    public void save(){
        when(libroRepository.save(libro)).thenReturn(libro);
        Libro libro1 = libroService.save(libro);
        assertNotNull(libro1);
        assertEquals("Area 51", libro.getTitulo());
        verify(libroRepository, times(1)).save(libro1);
    }

    @Test
    public void update(){
        Libro libroActualizado = new Libro(1, "Area 55", "Ecuadorian", 550, "Limitada", "Español", new Date(),"Conoce el area 51","Pasta dura","ISBN-005", 9500,"Amarilla", "Nueva",85.50,categoria, autor);
        when(libroRepository.findById(1)).thenReturn(Optional.of(libro));
        when(libroRepository.save(any(Libro.class))).thenReturn(libroActualizado);
        when(categoriaRepository.findById(1)).thenReturn(Optional.of(categoria));
        when(autorRepository.findById(1)).thenReturn(Optional.of(autor));
        Libro libro1 = libroService.update(1,1,libroActualizado);
        assertNotNull(libro1);
        assertEquals("Area 55", libro1.getTitulo());
        assertEquals("Ecuadorian", libro1.getEditorial());
        verify(libroRepository).save(any(Libro.class));
    }

    @Test
    public void testDelete(){
        when(libroRepository.existsById(1)).thenReturn(false);
        libroService.delete(1);
        verify(libroRepository, times(0)).deleteById(1);
    }

}
