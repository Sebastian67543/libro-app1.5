package com.distribuida.dao;

import com.distribuida.model.Carrito;
import com.distribuida.model.Cliente;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.Rollback;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Transactional
@Rollback(value = false)
public class CarritoRepositorioTestIntegracion {

    @Autowired
    private CarritoRepository carritoRepository;

    @Autowired
    private ClienteRepository clienteRepository;

    @Test
    public void findAll() {
        List<Carrito> carritos = carritoRepository.findAll();
        assertNotNull(carritos);
        assertTrue(carritos.size() >= 1);
        for (Carrito carrito : carritos) {
            System.out.println(carrito.toString());
        }
    }

    @Test
    public void findOne() {
        Optional<Carrito> carrito = carritoRepository.findById(1L);
        assertTrue(carrito.isPresent(), "El carrito con id= 1 debería existir");
        System.out.println(carrito.toString());
    }

    @Test
    public void save() {
        // Primero necesitamos un cliente existente
        Optional<Cliente> clienteExistente = clienteRepository.findById(1);
        assertTrue(clienteExistente.isPresent(), "Debe existir un cliente con id=1");

        Carrito carrito = new Carrito();
        carrito.setCliente(clienteExistente.get());
        carrito.setToken("test-token-123");

        carritoRepository.save(carrito);

        assertNotNull(carrito.getIdCarrito(), "El carrito guardado debe tener un Id");
        assertEquals("test-token-123", carrito.getToken());
        assertEquals(1, carrito.getCliente().getIdCliente());
    }

    @Test
    public void update() {
        // Crear un carrito de prueba
        Optional<Cliente> cliente = clienteRepository.findById(1);
        assertTrue(cliente.isPresent(), "Debe existir un cliente con id=1");

        Carrito carritoOriginal = new Carrito();
        carritoOriginal.setCliente(cliente.get());
        carritoOriginal.setToken("token-original");

        Carrito carritoGuardado = carritoRepository.save(carritoOriginal);
        Long carritoId = carritoGuardado.getIdCarrito();

        // Actualizar el carrito
        Optional<Carrito> carritoParaActualizar = carritoRepository.findById(carritoId);
        assertTrue(carritoParaActualizar.isPresent(), "El carrito debería existir");

        String nuevoToken = "token-actualizado-" + System.currentTimeMillis();
        carritoParaActualizar.get().setToken(nuevoToken);

        Carrito carritoActualizado = carritoRepository.save(carritoParaActualizar.get());

        assertEquals(nuevoToken, carritoActualizado.getToken());
        assertEquals(carritoId, carritoActualizado.getIdCarrito());

        System.out.println("Carrito actualizado: ID=" + carritoActualizado.getIdCarrito() +
                ", Token=" + carritoActualizado.getToken());
    }


    @Test
    public void delete() {
        Optional<Cliente> cliente = clienteRepository.findById(1);
        assertTrue(cliente.isPresent(), "Debe existir un cliente con id=1");

        Carrito carrito = new Carrito();
        carrito.setCliente(cliente.get());
        carrito.setToken("token-to-delete");

        Carrito carritoGuardado = carritoRepository.save(carrito);
        Long idToDelete = carritoGuardado.getIdCarrito();

        assertTrue(carritoRepository.existsById(idToDelete), "El carrito debería existir antes de eliminar");


        carritoRepository.deleteById(idToDelete);

        assertFalse(carritoRepository.existsById(idToDelete), "El carrito debería haberse eliminado");
    }

    @Test
    public void findByCliente() {

        Cliente clienteTest = new Cliente();
        clienteTest.setNombre("Cliente Test FindBy");
        clienteTest.setCorreo("findby@test.com");
        Cliente clienteGuardado = clienteRepository.save(clienteTest);

        Carrito carritoTest = new Carrito();
        carritoTest.setCliente(clienteGuardado);
        carritoTest.setToken("token-findby-test");
        Carrito carritoGuardado = carritoRepository.save(carritoTest);

        Optional<Carrito> carritoEncontrado = carritoRepository.findByCliente(clienteGuardado);

        assertTrue(carritoEncontrado.isPresent(), "Debería encontrar el carrito por cliente");
        assertEquals(clienteGuardado.getIdCliente(), carritoEncontrado.get().getCliente().getIdCliente());
        assertEquals("token-findby-test", carritoEncontrado.get().getToken());
        assertEquals(carritoGuardado.getIdCarrito(), carritoEncontrado.get().getIdCarrito());

        System.out.println("Carrito encontrado por cliente: " + carritoEncontrado.get().toString());

        carritoRepository.deleteById(carritoGuardado.getIdCarrito());
        clienteRepository.deleteById(clienteGuardado.getIdCliente());
    }

