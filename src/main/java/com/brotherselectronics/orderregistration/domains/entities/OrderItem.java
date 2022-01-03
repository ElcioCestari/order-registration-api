package com.brotherselectronics.orderregistration.domains.entities;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class OrderItem extends BaseEntity{

    private String productId;
    private int quantity;

}
