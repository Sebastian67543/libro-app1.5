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

import java.lang.reflect.Array;
import java.util.*;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class FacturaDetalleTestUnitaria {

    @Mock
    private FacturaDetalleRepository facturaDetalleRepository;

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
    private FacturaDetalleServiceImpl facturaDetalleService;

    @InjectMocks
    private FacturaServiceImpl facturaService;

    @InjectMocks
    private LibroServiceImpl libroService;

    @InjectMocks
    private ClienteServiceImpl clienteService;

    private FacturaDetalle facturaDetalle;
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
        facturaDetalle = new FacturaDetalle(1, 1500,850.50,libro,factura);
    }

    @Test
    public void testFindAll(){
        when(facturaDetalleRepository.findAll()).thenReturn(Arrays.asList(facturaDetalle));
        List<FacturaDetalle> facturaDetalles = facturaDetalleService.findAll();
        assertNotNull(facturaDetalles);
        assertEquals(1, facturaDetalles.size());
        verify(facturaDetalleRepository, times(1)).findAll();
    }

    @Test
    public void restFindOne(){
        when(facturaDetalleRepository.findById(1)).thenReturn(Optional.of(facturaDetalle));
        FacturaDetalle facturaDetalle = facturaDetalleService.findOne(1);
        assertEquals(1500, facturaDetalle.getCantidad());
        verify(facturaDetalleRepository, times(1)).findById(1);
    }

    @Test
    public void save(){
        when(facturaDetalleRepository.save(facturaDetalle)).thenReturn(facturaDetalle);
        FacturaDetalle facturaDetalle1 = facturaDetalleService.save(facturaDetalle);
        assertNotNull(facturaDetalle1);
        assertEquals(1500, facturaDetalle.getCantidad());
        verify(facturaDetalleRepository, times(1)).save(facturaDetalle);
    }

    @Test
    public void update(){
        FacturaDetalle facturaDetalleActualizada = new FacturaDetalle(1, 1600,850.50,libro,factura);
        when(facturaDetalleRepository.findById(1)).thenReturn(Optional.of(facturaDetalle));
        when(facturaDetalleRepository.save(any(FacturaDetalle.class))).thenReturn(facturaDetalleActualizada);
        when(libroRepository.findById(1)).thenReturn(Optional.of(libro));
        when(facturaRepository.findById(1)).thenReturn(Optional.of(factura));
        FacturaDetalle facturaDetalle1 = facturaDetalleService.update(1,facturaDetalleActualizada);
        assertNotNull(facturaDetalle1);
        assertEquals(1600,facturaDetalle1.getCantidad());
        assertEquals(850.50, facturaDetalle1.getSubtotal());
        verify(facturaDetalleRepository).save(any(FacturaDetalle.class));
    }

    @Test
    public void testDelete(){
        when(facturaDetalleRepository.existsById(1)).thenReturn(false);
        facturaDetalleService.delete(1);
        verify(facturaDetalleRepository, times(0)).deleteById(1);
    }





}
