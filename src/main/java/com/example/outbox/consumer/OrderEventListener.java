package com.example.outbox.consumer;

import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class OrderEventListener {

    @KafkaListener(topics = "outbox.event.ORDER", groupId = "order-consumer-group")
    public void handleOrderEvent(
            @Payload String payload,
            @Header(name = "eventType", required = false) String eventType) {
        
        log.info("Received event from Kafka!");
        log.info("Event Type: {}", eventType);
        log.info("Payload: {}", payload);
        
        // Here you would typically deserialize the payload and perform business logic
        // e.g., send an email, update another service, etc.
    }
}
