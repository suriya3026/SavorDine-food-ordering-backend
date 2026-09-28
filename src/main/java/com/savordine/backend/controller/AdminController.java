
package com.savordine.backend.controller;

import com.savordine.backend.dto.AdminLoginRequest;
import com.savordine.backend.model.Food;
import com.savordine.backend.model.Order;
import com.savordine.backend.model.User;
import com.savordine.backend.service.AdminService;
import com.savordine.backend.service.FoodService;
import com.savordine.backend.service.OrderService;
import com.savordine.backend.service.UserService;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/admin")
@CrossOrigin(origins = "http://localhost:3000")
public class AdminController {

    private final UserService userService;
    private final FoodService foodService;
    private final OrderService orderService;
    private final AdminService adminService;

    public AdminController(
            UserService userService,
            FoodService foodService,
            OrderService orderService,
            AdminService adminService) {

        this.userService = userService;
        this.foodService = foodService;
        this.orderService = orderService;
        this.adminService = adminService;
    }

    // =========================
    // ADMIN LOGIN
    // =========================

    @PostMapping("/login")
    public ResponseEntity<Map<String, Object>> adminLogin(
            @RequestBody AdminLoginRequest loginRequest) {

        Map<String, Object> response = new HashMap<>();

        boolean validLogin = adminService.login(
                loginRequest.getUsername(),
                loginRequest.getPassword()
        );

        if (validLogin) {
            response.put("success", true);
            response.put("message", "Admin login successful");
        } else {
            response.put("success", false);
            response.put("message", "Invalid username or password");
        }

        return ResponseEntity.ok(response);
    }

    // =========================
    // USERS
    // =========================

    @GetMapping("/users")
    public ResponseEntity<List<User>> getAllUsers() {
        return ResponseEntity.ok(userService.getAllUsers());
    }

    @DeleteMapping("/users/{id}")
    public ResponseEntity<?> deleteUser(@PathVariable Long id) {

        try {
            userService.deleteUser(id);
            return ResponseEntity.ok("User deleted successfully");

        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    // =========================
    // FOODS
    // =========================

    @GetMapping("/foods")
    public ResponseEntity<List<Food>> getAllFoods() {
        return ResponseEntity.ok(foodService.getAllFoods());
    }

    @DeleteMapping("/foods/{id}")
    public ResponseEntity<?> deleteFood(@PathVariable Long id) {

        try {
            foodService.deleteFood(id);
            return ResponseEntity.ok("Food deleted successfully");

        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    // =========================
    // ORDERS
    // =========================

    @GetMapping("/orders")
    public ResponseEntity<List<Order>> getAllOrders() {
        return ResponseEntity.ok(orderService.getAllOrders());
    }

    @PutMapping("/orders/{orderId}/status")
    public ResponseEntity<?> updateOrderStatus(
            @PathVariable Long orderId,
            @RequestParam String status) {

        try {
            return ResponseEntity.ok(
                    orderService.updateOrderStatus(orderId, status)
            );

        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @DeleteMapping("/orders/{orderId}")
    public ResponseEntity<?> deleteOrder(
            @PathVariable Long orderId) {

        try {
            orderService.deleteOrder(orderId);
            return ResponseEntity.ok("Order deleted successfully");

        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}