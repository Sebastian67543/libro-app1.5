package com.distribuida.service;

import com.distribuida.dao.CarritoItemRepository;
import com.distribuida.dao.CarritoRepository;
import com.distribuida.dao.ClienteRepository;
import com.distribuida.dao.LibroRepository;
import com.distribuida.model.Carrito;
import com.distribuida.model.CarritoItem;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.UUID;


@Service
public class CarritoServiceImpl implements CarritoService{


    private final CarritoRepository carritoRepository;
    private final CarritoItemRepository carritoItemRepository;
    private final ClienteRepository clienteRepository;
    private final LibroRepository libroRepository;

    private static final BigDecimal IVA = new BigDecimal("0.15");

    public CarritoServiceImpl(CarritoRepository carritoRepository
                              , CarritoItemRepository carritoItemRepository
                              , ClienteRepository clienteRepository
                              ,LibroRepository libroRepository
                            ){
        this.carritoRepository = carritoRepository;
        this.carritoItemRepository = carritoItemRepository;
        this.clienteRepository = clienteRepository;
        this.libroRepository = libroRepository;

    }

    @Override
    @Transactional
    public Carrito getOrCreateByClienteId(int clienteId, String token) {
        var cliente = clienteRepository.findById(clienteId)
                .orElseThrow(() -> new IllegalArgumentException("Cliente no encontrado"+ clienteId));
        var carritoOpt = carritoRepository.findByCliente(cliente);
        if (carritoOpt.isPresent()) return carritoOpt.get();

        var carrito = new Carrito();
        carrito.setCliente(cliente);
        carrito.setToken(token);
        carrito.recomprobacionTotalesCompat();

        return carritoRepository.save(carrito);
    }

    @Override
    @Transactional
    public Carrito addItem(int clienteId, int libroId, int cantidad) {

        if (cantidad <= 0) throw new IllegalArgumentException("Cantidad debe ser > 0");

        var carrito = getOrCreateByClienteId(clienteId, null);
        var libro = libroRepository.findById(libroId)
                .orElseThrow(() -> new IllegalArgumentException("Libro no encontrado: "+ libroId));

        var itemOpt = carritoItemRepository.findByCarritoAndLibro(carrito, libro);
        if (itemOpt.isPresent()){
            var item = itemOpt.get();
            item.setCantidad(item.getCantidad() + cantidad);
            item.setPrecioUnitario(BigDecimal.valueOf(libro.getPrecio()));
            item.calcTotal();
            carritoItemRepository.save(item);
        }else {
            var item = new CarritoItem();
            item.setCarrito(carrito);
            item.setLibro(libro);
            item.setCantidad(cantidad);
            item.setPrecioUnitario(BigDecimal.valueOf(libro.getPrecio()));
            item.calcTotal();
            carrito.getItems().add(item);
        }



        carrito.recomputarTotales(IVA);
        return carritoRepository.save(carrito);
    }

    @Override
    @Transactional
    public Carrito updateItemCantidad(int clienteId, long carritoItemId, int nuevaCantidad) {
        if (nuevaCantidad < 0) throw new IllegalArgumentException("Cantidad no puede ser negativa");

        var carrito = getByClienteId(clienteId);
        var item = carritoItemRepository.findById(carritoItemId)
                .orElseThrow(() -> new IllegalArgumentException("Item no encontrado"+carritoItemId));
        if (nuevaCantidad == 0){
            carrito.getItems().remove(item);
            carritoItemRepository.delete(item);
        }else {
            item.setCantidad(nuevaCantidad);
            carritoItemRepository.save(item);
        }
        carrito.recomputarTotales(IVA);
        return carritoRepository.save(carrito);
    }

    @Override
    @Transactional
    public void removeItem(int clienteId, long carritoItemId) {
    updateItemCantidad(clienteId, carritoItemId, 0);
    }

    @Override
    @Transactional
    public void clear(int clienteId) {
    var carrito = getByClienteId(clienteId);
    carrito.getItems().clear();
    carrito.recomputarTotales(IVA);
    carritoRepository.save(carrito);
    }

