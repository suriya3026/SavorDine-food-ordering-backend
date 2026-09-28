package com.savordine.backend.controller;

import com.savordine.backend.model.Food;
import com.savordine.backend.service.FoodService;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/foods")
@CrossOrigin(origins = "http://localhost:3000")
public class FoodController {

    private final FoodService foodService;

    public FoodController(FoodService foodService) {
        this.foodService = foodService;
    }

    // Get all foods
    @GetMapping
    public ResponseEntity<List<Food>> getAllFoods() {
        return ResponseEntity.ok(
                foodService.getAllFoods()
        );
    }

    // Get food by ID
    @GetMapping("/{id}")
    public ResponseEntity<?> getFoodById(
            @PathVariable Long id) {

        return foodService.getFoodById(id)
                .map(ResponseEntity::ok)
                .orElseGet(() ->
                        ResponseEntity.notFound().build());
    }

    // Get foods by category
    @GetMapping("/category/{categoryId}")
    public ResponseEntity<List<Food>> getFoodsByCategory(
            @PathVariable Long categoryId) {

        return ResponseEntity.ok(
                foodService.getFoodsByCategory(categoryId)
        );
    }

    // Get available foods
    @GetMapping("/available")
    public ResponseEntity<List<Food>> getAvailableFoods() {

        return ResponseEntity.ok(
                foodService.getAvailableFoods()
        );
    }

    // Search foods
    @GetMapping("/search")
    public ResponseEntity<List<Food>> searchFoods(
            @RequestParam String name) {

        return ResponseEntity.ok(
                foodService.searchFoods(name)
        );
    }

    // Create food - Admin
    @PostMapping
    public ResponseEntity<Food> createFood(
            @RequestBody Food food) {

        return ResponseEntity.ok(
                foodService.createFood(food)
        );
    }

    // Update food - Admin
    @PutMapping("/{id}")
    public ResponseEntity<?> updateFood(
            @PathVariable Long id,
            @RequestBody Food food) {

        try {

            return ResponseEntity.ok(
                    foodService.updateFood(id, food)
            );

        } catch (RuntimeException e) {

            return ResponseEntity
                    .notFound()
                    .build();
        }
    }

    // Delete food - Admin
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteFood(
            @PathVariable Long id) {

        try {

            foodService.deleteFood(id);

            return ResponseEntity.ok(
                    "Food deleted successfully"
            );

        } catch (RuntimeException e) {

            return ResponseEntity
                    .notFound()
                    .build();
        }
    }
}