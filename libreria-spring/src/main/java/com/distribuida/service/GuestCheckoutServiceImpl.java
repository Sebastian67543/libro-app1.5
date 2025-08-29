package com.distribuida.service;


import com.distribuida.dao.CarritoRepository;
import com.distribuida.dao.FacturaDetalleRepository;
import com.distribuida.dao.FacturaRepository;
import com.distribuida.dao.LibroRepository;
import com.distribuida.model.Factura;
import com.distribuida.service.util.CheckoutMapper;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Service
public class GuestCheckoutServiceImpl implements GuestCheckoutService {

    private final CarritoRepository carritoRepository;
    private final FacturaRepository facturaRepository;
    private final FacturaDetalleRepository facturaDetalleRepository;
    private final LibroRepository libroRepository;

    private static final double IVA = 0.15d;

    public GuestCheckoutServiceImpl(
            CarritoRepository carritoRepository,
            FacturaRepository facturaRepository,
            FacturaDetalleRepository facturaDetalleRepository,
            LibroRepository libroRepository
    ){
        this.carritoRepository = carritoRepository;
        this.facturaRepository = facturaRepository;
        this.facturaDetalleRepository = facturaDetalleRepository;
        this.libroRepository = libroRepository;

    }

    @Override
    @Transactional
    public Factura checkoutByToken(String token) {
        // Validar token
        if (token == null || token.isEmpty()) {
            throw new IllegalArgumentException("Token inválido");
        }

        // Obtener carrito
        var carrito = carritoRepository.findByToken(token)
                .orElseThrow(() -> new IllegalArgumentException("No existe carrito para el token"));

        // Validar que tenga items
        if (carrito.getItems() == null || carrito.getItems().isEmpty()) {
            throw new IllegalArgumentException("El carrito está vacío");
        }

        System.out.println("Procesando checkout para token: " + token + ", items: " + carrito.getItems().size());

        // Validar stock
        for (var item : carrito.getItems()) {
            var libro = item.getLibro();
            if (libro.getNumEjemplares() < item.getCantidad()) {
                throw new IllegalArgumentException("Stock insuficiente para: " + libro.getTitulo());
            }
        }

        // Actualizar stock de libros
        for (var item : carrito.getItems()) {
            var libro = item.getLibro();
            libro.setNumEjemplares(libro.getNumEjemplares() - item.getCantidad());
            libroRepository.save(libro);
        }

        // Generar número de factura
        String numFactura = "F-" + DateTimeFormatter.ofPattern("yyyyMMddHHmmss").format(LocalDateTime.now());

        // Construir factura
        var factura = CheckoutMapper.construirFacturaDesdeCarrito(carrito, numFactura, IVA);
        factura = facturaRepository.save(factura);

        // Guardar detalles de factura
        for (var item : carrito.getItems()) {
            var detalle = CheckoutMapper.construirDetalle(factura, item);
            facturaDetalleRepository.save(detalle);
        }

        // Vaciar carrito
        carrito.getItems().clear();
        carritoRepository.save(carrito);

        System.out.println("Checkout completado para token: " + token + ", factura: " + numFactura);

        return factura;
    }

}
