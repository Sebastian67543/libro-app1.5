package com.distribuida.service;

import com.distribuida.dao.AutorRepository;
import com.distribuida.dao.CategoriaRepository;
import com.distribuida.dao.LibroRepository;
import com.distribuida.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class LibroServiceImpl implements LibroService {

    @Autowired
    private LibroRepository libroRepository;

    @Autowired
    private CategoriaRepository categoriaRepository;

    @Autowired
    private AutorRepository autorRepository;

    @Override
    public List<Libro> findAll() {
        return libroRepository.findAll();
    }

    @Override
    public Libro findOne(int id) {
        Optional<Libro> libro = libroRepository.findById(id);
        return libro.orElse(null);

    }

    @Override
    public Libro save(Libro libro) {
        return libroRepository.save(libro);
    }

    @Override
    public Libro update(int idLibro, Libro libro) {
        Libro libroExistente = findOne(idLibro);
        if (libroExistente == null) return null;

        // Actualiza campos básicos
        libroExistente.setTitulo(libro.getTitulo());
        libroExistente.setEditorial(libro.getEditorial());
        libroExistente.setNumPaginas(libro.getNumPaginas());
        libroExistente.setEdicion(libro.getEdicion());
        libroExistente.setIdioma(libro.getIdioma());
        libroExistente.setFechaPublicacion(libro.getFechaPublicacion());
        libroExistente.setDescripcion(libro.getDescripcion());
        libroExistente.setTipoPasta(libro.getTipoPasta());
        libroExistente.setIsbn(libro.getIsbn());
        libroExistente.setNumEjemplares(libro.getNumEjemplares());
        libroExistente.setPortada(libro.getPortada());
        libroExistente.setPresentacion(libro.getPresentacion());
        libroExistente.setPrecio(libro.getPrecio());

// Actualiza autor
        if (libro.getAutor() != null) {
            Integer idAut = libro.getAutor().getIdAutor();
            if (idAut != null) {
                Autor autor = autorRepository.findById(idAut).orElse(null);
                libroExistente.setAutor(autor);
            }
        }

// Actualiza categoría
        if (libro.getCategoria() != null) {
            Integer idCat = libro.getCategoria().getIdCategoria();
            if (idCat != null) {
                Categoria categoria = categoriaRepository.findById(idCat).orElse(null);
                libroExistente.setCategoria(categoria);
            }
        }


        return libroRepository.save(libroExistente);
    }


    @Override
    public void delete(int id) {
    if (libroRepository.existsById(id)){
        libroRepository.deleteById(id);
    }
    }
}
