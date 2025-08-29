package com.distribuida.controller;

import com.distribuida.model.Cliente;
import com.distribuida.service.ClienteService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.persistence.ManyToOne;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.any;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


import java.util.List;



@SuppressWarnings("removal")
@WebMvcTest(clienteController.class)
public class ClienteControllerTestIntegracion {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private ClienteService clienteService;

    @Test
    public void testFindAll() throws Exception {
        Cliente clientes = new Cliente(1, "1756210236", "Juan", "Av. Por ahi y mas alla", "Ingaramo", "0945210358","juaningaramo2@correo.com");

        Mockito.when(clienteService.findAll()).thenReturn(List.of(clientes));

        mockMvc.perform(get("/clientes"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].nombre").value("Juan"));
    }

    @Test
    public void testSave() throws Exception {
        Cliente clientes = new Cliente(0, "1756210236", "Juan", "Av. Por ahi y mas alla", "Ingaramo", "0945210358","juaningaramo2@correo.com");

        Mockito.when(clienteService.save(any(Cliente.class))).thenReturn(clientes);

        mockMvc.perform(post("/clientes")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(clientes))
                )      .andExpect(status().isOk())
                .andExpect(jsonPath("$.nombre").value("Juan"));
    }

    @Test
    public void testDelete() throws Exception {
        mockMvc.perform(delete("/clientes/1")).andExpect(status().isNoContent());
    }

}
