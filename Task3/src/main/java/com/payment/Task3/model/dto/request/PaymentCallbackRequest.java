package com.payment.Task3.model.dto.request;


import com.payment.Task3.model.entity.PaymentStatus;
import lombok.Data;

@Data
public class PaymentCallbackRequest {
    private String externalReference;
    private PaymentStatus status;
}
