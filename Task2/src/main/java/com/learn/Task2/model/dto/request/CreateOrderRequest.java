package com.learn.Task2.model.dto.request;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class CreateOrderRequest {


    @NotBlank(message= "Customer Name is required")
    @Size(min=1, max=100, message="Customer Name must be between 1 to 100")
    private String customerName;

    @NotNull(message = "Total Amount must be greater than 0")
    @Positive
    private BigDecimal totalAmount;


}
