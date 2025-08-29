package com.distribuida.dao;

import com.distribuida.model.Factura;
import com.distribuida.model.FacturaDetalle;
import com.distribuida.model.Libro;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.Rollback;

import javax.management.ConstructorParameters;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Transactional
@Rollback(value = false)
public class FacturaDetalleTestIntegracion {

    @Autowired
    private FacturaDetalleRepository facturaDetalleRepository;

    @Autowired
    private LibroRepository libroRepository;

    @Autowired
    private FacturaRepository facturaRepository;

    @Test
    public void findAll(){
        List<FacturaDetalle> facturaDetalle = facturaDetalleRepository.findAll();
        assertNotNull(facturaDetalle);
        assertTrue(facturaDetalle.size()>0);
        for (FacturaDetalle item: facturaDetalle){
            System.out.println(item.toString());
        }

    }

    @Test
    public void findOne(){
        Optional<FacturaDetalle> facturaDetalle = facturaDetalleRepository.findById(1);
        assertTrue(facturaDetalle.isPresent());
        System.out.println(facturaDetalle.toString());
    }

    @Test
    public void save(){
        FacturaDetalle facturaDetalle = new FacturaDetalle();
        Optional<Libro> libro = libroRepository.findById(1);
        Optional<Factura> factura = facturaRepository.findById(1);

        assertTrue(libro.isPresent());
        assertTrue(factura.isPresent());

        facturaDetalle.setCantidad(15);
        facturaDetalle.setSubtotal(15.50);
        facturaDetalle.setFactura(factura.orElse(null));
        facturaDetalle.setLibro(libro.orElse(null));

        FacturaDetalle facturaDetalleGuardado = facturaDetalleRepository.save(facturaDetalle);
        assertEquals(15.50, facturaDetalleGuardado.getSubtotal());

    }

    @Test
    public void update(){
        Optional<FacturaDetalle> facturaDetalleExistente = facturaDetalleRepository.findById(211);
        Optional<Factura> factura = facturaRepository.findById(10);
        Optional<Libro> libro = libroRepository.findById(5);

        assertTrue(factura.isPresent());
        assertTrue(libro.isPresent());
        facturaDetalleExistente.orElse(null).setCantidad(50);
        facturaDetalleExistente.orElse(null).setSubtotal(85.50);
        facturaDetalleExistente.orElse(null).setFactura(factura.orElse(null));
        facturaDetalleExistente.orElse(null).setLibro(libro.orElse(null));

        FacturaDetalle facturaDetalleActualizado = facturaDetalleRepository.save(facturaDetalleExistente.orElse(null));
        assertEquals(50, facturaDetalleActualizado.getCantidad());


    }

    @Test
    public void delete(){
        if (facturaDetalleRepository.existsById(210)){
            facturaDetalleRepository.deleteById(210);
            Optional<FacturaDetalle> facturaDetalleEliminado = facturaDetalleRepository.findById(210);
            assertFalse(facturaDetalleEliminado.isPresent());
        }
    }

}