    @Override
    public Carrito getByClienteId(int clienteId) {
        var cliente = clienteRepository.findById(clienteId)
                .orElseThrow(() -> new IllegalArgumentException("Cliente no encontrado"+clienteId));
        return carritoRepository.findByCliente(cliente)
                .orElseGet(() -> {
                    var c = new Carrito();
                    c.setCliente(cliente);
                    return c;
                });
    }

    @Override
    @Transactional
    public Carrito getOrCreateByToken(String token) {
        // Si no se recibe token, se genera uno único
        if(token == null || token.isEmpty()) {
            token = UUID.randomUUID().toString();
        }

        String finalToken = token;
        return carritoRepository.findByToken(token)
                .orElseGet(() -> {
                    var c = new Carrito();
                    c.setToken(finalToken);
                    c.setSubtotal(BigDecimal.ZERO);
                    c.setDescuento(BigDecimal.ZERO);
                    c.setImpuestos(BigDecimal.ZERO);
                    c.setTotal(BigDecimal.ZERO);
                    c.setItems(new ArrayList<>()); // inicializa la lista de items
                    return carritoRepository.save(c);
                });
    }


    @Override
    @Transactional
    public Carrito addItem(String token, int libroId, int cantidad) {
        if (cantidad <= 0) throw new IllegalArgumentException("Cantidad debe ser > 0 ");
        var carrito = getOrCreateByToken(token);
        var libro = libroRepository.findById(libroId)
                .orElseThrow(() -> new IllegalArgumentException("Libro no encontrado: "+libroId));
        var itemOpt = carritoItemRepository.findByCarritoAndLibro(carrito, libro);
        if (itemOpt.isPresent()) {
            var item = itemOpt.get();
            item.setCantidad(item.getCantidad() + cantidad);
            item.setPrecioUnitario(BigDecimal.valueOf(libro.getPrecio()));
            item.calcTotal();
            carritoItemRepository.save(item);
        }else {
            var item = new CarritoItem();
            item.setCarrito(carrito);
            item.setLibro(libro);
            item.setCantidad(cantidad);
            item.setPrecioUnitario(BigDecimal.valueOf(libro.getPrecio()));
            item.calcTotal();
            carrito.getItems().add(item);
        }
        carrito.recomputarTotales(IVA);
        return carritoRepository.save(carrito);
    }

    @Override
    public Carrito updateItemCantidad(String token, long carritoItemId, int nuevaCantidad) {
        var carrito = getOrCreateByToken(token);
        var item = carritoItemRepository.findById(carritoItemId)
                .orElseThrow(() -> new IllegalArgumentException("Item no encontrado: "+ carritoItemId));

        if (nuevaCantidad <= 0 ){
            carrito.getItems().remove(item);
            carritoItemRepository.delete(item);
        }else {
            item.setCantidad(nuevaCantidad);
            item.calcTotal();
            carritoItemRepository.save(item);
        }
        carrito.recomputarTotales(IVA);
        return carritoRepository.save(carrito);
    }

    @Override
    @Transactional
    public void removeItem(String token, long carritoItemId) {
        updateItemCantidad(token, carritoItemId, 0);
    }

    @Override
    @Transactional
    public void clearByToken(String token) {
        var carrito = getOrCreateByToken(token);
        carrito.getItems().clear();
        carrito.setSubtotal(BigDecimal.ZERO);
        carrito.setDescuento(BigDecimal.ZERO);
        carrito.setImpuestos(BigDecimal.ZERO);
        carrito.setTotal(BigDecimal.ZERO);
        carritoRepository.save(carrito);
    }

    @Override
    @Transactional
    public Carrito getByToken(String token) {

        return carritoRepository.findByToken(token)
                .orElseGet(() -> {
                    var c = new Carrito();
                    c.setToken(token);
                    c.setSubtotal(BigDecimal.ZERO);
                    c.setDescuento(BigDecimal.ZERO);
                    c.setImpuestos(BigDecimal.ZERO);
                    c.setTotal(BigDecimal.ZERO);
                    return c;
                });
    }
}
