package com.savordine.backend.controller;

import com.savordine.backend.dto.OrderRequest;
import com.savordine.backend.model.Order;
import com.savordine.backend.model.OrderItem;
import com.savordine.backend.service.OrderService;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/orders")
@CrossOrigin(origins = "http://localhost:3000")
public class OrderController {

    private final OrderService orderService;

    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    // Create new order from cart
    @PostMapping
    public ResponseEntity<?> createOrder(
            @RequestBody OrderRequest request) {

        try {
            Order order = orderService.createOrder(
                    request.getUserId(),
                    request.getDeliveryAddress()
            );

            return ResponseEntity.ok(order);

        } catch (RuntimeException e) {
            return ResponseEntity
                    .badRequest()
                    .body(e.getMessage());
        }
    }

    // Get all orders - Admin
    @GetMapping
    public ResponseEntity<List<Order>> getAllOrders() {

        return ResponseEntity.ok(
                orderService.getAllOrders()
        );
    }

    // Get orders of a particular user
    @GetMapping("/user/{userId}")
    public ResponseEntity<List<Order>> getOrdersByUser(
            @PathVariable Long userId) {

        return ResponseEntity.ok(
                orderService.getOrdersByUser(userId)
        );
    }

    // Get single order
    @GetMapping("/{id}")
    public ResponseEntity<?> getOrderById(
            @PathVariable Long id) {

        try {
            return ResponseEntity.ok(
                    orderService.getOrderById(id)
            );

        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    // Get order items
    @GetMapping("/{orderId}/items")
    public ResponseEntity<List<OrderItem>> getOrderItems(
            @PathVariable Long orderId) {

        return ResponseEntity.ok(
                orderService.getOrderItems(orderId)
        );
    }

    // Update order status - Admin
    @PutMapping("/{orderId}/status")
    public ResponseEntity<?> updateOrderStatus(
            @PathVariable Long orderId,
            @RequestParam String status) {

        try {
            return ResponseEntity.ok(
                    orderService.updateOrderStatus(
                            orderId,
                            status
                    )
            );

        } catch (RuntimeException e) {
            return ResponseEntity
                    .badRequest()
                    .body(e.getMessage());
        }
    }

    // Update payment status
    @PutMapping("/{orderId}/payment")
    public ResponseEntity<?> updatePaymentStatus(
            @PathVariable Long orderId,
            @RequestParam String paymentStatus) {

        try {
            return ResponseEntity.ok(
                    orderService.updatePaymentStatus(
                            orderId,
                            paymentStatus
                    )
            );

        } catch (RuntimeException e) {
            return ResponseEntity
                    .badRequest()
                    .body(e.getMessage());
        }
    }

    // Delete order - Admin
    @DeleteMapping("/{orderId}")
    public ResponseEntity<?> deleteOrder(
            @PathVariable Long orderId) {

        try {

            orderService.deleteOrder(orderId);

            return ResponseEntity.ok(
                    "Order deleted successfully"
            );

        } catch (RuntimeException e) {

            return ResponseEntity
                    .badRequest()
                    .body(e.getMessage());
        }
    }
}