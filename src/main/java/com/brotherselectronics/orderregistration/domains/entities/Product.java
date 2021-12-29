package com.brotherselectronics.orderregistration.domains.entities;

import com.brotherselectronics.orderregistration.domains.enums.Category;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Product extends BaseEntity{

    private String name;
    private String description;
    private String unitPurchasePrice;
    private String unitPurchaseSale;
    private String haveInStock;
    private Category category;
    private Stock stock;
}