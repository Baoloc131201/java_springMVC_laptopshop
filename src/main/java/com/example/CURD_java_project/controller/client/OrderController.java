package com.example.CURD_java_project.controller.client;

import com.example.CURD_java_project.model.Cart;
import com.example.CURD_java_project.model.Order;
import com.example.CURD_java_project.model.User;
import com.example.CURD_java_project.service.OrderService;
import com.stripe.exception.StripeException;
import com.stripe.model.PaymentIntent;
import com.stripe.model.checkout.Session;
import com.stripe.param.checkout.SessionCreateParams;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;

@Controller
public class OrderController {
    private OrderService orderService;

    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @PostMapping("/place-order")
    public String handlePlaceOrder(HttpServletRequest httpServletRequest,
                                   @RequestParam("receiverName") String receiverName,
                                   @RequestParam("receiverAddress") String receiverAddress,
                                   @RequestParam("receiverPhone") String receiverPhone
                                  ) throws StripeException {
        User currentUser = new User();
        HttpSession session = httpServletRequest.getSession(false);
        Long id = (Long) session.getAttribute("id");
        currentUser.setId(id);

        Order order = this.orderService.handlePlaceOrder(currentUser, session, receiverName, receiverAddress, receiverPhone);

        long amountVND = java.math.BigDecimal.valueOf(order.getTotalPrice())
                .setScale(0, java.math.RoundingMode.HALF_UP)
                .longValueExact();

        SessionCreateParams params = SessionCreateParams.builder()
                .setMode(SessionCreateParams.Mode.PAYMENT)
                .setSuccessUrl("http://localhost:8080/pay/success?session_id={CHECKOUT_SESSION_ID}")
                .setCancelUrl("http://8080/pay/cancel?orderId=" + order.getId())
                .putMetadata("orderId", String.valueOf(order.getId()))
                .putMetadata("userId", String.valueOf(currentUser))
                .addLineItem(
                        SessionCreateParams.LineItem.builder()
                                .setQuantity(1L)
                                .setDescription("payment order")
                                .setPriceData(
                                        SessionCreateParams.LineItem.PriceData.builder()
                                                .setCurrency("vnd")                // or "usd" nếu tài khoản chưa bật VND
                                                .setUnitAmount(amountVND)          // 42000000
                                                .setProductData(
                                                        SessionCreateParams.LineItem.PriceData.ProductData.builder()
                                                                .setName("Order #" + order.getId())
                                                                .build()
                                                ).build()
                                ).build()
                ).build();

        Session s = Session.create(params);
        orderService.attachStripeSession(order.getId(), s.getId());

        return "redirect:" + s.getUrl();
//        return "redirect:/thanks";
    }

    @GetMapping("/pay/success")
    public String success(@RequestParam("session_id") String sessionId, Model model) throws Exception {
        Session s = Session.retrieve(sessionId);
        Long orderId = Long.valueOf(s.getMetadata().get("orderId"));

        model.addAttribute("sessionId", s.getId());
        model.addAttribute("paymentStatus", s.getPaymentStatus());  // change status paid if you checkout success
        model.addAttribute("orderId", s.getMetadata().get("orderId"));

        var pi = PaymentIntent.retrieve(s.getPaymentIntent());
        var charges = pi.getCharges().getData();
        String receiptUrl = charges.isEmpty() ? null : charges.get(0).getReceiptUrl();

        model.addAttribute("receiptUrl", receiptUrl);

        if ("paid".equalsIgnoreCase(s.getPaymentStatus())) {
            orderService.markPaid(orderId);
        }
        return "/client/cart/paymentSuccess";
    }

    @GetMapping("/thanks")
    public String getPageThankForCustomer(){
        return "/client/cart/thanksPage";
    }

    @GetMapping("/order-history")
    public String getPageOrderHistory(Model model, HttpServletRequest httpServletRequest){
        User user = new User();
        HttpSession session = httpServletRequest.getSession(false);
        Long id = (Long) session.getAttribute("id");
        user.setId(id);
        List<Order> order = this.orderService.getOrderByUser(user);
        model.addAttribute("orders", order);
        return "/client/orderHistory/orderHistory";
    }
}
