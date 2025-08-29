package com.distribuida.controller;

import com.distribuida.model.Categoria;
import com.distribuida.service.CategoriaService;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.persistence.ManyToOne;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@SuppressWarnings("removal")
@WebMvcTest(CategoriaController.class)
public class CategoriaControllerTestIntegracion {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private CategoriaService categoriaService;

    @Test
    public void testFindAll() throws Exception {
        Categoria categorias = new Categoria(1, "Accion", "Libros de acción");

        Mockito.when(categoriaService.findAll()).thenReturn(List.of(categorias));

        mockMvc.perform(get("/api/categorias"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].categoria").value("Accion"));
    }

    @Test
    public void testSave() throws Exception {
        Categoria categorias = new Categoria(0, "Animada", "Libros animados");

        Mockito.when(categoriaService.save(any(Categoria.class))).thenReturn(categorias);

        mockMvc.perform(post("/api/categorias")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(categorias)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.categoria").value("Animada"));
    }

    @Test
    public void testDelete() throws Exception {
        mockMvc.perform(delete("/api/categorias/1")).andExpect(status().isNoContent());
    }

}
