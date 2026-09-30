package com.savordine.backend.service;

import com.savordine.backend.model.Food;
import com.savordine.backend.repository.FoodRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class FoodService {

    private final FoodRepository foodRepository;

    public FoodService(FoodRepository foodRepository) {
        this.foodRepository = foodRepository;
    }

    // Get all foods
    public List<Food> getAllFoods() {
        return foodRepository.findAll();
    }

    // Get food by ID
    public Optional<Food> getFoodById(Long id) {
        return foodRepository.findById(id);
    }

    // Get foods by category
    public List<Food> getFoodsByCategory(Long categoryId) {
        return foodRepository.findByCategoryId(categoryId);
    }

    // Get available foods
    public List<Food> getAvailableFoods() {
        return foodRepository.findByAvailableTrue();
    }

    // Search foods
    public List<Food> searchFoods(String name) {
        return foodRepository.findByNameContainingIgnoreCase(name);
    }

    // Create food
    public Food createFood(Food food) {
        return foodRepository.save(food);
    }

    // Update food
    public Food updateFood(Long id, Food updatedFood) {

        Food existingFood = foodRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Food not found"));

        existingFood.setName(updatedFood.getName());
        existingFood.setDescription(updatedFood.getDescription());
        existingFood.setPrice(updatedFood.getPrice());
        existingFood.setImage(updatedFood.getImage());
        existingFood.setAvailable(updatedFood.isAvailable());
        existingFood.setCategory(updatedFood.getCategory());

        return foodRepository.save(existingFood);
    }

    // Delete food
    public void deleteFood(Long id) {

        if (!foodRepository.existsById(id)) {
            throw new RuntimeException("Food not found");
        }

        foodRepository.deleteById(id);
    }
}