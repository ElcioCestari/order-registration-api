package com.brotherselectronics.orderregistration.domains.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class Stock extends BaseEntity{

    private int quantity;
}