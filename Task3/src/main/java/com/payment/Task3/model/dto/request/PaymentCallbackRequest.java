package com.payment.Task3.model.dto.request;


import lombok.Data;

@Data
public class PaymentCallbackRequest {
    private String externalReference;
    private PaymentStatus status;
}
