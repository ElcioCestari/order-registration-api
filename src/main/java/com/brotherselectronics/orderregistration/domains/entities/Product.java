package com.brotherselectronics.orderregistration.domains.entities;

import com.brotherselectronics.orderregistration.domains.enums.Category;
import lombok.*;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.validation.constraints.*;
import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Document("Products")
@Builder
@EqualsAndHashCode(callSuper = true)
public class Product extends BaseEntityImp {

    @NotBlank
    private String name;

    @NotBlank
    private String description;

    @DecimalMin(value = "0")
    @DecimalMax(value = "9999.99")
    @NotNull
    private BigDecimal unitPurchasePrice;

    @DecimalMin(value = "0")
    @DecimalMax(value = "9999.99")
    private BigDecimal unitPurchaseSale;

    @NotNull
    private boolean haveInStock;

    @NotNull
    private Category category;

    @NotNull
    private Stock stock;
}