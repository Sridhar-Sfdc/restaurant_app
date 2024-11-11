package com.example.restaurant.controller;

import com.example.restaurant.model.Order;
import com.example.restaurant.model.OrderStatus;
import com.example.restaurant.service.OrderService;
import com.example.restaurant.utils.WhatsAppApiHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/orders")
public class OrderController {

    private final OrderService orderService;

    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @GetMapping
    public String viewOrders(Model model) {
        model.addAttribute("order", orderService.getAllOrders());
        return "orders";
    }

    @PostMapping("/{orderId}/accept")
    public String acceptOrder(@PathVariable Long orderId) {
        orderService.updateOrderStatus(orderId, OrderStatus.ACCEPTED);
        return "redirect:/orders";
    }

    @PostMapping("/{orderId}/decline")
    public String declineOrder(@PathVariable Long orderId) {
        orderService.updateOrderStatus(orderId, OrderStatus.DECLINED);
        return "redirect:/orders";
    }
}

