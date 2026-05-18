package com.example.outbox.service;

import com.example.outbox.entity.Order;
import com.example.outbox.entity.OutboxEvent;
import com.example.outbox.repository.OrderRepository;
import com.example.outbox.repository.OutboxRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class OrderService {

    private final OrderRepository orderRepository;
    private final OutboxRepository outboxRepository;
    private final ObjectMapper objectMapper;

    @Transactional
    public Order createOrder(Order order) throws JsonProcessingException {
        // Save the Order
        order.setStatus("CREATED");
        Order savedOrder = orderRepository.save(order);

        // Create Outbox Event
        OutboxEvent outboxEvent = OutboxEvent.builder()
                .aggregateType("ORDER")
                .aggregateId(savedOrder.getId().toString())
                .type("ORDER_CREATED")
                .payload(objectMapper.writeValueAsString(savedOrder))
                .createdAt(LocalDateTime.now())
                .build();

        // Save to Outbox table in the same transaction
        outboxRepository.save(outboxEvent);

        return savedOrder;
    }
}
