package com.brotherselectronics.orderregistration.domains.dtos;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderItemRequestDTO {

    @NotEmpty
    private String productId;

    @NotNull
    @Min(value = 1)
    private int quantity;
}