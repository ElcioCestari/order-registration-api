package com.brotherselectronics.orderregistration.domains.entities;

import com.brotherselectronics.orderregistration.domains.enums.Category;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.mapping.Document;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Document("Products")
@Builder
public class Product extends BaseEntity{

    private String name;
    private String description;
    private BigDecimal unitPurchasePrice;
    private BigDecimal unitPurchaseSale;
    private String haveInStock;
    private Category category;
    private Stock stock;
}