    @Test
    public void findByToken() {
        // Primero creamos un cliente de prueba
        Cliente clienteTest = new Cliente();
        clienteTest.setNombre("Cliente Token Test");
        clienteTest.setCorreo("token-test@email.com");
        Cliente clienteGuardado = clienteRepository.save(clienteTest);

        // Creamos un token único
        String tokenUnico = "test-token-" + System.currentTimeMillis() + "-" + Math.random();

        // Creamos el carrito con el token único
        Carrito carrito = new Carrito();
        carrito.setCliente(clienteGuardado);
        carrito.setToken(tokenUnico);

        Carrito carritoGuardado = carritoRepository.save(carrito);

        // Buscamos por token
        Optional<Carrito> carritoEncontrado = carritoRepository.findByToken(tokenUnico);

        // Assertions
        assertTrue(carritoEncontrado.isPresent(), "Debería encontrar el carrito por token único");
        assertEquals(tokenUnico, carritoEncontrado.get().getToken());
        assertEquals(carritoGuardado.getIdCarrito(), carritoEncontrado.get().getIdCarrito());
        assertEquals(clienteGuardado.getIdCliente(), carritoEncontrado.get().getCliente().getIdCliente());

        System.out.println("Carrito encontrado por token '" + tokenUnico + "': " + carritoEncontrado.get().toString());

        // Limpieza (opcional)
        carritoRepository.deleteById(carritoGuardado.getIdCarrito());
        clienteRepository.deleteById(clienteGuardado.getIdCliente());
    }

    @Test
    public void findByToken_NotExists() {
        Optional<Carrito> carrito = carritoRepository.findByToken("non-existent-token");

        assertFalse(carrito.isPresent(), "No debería encontrar carrito con token inexistente");
        System.out.println("Carrito no encontrado (como se esperaba) para token: non-existent-token");
    }

    @Test
    public void testCarritoRelationships() {
        // Primero verificamos si existe algún carrito con relaciones
        List<Carrito> todosCarritos = carritoRepository.findAll();

        Carrito carritoConRelaciones = null;

        // Buscamos un carrito que tenga cliente asociado
        for (Carrito carrito : todosCarritos) {
            if (carrito.getCliente() != null) {
                carritoConRelaciones = carrito;
                break;
            }
        }

        // Si no existe, creamos uno
        if (carritoConRelaciones == null) {
            // Crear cliente
            Cliente cliente = new Cliente();
            cliente.setNombre("Test Relationships");
            cliente.setCorreo("test-relations@email.com");
            Cliente clienteGuardado = clienteRepository.save(cliente);

            // Crear carrito
            Carrito carrito = new Carrito();
            carrito.setCliente(clienteGuardado);
            carrito.setToken("test-relations-token");
            carritoConRelaciones = carritoRepository.save(carrito);
        }

        // Ahora probamos las relaciones
        Optional<Carrito> carrito = carritoRepository.findById(carritoConRelaciones.getIdCarrito());
        assertTrue(carrito.isPresent(), "El carrito debería existir");

        Carrito carritoCompleto = carrito.get();

        assertNotNull(carritoCompleto.getCliente(), "El carrito debería tener un cliente asociado");
        assertNotNull(carritoCompleto.getToken(), "El carrito debería tener un token");
        assertNotNull(carritoCompleto.getCliente().getIdCliente(), "El cliente debería tener un ID");

        System.out.println("Carrito completo: " + carritoCompleto.toString());
        System.out.println("Cliente asociado: " + carritoCompleto.getCliente().toString());
        System.out.println("ID Cliente: " + carritoCompleto.getCliente().getIdCliente());
        System.out.println("Nombre Cliente: " + carritoCompleto.getCliente().getNombre());
    }
}