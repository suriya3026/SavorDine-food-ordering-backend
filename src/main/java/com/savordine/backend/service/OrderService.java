package com.savordine.backend.service;

import com.savordine.backend.model.Cart;
import com.savordine.backend.model.CartItem;
import com.savordine.backend.model.Order;
import com.savordine.backend.model.OrderItem;
import com.savordine.backend.model.User;
import com.savordine.backend.repository.CartItemRepository;
import com.savordine.backend.repository.CartRepository;
import com.savordine.backend.repository.OrderItemRepository;
import com.savordine.backend.repository.OrderRepository;
import com.savordine.backend.repository.UserRepository;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class OrderService {

    private final OrderRepository orderRepository;
    private final OrderItemRepository orderItemRepository;
    private final CartItemRepository cartItemRepository;
    private final CartRepository cartRepository;
    private final UserRepository userRepository;

    public OrderService(
            OrderRepository orderRepository,
            OrderItemRepository orderItemRepository,
            CartItemRepository cartItemRepository,
            CartRepository cartRepository,
            UserRepository userRepository) {

        this.orderRepository = orderRepository;
        this.orderItemRepository = orderItemRepository;
        this.cartItemRepository = cartItemRepository;
        this.cartRepository = cartRepository;
        this.userRepository = userRepository;
    }

    // =====================================================
    // CREATE ORDER FROM CART
    // =====================================================

    @Transactional
    public Order createOrder(
            Long userId,
            String deliveryAddress) {

        // ---------------------------------------------
        // Validate User
        // ---------------------------------------------

        if (userId == null) {
            throw new RuntimeException("User ID is required");
        }

        User user = userRepository.findById(userId)
                .orElseThrow(() ->
                        new RuntimeException(
                                "User not found: " + userId
                        )
                );

        // ---------------------------------------------
        // Validate Address
        // ---------------------------------------------

        if (deliveryAddress == null ||
                deliveryAddress.trim().isEmpty()) {

            throw new RuntimeException(
                    "Delivery address is required"
            );
        }

        // ---------------------------------------------
        // Get User Cart
        // ---------------------------------------------

        Cart cart = cartRepository.findByUserId(userId)
                .orElseThrow(() ->
                        new RuntimeException(
                                "Cart not found for user: " + userId
                        )
                );

        // ---------------------------------------------
        // Get Cart Items Using Cart ID
        // ---------------------------------------------

        List<CartItem> cartItems =
                cartItemRepository.findByCartId(cart.getId());

        // ---------------------------------------------
        // Check Cart
        // ---------------------------------------------

        if (cartItems == null ||
                cartItems.isEmpty()) {

            throw new RuntimeException(
                    "Cart is empty for user: " + userId
            );
        }

        // ---------------------------------------------
        // Calculate Total
        // ---------------------------------------------

        double totalAmount = 0;

        for (CartItem item : cartItems) {

            if (item == null) {
                continue;
            }

            if (item.getFood() == null) {
                throw new RuntimeException(
                        "Food item not found in cart"
                );
            }

            if (item.getQuantity() == null ||
                    item.getQuantity() <= 0) {

                throw new RuntimeException(
                        "Invalid cart quantity"
                );
            }

            if (item.getFood().getPrice() == null) {

                throw new RuntimeException(
                        "Food price is missing"
                );
            }

            totalAmount +=
                    item.getFood().getPrice()
                    * item.getQuantity();
        }

        // ---------------------------------------------
        // Create Order
        // ---------------------------------------------

        Order order = new Order(
                user,
                totalAmount,
                "PLACED",
                "PENDING",
                deliveryAddress.trim()
        );

        Order savedOrder =
                orderRepository.save(order);

        // ---------------------------------------------
        // Create Order Items
        // ---------------------------------------------

        for (CartItem cartItem : cartItems) {

            OrderItem orderItem =
                    new OrderItem(
                            savedOrder,
                            cartItem.getFood(),
                            cartItem.getQuantity(),
                            cartItem.getFood().getPrice()
                    );

            orderItemRepository.save(orderItem);
        }

        // ---------------------------------------------
        // Clear Cart
        // ---------------------------------------------

        cartItemRepository.deleteByCartId(
                cart.getId()
        );

        return savedOrder;
    }

    // =====================================================
    // GET ALL ORDERS
    // =====================================================

    public List<Order> getAllOrders() {

        return orderRepository.findAll();
    }

    // =====================================================
    // GET USER ORDERS
    // =====================================================

    public List<Order> getOrdersByUser(
            Long userId) {

        return orderRepository.findByUserId(userId);
    }

    // =====================================================
    // GET ORDER BY ID
    // =====================================================

    public Order getOrderById(
            Long id) {

        return orderRepository.findById(id)
                .orElseThrow(() ->
                        new RuntimeException(
                                "Order not found"
                        )
                );
    }

    // =====================================================
    // GET ORDER ITEMS
    // =====================================================

    public List<OrderItem> getOrderItems(
            Long orderId) {

        return orderItemRepository
                .findByOrderId(orderId);
    }

    // =====================================================
    // UPDATE ORDER STATUS
    // =====================================================

    public Order updateOrderStatus(
            Long orderId,
            String status) {

        Order order =
                orderRepository.findById(orderId)
                        .orElseThrow(() ->
                                new RuntimeException(
                                        "Order not found"
                                )
                        );

        if (status == null ||
                status.trim().isEmpty()) {

            throw new RuntimeException(
                    "Order status is required"
            );
        }

        order.setOrderStatus(
                status.trim()
        );

        return orderRepository.save(order);
    }

    // =====================================================
    // UPDATE PAYMENT STATUS
    // =====================================================

    public Order updatePaymentStatus(
            Long orderId,
            String paymentStatus) {

        Order order =
                orderRepository.findById(orderId)
                        .orElseThrow(() ->
                                new RuntimeException(
                                        "Order not found"
                                )
                        );

        if (paymentStatus == null ||
                paymentStatus.trim().isEmpty()) {

            throw new RuntimeException(
                    "Payment status is required"
            );
        }

        order.setPaymentStatus(
                paymentStatus.trim()
        );

        return orderRepository.save(order);
    }

    // =====================================================
    // DELETE ORDER
    // =====================================================

    @Transactional
    public void deleteOrder(
            Long orderId) {

        if (!orderRepository.existsById(orderId)) {

            throw new RuntimeException(
                    "Order not found"
            );
        }

        orderItemRepository
                .findByOrderId(orderId)
                .forEach(
                        orderItemRepository::delete
                );

        orderRepository.deleteById(orderId);
    }
}