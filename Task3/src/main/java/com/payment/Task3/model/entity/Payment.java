package com.payment.Task3.model.entity;

import jdk.jfr.DataAmount;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Payment {

    private Long id;

    private Long orderId;

    private BigDecimal amount;

    private PaymentStatus status;

    private String externalReference;

    private String IdempotencyKey;

    private LocalDateTime createdDate;
    private LocalDateTime updatedDate;

}
