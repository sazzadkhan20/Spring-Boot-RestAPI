package com.learn.Task2.repository;

import com.learn.Task2.model.entity.Order;

import java.util.Optional;

public interface OrderRepository {
    Order save(Order order);

    Optional<Order> findById(Long id);
}
