package com.learn.Task2.repository;

import com.learn.Task2.model.entity.Order;

public interface OrderRepository {
    Order save(Order order);
}
