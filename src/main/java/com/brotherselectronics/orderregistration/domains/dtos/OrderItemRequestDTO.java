package com.brotherselectronics.orderregistration.domains.dtos;


import com.brotherselectronics.orderregistration.domains.constraints.ConstraintExists;
import com.brotherselectronics.orderregistration.domains.entities.Product;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class OrderItemRequestDTO {

    @NotEmpty
    @ConstraintExists(entityRepository = Product.class)
    private String productId;

    @NotNull
    @Min(value = 1)
    private int quantity;
}