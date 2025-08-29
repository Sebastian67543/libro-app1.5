package com.distribuida.service;

import com.distribuida.dao.ClienteRepository;
import com.distribuida.model.Cliente;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.swing.text.html.Option;
import java.util.List;
import java.util.Optional;

//aqui se gestiona la logica de negocio

@Service
public class ClienteServiceImpl implements ClienteService {


    @Autowired //Inyección de dependencias
    private ClienteRepository clienteRepository;



    @Override
    public List<Cliente> findAll() {
        return clienteRepository.findAll();
    }

    @Override
    public Cliente findOne(int id) {
        Optional<Cliente> cliente = clienteRepository.findById(id);
        return cliente.orElse(null);
    }

    @Override
    public Cliente save(Cliente cliente) {
        return clienteRepository.save(cliente);
    }

    @Override
    public Cliente update(int id, Cliente clienteNuevo) {
        Cliente clienteExistente = findOne(id);

        if(clienteExistente == null) {
            return null;
        }
        clienteExistente.setCedula(clienteNuevo.getCedula());
        clienteExistente.setCedula(clienteNuevo.getNombre());
        clienteExistente.setApellido(clienteNuevo.getApellido());
        clienteExistente.setApellido(clienteNuevo.getDireccion());
        clienteExistente.setDireccion(clienteNuevo.getTelefono());
        clienteExistente.setTelefono(clienteNuevo.getCorreo());


        return clienteRepository.save(clienteExistente);
    }

    @Override
    public void delete(int id) {
        if (clienteRepository.existsById(id)){
            clienteRepository.deleteById(id);

        }

    }
}
