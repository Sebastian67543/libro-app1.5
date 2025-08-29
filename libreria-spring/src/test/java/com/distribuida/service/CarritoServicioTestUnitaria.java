package com.distribuida.service;

import com.distribuida.dao.CarritoItemRepository;
import com.distribuida.dao.CarritoRepository;
import com.distribuida.dao.ClienteRepository;
import com.distribuida.dao.LibroRepository;
import com.distribuida.model.Carrito;
import com.distribuida.model.CarritoItem;
import com.distribuida.model.Cliente;
import com.distribuida.model.Libro;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class CarritoServicioTestUnitaria {

    @Mock
    private CarritoRepository carritoRepository;

    @Mock
    private CarritoItemRepository carritoItemRepository;

    @Mock
    private ClienteRepository clienteRepository;

    @Mock
    private LibroRepository libroRepository;

    @InjectMocks
    private CarritoServiceImpl carritoService;

    private Cliente cliente;
    private Libro libro;
    private Carrito carrito;
    private CarritoItem carritoItem;

    @BeforeEach
    public void setUp() {
        cliente = new Cliente();
        cliente.setIdCliente(1);
        cliente.setNombre("Juan Pérez");

        libro = new Libro();
        libro.setIdLibro(1);
        libro.setPrecio(25.50);

        carrito = new Carrito();
        carrito.setIdCarrito(1L);
        carrito.setCliente(cliente);
        carrito.setToken("token-123");

        carritoItem = new CarritoItem();
        carritoItem.setIdCarritoItem(1L);
        carritoItem.setCarrito(carrito);
        carritoItem.setLibro(libro);
        carritoItem.setCantidad(2);
        carritoItem.setPrecioUnitario(BigDecimal.valueOf(25.50));
        carritoItem.calcTotal();
    }

    @Test
    public void testGetOrCreateByClienteId_Existente() {
        when(clienteRepository.findById(1)).thenReturn(Optional.of(cliente));
        when(carritoRepository.findByCliente(cliente)).thenReturn(Optional.of(carrito));

        Carrito resultado = carritoService.getOrCreateByClienteId(1, "token-123");

        assertNotNull(resultado);
        assertEquals(carrito, resultado);
        verify(carritoRepository, never()).save(any());
    }

    @Test
        public void testGetOrCreateByClienteId_Nuevo() {
        when(clienteRepository.findById(1)).thenReturn(Optional.of(cliente));
        when(carritoRepository.findByCliente(cliente)).thenReturn(Optional.empty());
        when(carritoRepository.save(any(Carrito.class))).thenReturn(carrito);

        Carrito resultado = carritoService.getOrCreateByClienteId(1, "token-123");

        assertNotNull(resultado);
        verify(carritoRepository, times(1)).save(any(Carrito.class));
    }

    @Test
    public void testGetOrCreateByClienteId_ClienteNoEncontrado() {
        when(clienteRepository.findById(999)).thenReturn(Optional.empty());

        assertThrows(IllegalArgumentException.class, () -> {
            carritoService.getOrCreateByClienteId(999, "token-123");
        });
    }

    @Test
    public void testAddItem_ClienteId_ItemExistente() {
        when(clienteRepository.findById(1)).thenReturn(Optional.of(cliente));
        when(carritoRepository.findByCliente(cliente)).thenReturn(Optional.of(carrito));
        when(libroRepository.findById(1)).thenReturn(Optional.of(libro));
        when(carritoItemRepository.findByCarritoAndLibro(carrito, libro)).thenReturn(Optional.of(carritoItem));
        when(carritoRepository.save(any(Carrito.class))).thenReturn(carrito);

        Carrito resultado = carritoService.addItem(1, 1, 3);

        assertNotNull(resultado);
        assertEquals(5, carritoItem.getCantidad()); // 2 existentes + 3 nuevos
        verify(carritoItemRepository, times(1)).save(carritoItem);
        verify(carritoRepository, times(1)).save(carrito);
    }

    @Test
    public void testAddItem_ClienteId_ItemNuevo() {
        when(clienteRepository.findById(1)).thenReturn(Optional.of(cliente));
        when(carritoRepository.findByCliente(cliente)).thenReturn(Optional.of(carrito));
        when(libroRepository.findById(1)).thenReturn(Optional.of(libro));
        when(carritoItemRepository.findByCarritoAndLibro(carrito, libro)).thenReturn(Optional.empty());
        when(carritoRepository.save(any(Carrito.class))).thenReturn(carrito);

        Carrito resultado = carritoService.addItem(1, 1, 2);

        assertNotNull(resultado);
        assertEquals(1, carrito.getItems().size()); // Debe tener un nuevo item
        verify(carritoRepository, times(1)).save(carrito);
    }

    @Test
    public void testAddItem_ClienteId_CantidadInvalida() {
        assertThrows(IllegalArgumentException.class, () -> {
            carritoService.addItem(1, 1, 0);
        });

        assertThrows(IllegalArgumentException.class, () -> {
            carritoService.addItem(1, 1, -1);
        });
    }

    @Test
    public void testUpdateItemCantidad_ClienteId_CantidadPositiva() {
        when(clienteRepository.findById(1)).thenReturn(Optional.of(cliente));
        when(carritoRepository.findByCliente(cliente)).thenReturn(Optional.of(carrito));
        when(carritoItemRepository.findById(1L)).thenReturn(Optional.of(carritoItem));
        when(carritoRepository.save(any(Carrito.class))).thenReturn(carrito);

        Carrito resultado = carritoService.updateItemCantidad(1, 1L, 5);

        assertNotNull(resultado);
        assertEquals(5, carritoItem.getCantidad());
        verify(carritoItemRepository, times(1)).save(carritoItem);
        verify(carritoRepository, times(1)).save(carrito);
    }

    @Test
    public void testUpdateItemCantidad_ClienteId_CantidadCero() {
        when(clienteRepository.findById(1)).thenReturn(Optional.of(cliente));
        when(carritoRepository.findByCliente(cliente)).thenReturn(Optional.of(carrito));
        when(carritoItemRepository.findById(1L)).thenReturn(Optional.of(carritoItem));
        when(carritoRepository.save(any(Carrito.class))).thenReturn(carrito);

        Carrito resultado = carritoService.updateItemCantidad(1, 1L, 0);

        assertNotNull(resultado);
        assertFalse(carrito.getItems().contains(carritoItem)); // Item debe ser removido
        verify(carritoItemRepository, times(1)).delete(carritoItem);
        verify(carritoRepository, times(1)).save(carrito);
    }

    @Test
    public void testRemoveItem_ClienteId() {
        // removeItem llama a updateItemCantidad con cantidad 0
        when(clienteRepository.findById(1)).thenReturn(Optional.of(cliente));
        when(carritoRepository.findByCliente(cliente)).thenReturn(Optional.of(carrito));
        when(carritoItemRepository.findById(1L)).thenReturn(Optional.of(carritoItem));
        when(carritoRepository.save(any(Carrito.class))).thenReturn(carrito);

        carritoService.removeItem(1, 1L);

        verify(carritoItemRepository, times(1)).delete(carritoItem);
        verify(carritoRepository, times(1)).save(carrito);
    }

    @Test
    public void testClear_ClienteId() {
        when(clienteRepository.findById(1)).thenReturn(Optional.of(cliente));
        when(carritoRepository.findByCliente(cliente)).thenReturn(Optional.of(carrito));
        when(carritoRepository.save(any(Carrito.class))).thenReturn(carrito);

        carritoService.clear(1);

        assertTrue(carrito.getItems().isEmpty());
        verify(carritoRepository, times(1)).save(carrito);
    }

    @Test
    public void testGetByClienteId_Existente() {
        when(clienteRepository.findById(1)).thenReturn(Optional.of(cliente));
        when(carritoRepository.findByCliente(cliente)).thenReturn(Optional.of(carrito));

        Carrito resultado = carritoService.getByClienteId(1);

        assertNotNull(resultado);
        assertEquals(carrito, resultado);
    }

    @Test
    public void testGetByClienteId_Nuevo() {
        when(clienteRepository.findById(1)).thenReturn(Optional.of(cliente));
        when(carritoRepository.findByCliente(cliente)).thenReturn(Optional.empty());

        Carrito resultado = carritoService.getByClienteId(1);

        assertNotNull(resultado);
        assertEquals(cliente, resultado.getCliente());
    }

    @Test
    public void testGetOrCreateByToken_Nuevo() {
        when(carritoRepository.findByToken("new-token")).thenReturn(Optional.empty());
        when(carritoRepository.save(any(Carrito.class))).thenReturn(carrito);

        Carrito resultado = carritoService.getOrCreateByToken("new-token");

        assertNotNull(resultado);
        verify(carritoRepository, times(1)).save(any(Carrito.class));
    }

    @Test
    public void testGetOrCreateByToken_Existente() {
        when(carritoRepository.findByToken("token-123")).thenReturn(Optional.of(carrito));

        Carrito resultado = carritoService.getOrCreateByToken("token-123");

        assertNotNull(resultado);
        assertEquals(carrito, resultado);
        verify(carritoRepository, never()).save(any());
    }

    @Test
    public void testAddItem_Token() {
        when(carritoRepository.findByToken("token-123")).thenReturn(Optional.empty());
        when(carritoRepository.save(any(Carrito.class))).thenReturn(carrito);
        when(libroRepository.findById(1)).thenReturn(Optional.of(libro));
        when(carritoItemRepository.findByCarritoAndLibro(carrito, libro)).thenReturn(Optional.empty());
        when(carritoRepository.save(any(Carrito.class))).thenReturn(carrito);

        Carrito resultado = carritoService.addItem("token-123", 1, 2);

        assertNotNull(resultado);
        assertEquals(1, carrito.getItems().size());
        verify(carritoRepository, times(2)).save(carrito); // Una para crear, otra para actualizar
    }

    @Test
    public void testGetByToken_Existente() {
        when(carritoRepository.findByToken("token-123")).thenReturn(Optional.of(carrito));

        Carrito resultado = carritoService.getByToken("token-123");

        assertNotNull(resultado);
        assertEquals(carrito, resultado);
    }

    @Test
    public void testGetByToken_Nuevo() {
        when(carritoRepository.findByToken("new-token")).thenReturn(Optional.empty());

        Carrito resultado = carritoService.getByToken("new-token");

        assertNotNull(resultado);
        assertEquals("new-token", resultado.getToken());
        assertEquals(BigDecimal.ZERO, resultado.getSubtotal());
    }

    @Test
    public void testClearByToken() {
        when(carritoRepository.findByToken("token-123")).thenReturn(Optional.empty());
        when(carritoRepository.save(any(Carrito.class))).thenReturn(carrito);

        carritoService.clearByToken("token-123");

        assertTrue(carrito.getItems().isEmpty());
        assertEquals(BigDecimal.ZERO, carrito.getSubtotal());
        verify(carritoRepository, times(1)).save(carrito);
    }
}