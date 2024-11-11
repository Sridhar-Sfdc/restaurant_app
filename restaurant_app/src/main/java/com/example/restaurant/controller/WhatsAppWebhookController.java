package com.example.restaurant.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.example.restaurant.service.OrderService;
import com.example.restaurant.utils.WhatsAppApiHelper;

@RestController
@RequestMapping("/whatsapp/webhook")
public class WhatsAppWebhookController {

    private final WhatsAppApiHelper whatsappApiHelper;
    private final OrderService orderService;

    public WhatsAppWebhookController(WhatsAppApiHelper whatsappApiHelper, OrderService orderService) {
        this.whatsappApiHelper = whatsappApiHelper;
        this.orderService = orderService;
    }

    @PostMapping
    public ResponseEntity<Void> handleIncomingMessage(@RequestBody String payload) {
        whatsappApiHelper.processIncomingMessage(payload, orderService);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}

