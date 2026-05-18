package com.example.outbox.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "OUTBOX")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class OutboxEvent {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "AGGREGATE_TYPE")
    private String aggregateType;

    @Column(name = "AGGREGATE_ID")
    private String aggregateId;

    private String type;
    
    @Lob
    private String payload;
    
    @Column(name = "CREATED_AT")
    private LocalDateTime createdAt;
}
