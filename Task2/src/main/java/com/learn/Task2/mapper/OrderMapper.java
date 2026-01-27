package com.learn.Task2.mapper;


import com.learn.Task2.model.dto.request.CreateOrderRequest;
import com.learn.Task2.model.dto.response.OrderResponse;
import com.learn.Task2.model.entity.Order;
import org.mapstruct.Mapper;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(
        componentModel = "spring",
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE
)
public interface OrderMapper {

    Order toEntity(CreateOrderRequest order);
    OrderResponse toDto(Order order);


}
