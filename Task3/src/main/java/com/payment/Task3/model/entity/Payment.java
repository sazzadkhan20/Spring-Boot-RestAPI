package com.payment.Task3.model.entity;

import jdk.jfr.DataAmount;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class Payment {

    private Long id;

    private Long orderId;

    private BigDecimal amount;

    private PaymentStatus status;




}
