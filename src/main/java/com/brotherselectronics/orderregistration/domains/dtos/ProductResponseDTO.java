package com.brotherselectronics.orderregistration.domains.dtos;

import com.brotherselectronics.orderregistration.domains.entities.Stock;
import com.brotherselectronics.orderregistration.domains.enums.Category;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ProductResponseDTO {

    private String name;
    private String description;
    private String unitPurchasePrice;
    private String unitPurchaseSale;
    private String haveInStock;
    private Category category;
    private Stock stock;
}