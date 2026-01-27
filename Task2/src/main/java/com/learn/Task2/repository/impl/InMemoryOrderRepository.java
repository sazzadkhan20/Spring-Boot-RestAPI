package com.learn.Task2.repository.impl;

import com.learn.Task2.model.entity.Order;
import com.learn.Task2.repository.OrderRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

@Repository
@Slf4j
public class InMemoryOrderRepository implements OrderRepository {


    private final Map<Long, Order> store = new ConcurrentHashMap<>();

    private final AtomicLong idGenerator = new AtomicLong(0);

    public Order save(Order order){

        if (order.getId()== null){
            order.setId(idGenerator.getAndIncrement());
            order.setCreatedAt(LocalDateTime.now());
        }

        order.setUpdatedAt(LocalDateTime.now());
        store.put(order.getId(), order);
        return order;
    }

    public Optional<Order> findById(Long id){
        return Optional.ofNullable(store.get(id));
    }

}

