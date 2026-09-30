package com.savordine.backend.controller;

import com.savordine.backend.model.Category;
import com.savordine.backend.service.CategoryService;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/categories")
public class CategoryController {

    private final CategoryService categoryService;

    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    // ========================================
    // GET ALL CATEGORIES
    // ========================================

    @GetMapping
    public ResponseEntity<List<Category>> getAllCategories() {

        return ResponseEntity.ok(
                categoryService.getAllCategories()
        );
    }

    // ========================================
    // GET CATEGORY BY ID
    // ========================================

    @GetMapping("/{id}")
    public ResponseEntity<?> getCategoryById(
            @PathVariable Long id) {

        return categoryService.getCategoryById(id)
                .map(ResponseEntity::ok)
                .orElseGet(() ->
                        ResponseEntity.notFound().build()
                );
    }

    // ========================================
    // GET CATEGORY BY NAME
    // ========================================

    @GetMapping("/name/{name}")
    public ResponseEntity<?> getCategoryByName(
            @PathVariable String name) {

        return categoryService.getCategoryByName(name)
                .map(ResponseEntity::ok)
                .orElseGet(() ->
                        ResponseEntity.notFound().build()
                );
    }

    // ========================================
    // CREATE CATEGORY - ADMIN
    // ========================================

    @PostMapping
    public ResponseEntity<Category> createCategory(
            @RequestBody Category category) {

        return ResponseEntity.ok(
                categoryService.createCategory(category)
        );
    }

    // ========================================
    // UPDATE CATEGORY - ADMIN
    // ========================================

    @PutMapping("/{id}")
    public ResponseEntity<?> updateCategory(
            @PathVariable Long id,
            @RequestBody Category category) {

        try {

            return ResponseEntity.ok(
                    categoryService.updateCategory(
                            id,
                            category
                    )
            );

        } catch (RuntimeException e) {

            return ResponseEntity
                    .notFound()
                    .build();
        }
    }

    // ========================================
    // DELETE CATEGORY - ADMIN
    // ========================================

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteCategory(
            @PathVariable Long id) {

        try {

            categoryService.deleteCategory(id);

            return ResponseEntity.ok(
                    "Category deleted successfully"
            );

        } catch (RuntimeException e) {

            return ResponseEntity
                    .notFound()
                    .build();
        }
    }
}