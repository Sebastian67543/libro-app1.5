package com.distribuida.controller;

import com.distribuida.model.Carrito;
import com.distribuida.service.CarritoService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

public class CarritoGuestControllerTestUnitaria {

    @InjectMocks
    private CarritoGuestController carritoGuestController;

    @Mock
    private CarritoService carritoService;

    private Carrito carrito;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);

        carrito = new Carrito();
        carrito.setIdCarrito(1L);
        carrito.setToken("test-token-123");
        carrito.setSubtotal(new BigDecimal("100.00"));
        carrito.setTotal(new BigDecimal("115.00"));
    }

    @Test
    public void testCreateOrGet() {
        when(carritoService.getOrCreateByToken(anyString())).thenReturn(carrito);

        ResponseEntity<Carrito> respuesta = carritoGuestController.createOrGet("test-token");

        assertEquals(200, respuesta.getStatusCodeValue());
        assertNotNull(respuesta.getBody());
        assertEquals("test-token-123", respuesta.getBody().getToken());
        verify(carritoService, times(1)).getOrCreateByToken("test-token");
    }

    @Test
    public void testGet() {
        when(carritoService.getByToken(anyString())).thenReturn(carrito);

        ResponseEntity<Carrito> respuesta = carritoGuestController.get("test-token");

        assertEquals(200, respuesta.getStatusCodeValue());
        assertNotNull(respuesta.getBody());
        assertEquals(1L, respuesta.getBody().getIdCarrito());
        verify(carritoService, times(1)).getByToken("test-token");
    }

    @Test
    public void testAddItem() {
        when(carritoService.addItem(anyString(), anyInt(), anyInt())).thenReturn(carrito);

        Map<String, Integer> body = new HashMap<>();
        body.put("libroId", 1);
        body.put("cantidad", 2);

        ResponseEntity<Carrito> respuesta = carritoGuestController.addItem("test-token", body);

        assertEquals(200, respuesta.getStatusCodeValue());
        assertNotNull(respuesta.getBody());
        assertEquals(new BigDecimal("100.00"), respuesta.getBody().getSubtotal());
        verify(carritoService, times(1)).addItem("test-token", 1, 2);
    }

    @Test
    public void testAddItem_WithDefaultValues() {
        when(carritoService.addItem(anyString(), anyInt(), anyInt())).thenReturn(carrito);

        Map<String, Integer> body = new HashMap<>();
        // Body vacío o con valores faltantes

        ResponseEntity<Carrito> respuesta = carritoGuestController.addItem("test-token", body);

        assertEquals(200, respuesta.getStatusCodeValue());
        verify(carritoService, times(1)).addItem("test-token", 0, 0);
    }

    @Test
    public void testUpdateItem() {
        when(carritoService.updateItemCantidad(anyString(), anyLong(), anyInt())).thenReturn(carrito);

        Map<String, Integer> body = new HashMap<>();
        body.put("cantidad", 3);

        ResponseEntity<Carrito> respuesta = carritoGuestController.update("test-token", 123L, body);

        assertEquals(200, respuesta.getStatusCodeValue());
        assertNotNull(respuesta.getBody());
        verify(carritoService, times(1)).updateItemCantidad("test-token", 123L, 3);
    }

    @Test
    public void testUpdateItem_WithDefaultValue() {
        when(carritoService.updateItemCantidad(anyString(), anyLong(), anyInt())).thenReturn(carrito);

        Map<String, Integer> body = new HashMap<>();
        // Body vacío

        ResponseEntity<Carrito> respuesta = carritoGuestController.update("test-token", 123L, body);

        assertEquals(200, respuesta.getStatusCodeValue());
        verify(carritoService, times(1)).updateItemCantidad("test-token", 123L, 0);
    }

    @Test
    public void testRemoveItem() {
        doNothing().when(carritoService).removeItem(anyString(), anyLong());

        ResponseEntity<Void> respuesta = carritoGuestController.remove("test-token", 456L);

        assertEquals(204, respuesta.getStatusCodeValue());
        verify(carritoService, times(1)).removeItem("test-token", 456L);
    }

    @Test
    public void testClear() {
        doNothing().when(carritoService).clearByToken(anyString());

        ResponseEntity<Void> respuesta = carritoGuestController.clear("test-token");

        assertEquals(204, respuesta.getStatusCodeValue());
        verify(carritoService, times(1)).clearByToken("test-token");
    }

    @Test
    public void testAddItem_NullBody() {
        when(carritoService.addItem(anyString(), anyInt(), anyInt())).thenReturn(carrito);

        ResponseEntity<Carrito> respuesta = carritoGuestController.addItem("test-token", null);

        assertEquals(200, respuesta.getStatusCodeValue());
        verify(carritoService, times(1)).addItem("test-token", 0, 0);
    }

    @Test
    public void testUpdateItem_NullBody() {
        when(carritoService.updateItemCantidad(anyString(), anyLong(), anyInt())).thenReturn(carrito);

        ResponseEntity<Carrito> respuesta = carritoGuestController.update("test-token", 123L, null);

        assertEquals(200, respuesta.getStatusCodeValue());
        verify(carritoService, times(1)).updateItemCantidad("test-token", 123L, 0);
    }

    @Test
    public void testAddItem_PartialBody() {
        when(carritoService.addItem(anyString(), anyInt(), anyInt())).thenReturn(carrito);

        Map<String, Integer> body = new HashMap<>();
        body.put("libroId", 5); // Solo libroId, falta cantidad

        ResponseEntity<Carrito> respuesta = carritoGuestController.addItem("test-token", body);

        assertEquals(200, respuesta.getStatusCodeValue());
        verify(carritoService, times(1)).addItem("test-token", 5, 0);
    }

    @Test
    public void testUpdateItem_ZeroQuantity() {
        when(carritoService.updateItemCantidad(anyString(), anyLong(), anyInt())).thenReturn(carrito);

        Map<String, Integer> body = new HashMap<>();
        body.put("cantidad", 0);

        ResponseEntity<Carrito> respuesta = carritoGuestController.update("test-token", 123L, body);

        assertEquals(200, respuesta.getStatusCodeValue());
        verify(carritoService, times(1)).updateItemCantidad("test-token", 123L, 0);
    }
}