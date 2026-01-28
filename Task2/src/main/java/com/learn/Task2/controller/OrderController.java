package com.learn.Task2.controller;

import com.learn.Task2.model.dto.request.CreateOrderRequest;
import com.learn.Task2.model.dto.response.OrderResponse;
import com.learn.Task2.service.OrderService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/orders")
@Validated
public class OrderController {

    private final OrderService service;

    public OrderController(OrderService service) {
        this.service = service;
    }

    @PostMapping(
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    @ResponseStatus(HttpStatus.CREATED)
    public OrderResponse create(@Valid @RequestBody CreateOrderRequest request){
        return service.create(request);
    }

    @GetMapping(
            value = "/{id}",
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public OrderResponse get(@PathVariable Long id) {
        return service.get(id);
    }

    @PostMapping(
            value = "/{id}/approve",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public OrderResponse approve(@PathVariable Long id) {
        return service.approve(id);
    }

    @PostMapping(
            value= "/{id}/pay",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public OrderResponse pay(@PathVariable Long id) {
        return service.pay(id);
    }

    @PostMapping(
            value= "/{id}/ship",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public OrderResponse ship(@PathVariable Long id) {
        return service.ship(id);
    }

    @PostMapping(
            value= "/{id}/close",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public OrderResponse close(@PathVariable Long id) {
        return service.close(id);
    }


}
