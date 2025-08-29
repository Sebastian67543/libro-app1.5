package com.distribuida.controller;

import com.distribuida.model.Carrito;
import com.distribuida.model.Factura;
import com.distribuida.service.CarritoService;
import com.distribuida.service.GuestCheckoutService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/api/guest/checkout")
public class GuestCheckoutController {

    private final GuestCheckoutService guestCheckoutService;
    private final CarritoService carritoService; // << agregar

    public GuestCheckoutController(GuestCheckoutService checkoutService,
                                   CarritoService carritoService){ // << inyectar
        this.guestCheckoutService = checkoutService;
        this.carritoService = carritoService;
    }

    @PostMapping
    public ResponseEntity<?> checkout(@RequestParam String token){
        try {
            // Obtener o crear carrito
            Carrito carrito = carritoService.getOrCreateByToken(token);

            if (carrito.getItems() == null || carrito.getItems().isEmpty()) {
                return ResponseEntity
                        .badRequest()
                        .body(Map.of("message", "Carrito vacío, no se puede procesar el checkout"));
            }

            Factura factura = guestCheckoutService.checkoutByToken(token);
            return ResponseEntity.ok(factura);

        } catch (IllegalArgumentException e) {
            return ResponseEntity
                    .badRequest()
                    .body(Map.of("message", e.getMessage()));
        } catch (Exception e) {
            e.printStackTrace(); // Para depuración
            return ResponseEntity
                    .status(500)
                    .body(Map.of("message", "Error interno en el servidor"));
        }
    }

}