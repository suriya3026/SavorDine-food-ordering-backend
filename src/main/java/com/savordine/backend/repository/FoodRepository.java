package com.savordine.backend.repository;

import com.savordine.backend.model.Food;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FoodRepository extends JpaRepository<Food, Long> {

    List<Food> findByCategoryId(Long categoryId);

    List<Food> findByAvailableTrue();

    List<Food> findByNameContainingIgnoreCase(String name);
}