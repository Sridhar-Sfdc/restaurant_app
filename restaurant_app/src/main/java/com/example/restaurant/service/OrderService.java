package com.example.restaurant.service;

import com.example.restaurant.model.Order;
import com.example.restaurant.model.OrderStatus;
import com.example.restaurant.utils.WhatsAppApiHelper;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import java.util.HashMap;
import java.util.Map;

@Service
public class OrderService {
	
    private static final Logger logger = LoggerFactory.getLogger(OrderService.class);

    private final WhatsAppApiHelper whatsappApiHelper;
    private Map<Long, Order> orders = new HashMap<>();

    public OrderService(WhatsAppApiHelper whatsappApiHelper) {
        this.whatsappApiHelper = whatsappApiHelper;
    }

    public Order createOrder(String phoneNumber, String message) {
    	
    	logger.debug("This is the Phone Number and Message: ", message);
    	
        Order order = new Order(phoneNumber, message, message, OrderStatus.PENDING);
        orders.put(order.getId(), order);

        // Notify customer of order receipt
        whatsappApiHelper.sendMessage(phoneNumber, "Your order has been received and is being processed.");
        return order;
    }
    
    public Map<Long, Order> getAllOrders() {
        return orders;
    }

    public void updateOrderStatus(Long orderId, OrderStatus status) {
        Order order = orders.get(orderId);
        if (order != null) {
            order.setStatus(status);
            String message = (status == OrderStatus.ACCEPTED) ?
                    "Your order has been accepted!" :
                    "Your order has been declined.";
            whatsappApiHelper.sendMessage(order.getPhoneNumber(), message);
        }
    }

}

