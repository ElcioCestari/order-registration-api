package com.brotherselectronics.orderregistration.domains.dtos;


import com.brotherselectronics.orderregistration.domains.constraints.ConstraintExists;
import com.brotherselectronics.orderregistration.repositories.RepositoryDomain;
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
    @ConstraintExists(repository = RepositoryDomain.PRODUCT)//TODO must to fix and change to ORDER_ITEM
    private String productId;

    @NotNull
    @Min(value = 1)
    private int quantity;
}