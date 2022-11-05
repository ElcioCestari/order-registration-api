package com.brotherselectronics.orderregistration.domains.mappers;

import com.brotherselectronics.orderregistration.domains.dtos.ProductRequestDTO;
import com.brotherselectronics.orderregistration.domains.entities.Product;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

import java.math.BigDecimal;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertNotNull;


class ProductMapperTest {

    private final ProductMapper mapper = Mappers.getMapper(ProductMapper.class);

    @Test
    void testWasDefined() {
        assertNotNull(mapper);
    }

    @Test
    void merge_givenSourceWithNull_assertThatTargetWillPreserveOldValue() {
        var source = ProductRequestDTO.builder()
                .name("new name")
                .build();

        Product target = Product.builder()
                .unitPurchaseSale(BigDecimal.ONE)
                .unitPurchasePrice(BigDecimal.TEN)
                .haveInStock(true)
                .description("anything")
                .name("any name")
                .build();
        mapper.merge(source, target);
        assertThat(target.getDescription()).isNotNull();
        assertThat(target.getName()).isEqualTo("new name");
    }
}