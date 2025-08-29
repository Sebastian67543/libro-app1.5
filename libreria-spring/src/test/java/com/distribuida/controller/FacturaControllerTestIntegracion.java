package com.distribuida.controller;

import com.distribuida.model.Cliente;
import com.distribuida.model.Factura;
import com.distribuida.service.ClienteService;
import com.distribuida.service.FacturaService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.hibernate.annotations.ManyToAny;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Date;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SuppressWarnings("removal")
@WebMvcTest(FacturaController.class)
public class FacturaControllerTestIntegracion {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private FacturaService facturaService;
    private ClienteService clienteService;

    @Test
    public void testFindAll() throws Exception {
        Factura factura= new Factura(1, "FAC-0001", new Date(), 39.50, 15.00, 54.50, new Cliente(1, "1756210236", "Juan", "Av. Por ahi y mas alla", "Ingaramo", "0945210358","juaningaramo2@correo.com"));

        Mockito.when(facturaService.findAll()).thenReturn(List.of(factura));

        mockMvc.perform(get("/api/facturas"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].numFactura").value("FAC-0001"));
    }

    @Test
    public void testSave() throws Exception {
        Factura factura = new Factura(0,  "FAC-0002", new Date(), 39.50, 15.00, 54.50, new Cliente(1, "1756210236", "Juan", "Av. Por ahi y mas alla", "Ingaramo", "0945210358","juaningaramo2@correo.com"));

        Mockito.when(facturaService.save(any(Factura.class))).thenReturn(factura);

        mockMvc.perform(post("/api/facturas")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(factura))
                ) .andExpect(status().isOk())
                .andExpect(jsonPath("$.numFactura").value("FAC-0002"));
    }

    @Test
    public void testDelete() throws Exception {
        mockMvc.perform(delete("/api/facturas/1")).andExpect(status().isNoContent());
    }

}
