package com.brotherselectronics.orderregistration.domains.projection;

import org.springframework.beans.factory.annotation.Value;

import java.math.BigDecimal;

public interface PriceProductProjection {
    BigDecimal getUnitPurchaseSale();

    String getName();

    @Value("#{(target.category.name)}")
    String getCategoryName();
}
