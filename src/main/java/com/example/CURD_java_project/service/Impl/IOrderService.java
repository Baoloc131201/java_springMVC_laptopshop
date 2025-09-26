package com.example.CURD_java_project.service.Impl;

import com.example.CURD_java_project.model.*;
import com.example.CURD_java_project.repository.CartDetailRepository;
import com.example.CURD_java_project.repository.CartRepository;
import com.example.CURD_java_project.repository.OrderDetailRepository;
import com.example.CURD_java_project.repository.OrderRepository;
import com.example.CURD_java_project.service.OrderService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.Optional;

@Service
public class IOrderService implements OrderService {

    private CartRepository cartRepository;
    private CartDetailRepository cartDetailRepository;
    private OrderRepository orderRepository;
    private OrderDetailRepository orderDetailRepository;

    public IOrderService(CartRepository cartRepository, CartDetailRepository cartDetailRepository, OrderRepository orderRepository, OrderDetailRepository orderDetailRepository) {
        this.cartRepository = cartRepository;
        this.cartDetailRepository = cartDetailRepository;
        this.orderRepository = orderRepository;
        this.orderDetailRepository = orderDetailRepository;
    }


    @Override
    public Order handlePlaceOrder(User currentUser, HttpSession session, String receiverName, String receiverAddress, String receiverPhone) {
         // get cart by user
//        Cart cart = this.cartRepository.findByUser(currentUser);
//        if(cart != null){
//            List<CartDetail> cartDetails = cart.getCartDetails();
//            if(cartDetails != null){
//                // Create Order
//                Order order = new Order();
//                order.setUser(currentUser);
//                order.setReceiverName(receiverName);
//                order.setReceiverAddress(receiverAddress);
//                order.setReceiverPhone(receiverPhone);
//                order.setStatus("PENDING");
//
//                double totalPrice = 0;
//                for(CartDetail cd : cartDetails){
//                    totalPrice += cd.getPrice() * cd.getQuantity();
//                }
//
//                order.setTotalPrice(totalPrice);
//                order = this.orderRepository.saveAndFlush(order);
//
//            // Create OrderDetail
//                for(CartDetail cd : cartDetails){
//                    OrderDetail orderDetail = new OrderDetail();
//                    orderDetail.setOrder(order);
//                    orderDetail.setProduct(cd.getProduct());
//                    orderDetail.setPrice(cd.getPrice());
//                    orderDetail.setQuantity(cd.getQuantity());
//
//                    this.orderDetailRepository.saveAndFlush(orderDetail);
//                }
//            // Deleted CartDetail and Cart
//                for(CartDetail cd : cartDetails){
//                    this.cartDetailRepository.deleteById(cd.getId());
//                }
//
//                this.cartRepository.deleteById(cart.getId());
//
//                session.setAttribute("sumQuantity", 0);
//            }
//
//        }
        Order order = new Order();

        Cart cart = this.cartRepository.findByUser(currentUser);

        if (cart != null) {

            List<CartDetail> cartDetails = cart.getCartDetails();

            if (cartDetails == null || cartDetails.isEmpty()) return null;
            order.setUser(currentUser);
            order.setReceiverName(receiverName);
            order.setReceiverAddress(receiverAddress);
            order.setReceiverPhone(receiverPhone);
            order.setStatus("PENDING");

            double totalPrice = 0;
            for (CartDetail cd : cartDetails) {
                totalPrice += cd.getPrice() * cd.getQuantity();
            }
            order.setTotalPrice(totalPrice);

            // Cần id ngay -> saveAndFlush để có order.getId()
            order = this.orderRepository.saveAndFlush(order);

            for (CartDetail cd : cartDetails) {
                OrderDetail od = new OrderDetail();
                od.setOrder(order);
                od.setProduct(cd.getProduct());
                od.setPrice(cd.getPrice());
                od.setQuantity(cd.getQuantity());
                this.orderDetailRepository.saveAndFlush(od);
            }

            for (CartDetail cd : cartDetails) this.cartDetailRepository.deleteById(cd.getId());
            this.cartRepository.deleteById(cart.getId());
            session.setAttribute("sumQuantity", 0);
        }
        return order;
    }

    @Override
    public void handlePlaceOrderService(User currentUser, HttpSession session, String receiverName, String receiverAddress, String receiverPhone) {
        // get cart by user
        Cart cart = this.cartRepository.findByUser(currentUser);
        if(cart != null){
            List<CartDetail> cartDetails = cart.getCartDetails();
            if(cartDetails != null){
                // Create Order
                Order order = new Order();
                order.setUser(currentUser);
                order.setReceiverName(receiverName);
                order.setReceiverAddress(receiverAddress);
                order.setReceiverPhone(receiverPhone);
                order.setStatus("PENDING");

                double totalPrice = 0;
                for(CartDetail cd : cartDetails){
                    totalPrice += cd.getPrice() * cd.getQuantity();
                }

                order.setTotalPrice(totalPrice);
                order = this.orderRepository.saveAndFlush(order);

                // Create OrderDetail
                for(CartDetail cd : cartDetails){
                    OrderDetail orderDetail = new OrderDetail();
                    orderDetail.setOrder(order);
                    orderDetail.setProduct(cd.getProduct());
                    orderDetail.setPrice(cd.getPrice());
                    orderDetail.setQuantity(cd.getQuantity());

                    this.orderDetailRepository.saveAndFlush(orderDetail);
                }
                // Deleted CartDetail and Cart
                for(CartDetail cd : cartDetails){
                    this.cartDetailRepository.deleteById(cd.getId());
                }

                this.cartRepository.deleteById(cart.getId());

                session.setAttribute("sumQuantity", 0);
            }
        }
    }

    @Override
    public List<Order> getAllOrder() {
        return this.orderRepository.findAll();
    }

    @Override
    public Optional<Order> getOrderById(long id) {
        Optional<Order> order = this.orderRepository.findById(id);
        return order;
    }

    @Override
    public void updateOrder(Order order) {
        Optional<Order> currentOrder = this.orderRepository.findById(order.getId());
        if(currentOrder.isPresent()){
            Order orderUpdate = currentOrder.get();
            orderUpdate.setStatus(order.getStatus());
            this.orderRepository.saveAndFlush(orderUpdate);
        }
    }

    @Override
    public void deleteOrder(long id) {
        Optional<Order> currentOrder = this.orderRepository.findById(id);
        if(currentOrder.isPresent()){
            Order order = currentOrder.get();
            List<OrderDetail> orderDetails = order.getOrderDetails();
            for(OrderDetail orderDetail: orderDetails){
                this.orderDetailRepository.deleteById(orderDetail.getId());
            }
        }
        this.orderRepository.deleteById(id);
    }

    @Override
    public List<Order> getOrderByUser(User user) {
        return this.orderRepository.findOrderByUser(user);
    }

    @Transactional
    public void attachStripeSession(Long orderId, String sessionId) {
        Order o = orderRepository.findById(orderId)
                .orElseThrow(() -> new IllegalArgumentException("Order not found"));
        o.setStripeSessionId(sessionId);
        orderRepository.save(o);
    }

    @Transactional
    public void markPaidBySession(String sessionId) {
        Order o = orderRepository.findByStripeSessionId(sessionId)
                .orElseThrow(() -> new IllegalArgumentException("Session not found"));
        o.setStatus("PAID");
        orderRepository.save(o);
    }

    @Transactional
    public void markPaid(Long orderId) {
        Order o = orderRepository.findById(orderId).orElseThrow();
        o.setStatus("PAID");
        orderRepository.save(o);
    }
}
