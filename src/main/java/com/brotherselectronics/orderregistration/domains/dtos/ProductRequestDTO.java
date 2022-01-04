package com.brotherselectronics.orderregistration.domains.dtos;

import com.brotherselectronics.orderregistration.domains.entities.Stock;
import com.brotherselectronics.orderregistration.domains.enums.Category;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ProductRequestDTO{

    @NotEmpty
    private String name;

    private String description;

    @DecimalMin(value = "0")
    @DecimalMax(value = "9999.99")
    @NotNull
    private BigDecimal unitPurchasePrice;

    @DecimalMin(value = "0")
    @DecimalMax(value = "9999.99")
    private BigDecimal unitPurchaseSale;

    private boolean haveInStock;

    @NotNull
    private Category category;

    @NotNull
    private Stock stock;

    private LocalDateTime registrationTime;
}