package com.payment.Task3.controller;


import com.payment.Task3.model.dto.request.PaymentCallbackRequest;
import com.payment.Task3.model.dto.request.PaymentInitiateRequest;
import com.payment.Task3.model.entity.Payment;
import com.payment.Task3.service.PaymentService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/payments")
public class PaymentController {

    private final PaymentService service;

    public PaymentController(PaymentService service) {
        this.service = service;
    }


    @PostMapping("/initiate")
    public Payment initiate(@Valid @RequestBody PaymentInitiateRequest request) {

        return service.initiate(request);
    }

    @PostMapping("/callback")
    public Payment callback(@RequestBody PaymentCallbackRequest request) {

        return service.handleCallback(request.getExternalReference(), request.getStatus());
    }

}
