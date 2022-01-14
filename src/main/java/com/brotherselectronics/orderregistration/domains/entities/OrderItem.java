package com.brotherselectronics.orderregistration.domains.entities;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@EqualsAndHashCode(callSuper=true)
public class OrderItem extends BaseEntityImp {

    private String productId;
    private int quantity;

}
