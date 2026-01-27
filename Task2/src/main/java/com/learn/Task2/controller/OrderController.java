package com.learn.Task2.controller;

import com.learn.Task2.model.dto.request.CreateOrderRequest;
import com.learn.Task2.model.dto.response.OrderResponse;
import com.learn.Task2.service.OrderService;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/orders")
@Validated
public class OrderController {

    private final OrderService service;

    public OrderController(OrderService service) {
        this.service = service;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public OrderResponse create(@Valid @RequestBody CreateOrderRequest request){
        return service.create(request);
    }

    @GetMapping("/{id}")
    public OrderResponse get(@PathVariable Long id) {
        return service.get(id);
    }

    @PostMapping("/{id}/approve")
    public OrderResponse approve(@PathVariable Long id) {
        return service.approve(id);
    }

    @PostMapping("/{id}/pay")
    public OrderResponse pay(@PathVariable Long id) {
        return service.pay(id);
    }

    @PostMapping("/{id}/ship")
    public OrderResponse ship(@PathVariable Long id) {
        return service.ship(id);
    }

    @PostMapping("/{id}/close")
    public OrderResponse close(@PathVariable Long id) {
        return service.close(id);
    }


}
