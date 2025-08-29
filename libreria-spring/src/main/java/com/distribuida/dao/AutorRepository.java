package com.distribuida.dao;

import com.distribuida.model.Autor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

//@Repository
public interface AutorRepository extends JpaRepository<Autor,Integer> {

    Autor findByNombre(String nombre);

}
