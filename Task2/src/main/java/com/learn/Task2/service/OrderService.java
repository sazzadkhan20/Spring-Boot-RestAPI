package com.learn.Task2.service;

import com.learn.Task2.model.dto.request.CreateOrderRequest;
import com.learn.Task2.model.dto.response.OrderResponse;

public interface OrderService {

    OrderResponse create(CreateOrderRequest request);

    OrderResponse get(Long id);

    OrderResponse approve(Long id);
    OrderResponse pay(Long id);

    OrderResponse ship(Long id);

    OrderResponse close(Long id);
}
