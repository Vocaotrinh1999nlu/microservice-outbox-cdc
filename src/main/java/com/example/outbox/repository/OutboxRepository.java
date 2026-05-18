package com.example.outbox.repository;

import com.example.outbox.entity.OutboxEvent;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OutboxRepository extends JpaRepository<OutboxEvent, Long> {
}
