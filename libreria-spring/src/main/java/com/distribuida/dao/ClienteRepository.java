package com.distribuida.dao;

import com.distribuida.model.Cliente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

// @Repository //el @Repository es un bean
public interface ClienteRepository extends JpaRepository<Cliente,Integer> {

    Cliente findByCedula(String cedula);

}
