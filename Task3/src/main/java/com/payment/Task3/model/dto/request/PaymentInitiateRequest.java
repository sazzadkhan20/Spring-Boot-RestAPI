package com.payment.Task3.model.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class PaymentInitiateRequest {

    @NotNull
    private long orderId;

    @NotNull
    @Positive
    private BigDecimal amount;


    @NotBlank
    private String idempotencyKey;
}
