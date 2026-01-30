package com.payment.Task3.model.dto.response;

import com.payment.Task3.model.entity.PaymentStatus;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
public class PaymentResponse {

    private long id;
    private Long orderId;

    private BigDecimal amount;
    private PaymentStatus status;

    private String externalReference;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
