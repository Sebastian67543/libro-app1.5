package com.distribuida.controller;

import com.distribuida.model.Carrito;
import com.distribuida.service.CarritoService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SuppressWarnings("removal")
@WebMvcTest(CarritoGuestController.class)
public class CarritoGuestControllerTestIntegracion {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private CarritoService carritoService;

    @Test
    public void testCreateOrGet() throws Exception {
        Carrito carrito = new Carrito();
        carrito.setIdCarrito(1L);
        carrito.setToken("test-token-123");
        carrito.setSubtotal(new BigDecimal("100.00"));
        carrito.setTotal(new BigDecimal("115.00"));

        when(carritoService.getOrCreateByToken(anyString())).thenReturn(carrito);

        mockMvc.perform(post("/api/guest/cart")
                        .param("token", "test-token-123"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.idCarrito").value(1))
                .andExpect(jsonPath("$.token").value("test-token-123"))
                .andExpect(jsonPath("$.subtotal").value(100.00))
                .andExpect(jsonPath("$.total").value(115.00));
    }

    @Test
    public void testGet() throws Exception {
        Carrito carrito = new Carrito();
        carrito.setIdCarrito(1L);
        carrito.setToken("test-token-456");
        carrito.setSubtotal(new BigDecimal("50.00"));
        carrito.setTotal(new BigDecimal("57.50"));

        when(carritoService.getByToken(anyString())).thenReturn(carrito);

        mockMvc.perform(get("/api/guest/cart")
                        .param("token", "test-token-456"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.idCarrito").value(1))
                .andExpect(jsonPath("$.token").value("test-token-456"))
                .andExpect(jsonPath("$.subtotal").value(50.00))
                .andExpect(jsonPath("$.total").value(57.50));
    }

    @Test
    public void testAddItem() throws Exception {
        Carrito carrito = new Carrito();
        carrito.setIdCarrito(1L);
        carrito.setToken("test-token-789");
        carrito.setSubtotal(new BigDecimal("25.50"));
        carrito.setTotal(new BigDecimal("29.33"));

        Map<String, Integer> requestBody = new HashMap<>();
        requestBody.put("libroId", 1);
        requestBody.put("cantidad", 2);

        when(carritoService.addItem(anyString(), anyInt(), anyInt())).thenReturn(carrito);

        mockMvc.perform(post("/api/guest/cart/items")
                        .param("token", "test-token-789")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(requestBody)))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.idCarrito").value(1))
                .andExpect(jsonPath("$.token").value("test-token-789"))
                .andExpect(jsonPath("$.subtotal").value(25.50))
                .andExpect(jsonPath("$.total").value(29.33));
    }

    @Test
    public void testAddItem_MissingParameters() throws Exception {
        Map<String, Integer> requestBody = new HashMap<>();
        requestBody.put("libroId", 1);
        // Falta cantidad

        mockMvc.perform(post("/api/guest/cart/items")
                        .param("token", "test-token")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(requestBody)))
                .andDo(print())
                .andExpect(status().isOk()); // El servicio usa getOrDefault, así que no debería fallar
    }

    @Test
    public void testUpdateItem() throws Exception {
        Carrito carrito = new Carrito();
        carrito.setIdCarrito(1L);
        carrito.setToken("test-token-update");
        carrito.setSubtotal(new BigDecimal("30.00"));
        carrito.setTotal(new BigDecimal("34.50"));

        Map<String, Integer> requestBody = new HashMap<>();
        requestBody.put("cantidad", 3);

        when(carritoService.updateItemCantidad(anyString(), anyLong(), anyInt())).thenReturn(carrito);

        mockMvc.perform(put("/api/guest/cart/items/123")
                        .param("token", "test-token-update")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(requestBody)))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.idCarrito").value(1))
                .andExpect(jsonPath("$.token").value("test-token-update"))
                .andExpect(jsonPath("$.subtotal").value(30.00))
                .andExpect(jsonPath("$.total").value(34.50));
    }

    @Test
    public void testRemoveItem() throws Exception {
        mockMvc.perform(delete("/api/guest/cart/items/456")
                        .param("token", "test-token-remove"))
                .andDo(print())
                .andExpect(status().isNoContent());
    }

    @Test
    public void testClear() throws Exception {
        mockMvc.perform(delete("/api/guest/cart/clear")
                        .param("token", "test-token-clear"))
                .andDo(print())
                .andExpect(status().isNoContent());
    }

    @Test
    public void testAddItem_InvalidBody() throws Exception {
        // Body vacío
        mockMvc.perform(post("/api/guest/cart/items")
                        .param("token", "test-token")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{}"))
                .andDo(print())
                .andExpect(status().isOk()); // El servicio usa getOrDefault, así que usa valores por defecto
    }

    @Test
    public void testUpdateItem_InvalidBody() throws Exception {
        // Body vacío
        mockMvc.perform(put("/api/guest/cart/items/123")
                        .param("token", "test-token")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{}"))
                .andDo(print())
                .andExpect(status().isOk()); // El servicio usa getOrDefault, así que usa valores por defecto
    }

    @Test
    public void testGet_NoToken() throws Exception {
        mockMvc.perform(get("/api/guest/cart"))
                .andDo(print())
                .andExpect(status().isBadRequest()); // Debería fallar porque falta el parámetro token
    }

    @Test
    public void testCreateOrGet_NoToken() throws Exception {
        mockMvc.perform(post("/api/guest/cart"))
                .andDo(print())
                .andExpect(status().isBadRequest()); // Debería fallar porque falta el parámetro token
    }
}