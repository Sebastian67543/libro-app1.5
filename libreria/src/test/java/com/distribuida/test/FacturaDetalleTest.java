package com.distribuida.test;

import com.distribuida.entities.Factura;
import com.distribuida.entities.FacturaDetalle;
import com.distribuida.entities.Libro;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class FacturaDetalleTest {

    private FacturaDetalle facturaDetalle;
    private Libro libro;
    private Factura factura;

    @BeforeEach
    public void setup() {
        libro = new Libro();       // ahora es un atributo de clase
        factura = new Factura();   // ahora es un atributo de clase

        facturaDetalle = new FacturaDetalle(1, 15, 12.0, libro, factura);
    }

    @Test
    public void testFacturaDetalleConstructorandGetter() {
        assertAll("Validar datos FacturaDetalle, Constructor and Getters",
                () -> assertEquals(1, facturaDetalle.getIdFacturaDetalle()),
                () -> assertEquals(15, facturaDetalle.getCantidad()),
                () -> assertEquals(12.0, facturaDetalle.getSubtotal()),
                () -> assertEquals(libro, facturaDetalle.getLibro()),
                () -> assertEquals(factura, facturaDetalle.getFactura())
        );
    }
}

