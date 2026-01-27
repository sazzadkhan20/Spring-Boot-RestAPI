package com.learn.Task2.service.impl;

import com.learn.Task2.mapper.OrderMapper;
import com.learn.Task2.model.dto.request.CreateOrderRequest;
import com.learn.Task2.model.dto.response.OrderResponse;
import com.learn.Task2.model.entity.Order;
import com.learn.Task2.model.entity.OrderState;
import com.learn.Task2.repository.OrderRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class OrderServiceImpl {

    private final OrderRepository repository;
    private final OrderMapper mapper;

    public OrderServiceImpl(OrderRepository repository, OrderMapper mapper) {
        this.repository = repository;
        this.mapper = mapper;

    }

    @Override
    public OrderResponse create(CreateOrderRequest request){
        Order order = mapper.toEntity(request);
        order.setState(OrderState.valueOf(OrderState.CREATED.toString()));

        return mapper.toDto(repository.save(order));

    }

    @Override
    public OrderResponse get(Long id){
        Order order = repository.findById(id).orElseThrow(()-> new OrderNotFoundException(id));

        return mapper.toDto(order);

    }

    @Override
    public OrderResponse approve(Long id){
        Order order = getOrderInternal(id);
        requireState(order, OrderState.APPROVED);
        order.setState(OrderState.valueOf(OrderState.APPROVED.toString()));

        return mapper.toDto(repository.save(order));
    }

    @Override
    public OrderResponse pay(Long id){
        Order order = getOrderInternal(id);
        requireState(order, OrderState.PAID);
        order.setState(OrderState.valueOf(OrderState.PAID.toString()));

        return mapper.toDto(repository.save(order));

    }

    @Override
    public OrderResponse ship(Long id){
        Order order = getOrderInternal(id);
        requireState(order, OrderState.SHIPPED);
        order.setState(OrderState.valueOf(OrderState.SHIPPED.toString()));

        return mapper.toDto(repository.save(order));

    }

    @Override
    public OrderResponse close(Long id){
        Order order = getOrderInternal(id);
        requireState(order, OrderState.CLOSED);
        order.setState(OrderState.valueOf(OrderState.CLOSED.toString()));

        return mapper.toDto(repository.save(order));

    }

    private Order getOrderInternal(Long id){

        return repository.findById(id).orElseThrow(() -> new OrderNotFoundException(id));
    }

    private void requireState(Order order, OrderState expected){

        if (order.getState() != expected){
            throw new InvalidOrderStateException(
                    "Expected state" + expected + "but was" + order.getState()
            );
        }
    }

}
