package com.savordine.backend.repository;

import com.savordine.backend.model.CartItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CartItemRepository extends JpaRepository<CartItem, Long> {

    List<CartItem> findByCartId(Long cartId);

    Optional<CartItem> findByCartIdAndFoodId(Long cartId, Long foodId);

    void deleteByCartId(Long cartId);

    void deleteByCartIdAndFoodId(Long cartId, Long foodId);
}