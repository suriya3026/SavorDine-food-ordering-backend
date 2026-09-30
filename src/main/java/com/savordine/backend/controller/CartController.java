package com.savordine.backend.controller;

import com.savordine.backend.dto.CartRequest;
import com.savordine.backend.model.CartItem;
import com.savordine.backend.service.CartService;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/cart")
public class CartController {

    private final CartService cartService;

    public CartController(CartService cartService) {
        this.cartService = cartService;
    }

    // ==========================================
    // GET CART ITEMS
    // ==========================================

    @GetMapping("/{userId}")
    public ResponseEntity<List<CartItem>> getCart(
            @PathVariable Long userId) {

        return ResponseEntity.ok(
                cartService.getCartItems(userId)
        );
    }


    // ==========================================
    // ADD FOOD TO CART
    // ==========================================

    @PostMapping("/add")
    public ResponseEntity<?> addToCart(
            @RequestBody CartRequest request) {

        try {

            CartItem cartItem = cartService.addToCart(
                    request.getUserId(),
                    request.getFoodId(),
                    request.getQuantity()
            );

            return ResponseEntity.ok(cartItem);

        } catch (RuntimeException e) {

            return ResponseEntity
                    .badRequest()
                    .body(e.getMessage());
        }
    }


    // ==========================================
    // UPDATE CART QUANTITY
    // ==========================================

    @PutMapping("/update")
    public ResponseEntity<?> updateQuantity(
            @RequestBody CartRequest request) {

        try {

            CartItem cartItem = cartService.updateQuantity(
                    request.getUserId(),
                    request.getFoodId(),
                    request.getQuantity()
            );

            return ResponseEntity.ok(cartItem);

        } catch (RuntimeException e) {

            return ResponseEntity
                    .badRequest()
                    .body(e.getMessage());
        }
    }


    // ==========================================
    // REMOVE SINGLE FOOD
    // ==========================================

    @DeleteMapping("/remove")
    public ResponseEntity<?> removeFromCart(
            @RequestParam Long userId,
            @RequestParam Long foodId) {

        try {

            cartService.removeFromCart(
                    userId,
                    foodId
            );

            return ResponseEntity.ok(
                    "Food removed from cart"
            );

        } catch (RuntimeException e) {

            return ResponseEntity
                    .badRequest()
                    .body(e.getMessage());
        }
    }


    // ==========================================
    // CLEAR ENTIRE CART
    // ==========================================

    @DeleteMapping("/clear/{userId}")
    public ResponseEntity<?> clearCart(
            @PathVariable Long userId) {

        try {

            cartService.clearCart(userId);

            return ResponseEntity.ok(
                    "Cart cleared successfully"
            );

        } catch (RuntimeException e) {

            return ResponseEntity
                    .badRequest()
                    .body(e.getMessage());
        }
    }
}
