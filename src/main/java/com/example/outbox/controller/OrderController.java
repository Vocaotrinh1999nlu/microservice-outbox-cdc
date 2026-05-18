package com.example.outbox.controller;

import com.example.outbox.entity.Order;
import com.example.outbox.service.OrderService;
import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/orders")
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;

    @PostMapping
    public ResponseEntity<Order> createOrder(@RequestBody Order order) throws JsonProcessingException {
        return ResponseEntity.ok(orderService.createOrder(order));
    }
}
