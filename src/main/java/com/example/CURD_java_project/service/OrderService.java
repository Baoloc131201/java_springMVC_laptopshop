package com.example.CURD_java_project.service;

import com.example.CURD_java_project.model.Order;
import com.example.CURD_java_project.model.User;

import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.Optional;

public interface OrderService {
    Order handlePlaceOrder(User currentUser, HttpSession session,String receiverName,String receiverAddress,String receiverPhone);
    void handlePlaceOrderService(User currentUser, HttpSession session, String receiverName, String receiverAddress, String receiverPhone);
    List<Order> getAllOrder();
    Optional<Order>getOrderById(long id);
    void updateOrder(Order order);
    void deleteOrder(long id);
    List<Order> getOrderByUser(User user);
    void attachStripeSession(Long orderId, String sessionId);
    void markPaidBySession(String sessionId);
    void markPaid(Long orderId);
}
