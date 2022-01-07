package com.brotherselectronics.orderregistration.domains.dtos;


import com.brotherselectronics.orderregistration.domains.constraints.ConstraintExists;
import com.brotherselectronics.orderregistration.domains.entities.Order;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class OrderItemRequestDTO {

    @NotEmpty
    @ConstraintExists(entityRepository = Order.class)//TODO must to fix and change to ORDER_ITEM
    private String productId;

    @NotNull
    @Min(value = 1)
    private int quantity;
}