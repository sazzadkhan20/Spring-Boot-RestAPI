package com.payment.Task3.service.impl;

import com.payment.Task3.client.ExternalPaymentClient;
import com.payment.Task3.model.dto.request.PaymentInitiateRequest;
import com.payment.Task3.model.entity.Payment;
import com.payment.Task3.model.entity.PaymentStatus;
import com.payment.Task3.repository.PaymentRepository;
import com.payment.Task3.service.PaymentService;
import org.springframework.stereotype.Service;


@Service
public class PaymentServiceImpl implements PaymentService {

    private final PaymentRepository repository;

    private final ExternalPaymentClient client;

    public PaymentServiceImpl(PaymentRepository repository, ExternalPaymentClient client) {

    this.repository = repository;
    this.client = client;
    }

    @Override
    public Payment initiate(PaymentInitiateRequest request) {

        return repository.findByIdempotencyKey(request.getIdempotencyKey()).orElseGet(()-> createAndProcessPayment(request));


    }

    private Payment createAndProcessPayment(PaymentInitiateRequest request) {

        Payment payment = new Payment();
        payment.setOrderId(request.getOrderId());
        payment.setAmount(request.getAmount());

        payment.setIdempotencyKey(request.getIdempotencyKey());

        payment.setStatus(PaymentStatus.INITIATED);

        repository.save(payment);

        try{
            String externalRef = client.initiatePayment();
            payment.setExternalReference(externalRef);
            payment.setStatus(PaymentStatus.PENDING);

        }
        catch (Exception ex){
            payment.setStatus(PaymentStatus.PENDING);
        }

        return repository.save(payment);
    }

    @Override
    public Payment handleCallback(String externalReference, PaymentStatus status) {

        Payment payment = repository.findByExternalReference(externalReference).orElseThrow(()-> new RuntimeException("Payment not found for reference: "));


        if ((payment.getStatus() == PaymentStatus.SUCCESS) || (payment.getStatus() == PaymentStatus.FAILED)) {

            return payment;
        }

        payment.setStatus(status);

        return repository.save(payment);

    }

}
