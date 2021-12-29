package com.brotherselectronics.orderregistration.domains.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderItem extends BaseEntity{

    private String productId;
    private int quantity;

}
