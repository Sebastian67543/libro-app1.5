package com.distribuida.service;

import com.distribuida.model.Factura;
import com.distribuida.model.Libro;

import java.util.List;

public interface LibroService {

    List<Libro> findAll();

    Libro findOne(int id);

    Libro save(Libro libro);

    // Solo se pasa el id del libro y el objeto completo
    Libro update(int idLibro, Libro libro);

    void delete(int id);
}

