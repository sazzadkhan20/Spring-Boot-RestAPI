package com.payment.Task3.service;

import com.payment.Task3.model.dto.request.PaymentInitiateRequest;
import com.payment.Task3.model.entity.Payment;
import com.payment.Task3.model.entity.PaymentStatus;

public interface PaymentService {


    Payment initiate(PaymentInitiateRequest request);

    Payment handleCallback(String externalReference, PaymentStatus status);

}
