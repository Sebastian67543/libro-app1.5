package com.distribuida.dao;

import com.distribuida.model.Cliente;
import com.distribuida.model.Factura;
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
public class FacturaRepositorioTestIntegracion {

    @Autowired
    private FacturaRepository facturaRepository;

    @Autowired
    private ClienteRepository clienteRepository;

    @Test
    public void findAll(){
        List<Factura> facturas = facturaRepository.findAll();
        assertNotNull(facturas);
        for (Factura item: facturas){
            System.out.println(item.toString());
        }
    }

    @Test
    public void findOne(){
        Optional<Factura> factura = facturaRepository.findById(1);
        assertTrue(factura.isPresent());
        System.out.println(factura.toString());
    }

    @Test
    public void save(){
        Factura factura = new Factura();

        Optional<Cliente> cliente = clienteRepository.findById(1);

        assertTrue(cliente.isPresent());

        factura.setIdFactura(0);
        factura.setNumFactura("FAC-0099");
        factura.setFecha(new Date());
        factura.setTotalNeto(100.00);
        factura.setIva(15.00);
        factura.setTotal(115.00);
        factura.setCliente(cliente.orElse(null));


        Factura facturaGuardada = facturaRepository.save(factura);
        assertEquals(115.00, facturaGuardada.getTotal());

    }

    @Test
    public void update(){

        Optional<Factura> facturExistente = facturaRepository.findById(88);

        Optional<Cliente> cliente = clienteRepository.findById(2);

        assertTrue(cliente.isPresent());

        facturExistente.orElse(null).setNumFactura("FAC-0100");
        facturExistente.orElse(null).setFecha(new Date());
        facturExistente.orElse(null).setTotalNeto(200.00);
        facturExistente.orElse(null).setIva(30.00);
        facturExistente.orElse(null).setTotal(230.00);
        facturExistente.orElse(null).setCliente(cliente.orElse(null));

        Factura facturaActualizada = facturaRepository.save(facturExistente.orElse(null));
        assertEquals(230.00, facturaActualizada.getTotal());


    }

    @Test
    public void delete(){
        if (facturaRepository.existsById(87)){
            facturaRepository.deleteById(87);
            Optional<Factura> facturaEliminada = facturaRepository.findById(87);
            assertFalse(facturaEliminada.isPresent());
        }
    }


}
