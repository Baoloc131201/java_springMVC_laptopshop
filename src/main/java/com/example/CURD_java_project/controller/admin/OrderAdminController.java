package com.example.CURD_java_project.controller.admin;

import com.example.CURD_java_project.model.Order;
import com.example.CURD_java_project.model.OrderDetail;
import com.example.CURD_java_project.repository.OrderRepository;
import com.example.CURD_java_project.service.OrderService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Controller
public class OrderAdminController {
    private OrderService orderService;

    public OrderAdminController(OrderService orderService) {
        this.orderService = orderService;
    }

    @GetMapping("/admin/order")
    public String getDashBoardAdmin(Model model){
        List<Order> orderList = orderService.getAllOrder();
        model.addAttribute("orders", orderList);
        return "/admin/order/order";
    }

    @GetMapping("/admin/order/{id}")
    public String getOrderDetailPage(Model model, @PathVariable("id") long id){
        Optional<Order> order = this.orderService.getOrderById(id);
        if(order.isPresent()){
            model.addAttribute("id", id);
            model.addAttribute("orderDetails", order.get().getOrderDetails());
        }
        return "/admin/order/showDetailOrder";
    }

    @GetMapping("/admin/order/update/{id}")
    public String getOrderUpdate(Model model, @PathVariable("id") long id){
        Optional<Order> order = this.orderService.getOrderById(id);
        model.addAttribute("newOrder", order.get());
        return "/admin/order/updateOrder";
    }

    @PostMapping("/admin/order/update")
    public String handleUpdateOrder(@ModelAttribute("newOrder") Order order){
        this.orderService.updateOrder(order);
        return "redirect:/admin/order";
    }

    @GetMapping("/admin/order/delete/{id}")
    public String getOrderDelete(@PathVariable("id") long id, Model model){
        Optional<Order> currentOrder = this.orderService.getOrderById(id);
        model.addAttribute("id", id);
        model.addAttribute("newOrder", currentOrder);
        return "/admin/order/deleteOrder";
    }

    @PostMapping("/admin/order/delete")
    public String handleDeleteOrder(@ModelAttribute("newOrder")Order order){
        this.orderService.deleteOrder(order.getId());
        return "redirect:/admin/order";
    }
}
