package com.brotherselectronics.orderregistration.domains.dtos;

import com.brotherselectronics.orderregistration.domains.entities.Stock;
import com.brotherselectronics.orderregistration.domains.enums.Category;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ProductResponseDTO implements Serializable {

    private String id;
    private String name;
    private String description;
    private String unitPurchasePrice;
    private String unitPurchaseSale;
    private boolean haveInStock;
    private Category category;
    private Stock stock;
}