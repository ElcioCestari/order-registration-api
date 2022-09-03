package com.brotherselectronics.orderregistration.domains.entities;

import com.brotherselectronics.orderregistration.domains.enums.Category;
import lombok.*;
import org.springframework.data.mongodb.core.mapping.Document;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Document("Products")
@Builder
@EqualsAndHashCode(callSuper=true)
public class Product extends BaseEntityImp {

    private String name;
    private String description;
    private BigDecimal unitPurchasePrice;
    private BigDecimal unitPurchaseSale;
    private boolean haveInStock;
    private Category category;
    private Stock stock;
}