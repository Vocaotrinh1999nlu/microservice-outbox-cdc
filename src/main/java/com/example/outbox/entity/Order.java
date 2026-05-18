package com.example.outbox.entity;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;

@Entity
@Table(name = "ORDERS")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "CUSTOMER_EMAIL")
    private String customerEmail;

    @Column(name = "TOTAL_AMOUNT")
    private BigDecimal totalAmount;
    
    private String status;
}
