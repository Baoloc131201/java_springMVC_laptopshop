package com.example.CURD_java_project.repository;

import com.example.CURD_java_project.model.Order;
import com.example.CURD_java_project.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface OrderRepository extends JpaRepository<Order,Long> {
    List<Order> findOrderByUser(User user);
    Optional<Order> findByStripeSessionId(String stripeSessionId);
}
