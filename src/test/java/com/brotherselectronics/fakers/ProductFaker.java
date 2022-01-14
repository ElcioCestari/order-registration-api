package com.brotherselectronics.fakers;

import com.brotherselectronics.orderregistration.domains.dtos.ProductRequestDTO;
import com.brotherselectronics.orderregistration.domains.dtos.ProductResponseDTO;
import com.brotherselectronics.orderregistration.domains.entities.BaseEntityImp;
import com.brotherselectronics.orderregistration.domains.entities.Product;
import com.brotherselectronics.orderregistration.domains.entities.Stock;
import com.brotherselectronics.orderregistration.domains.enums.Category;

import java.math.BigDecimal;

public class ProductFaker extends BaseEntityImp implements EntityFake<Product, ProductRequestDTO, ProductResponseDTO>{

    public static final String PRODUCT_NAME = "phone";
    public static final String DESCRIPTION = "An Apple cellphone";
    public static final boolean HAVE_IN_STOCK = true;
    public static final BigDecimal UNIT_PURCHASE_PRICE = BigDecimal.valueOf(1000.00);
    public static final BigDecimal UNIT_PURCHASE_SALE = BigDecimal.valueOf(1100.00);
    public static final int STOCK_QUANTITY = 10;

    @Override
    public Product getEntity() {
        return Product.builder()
                .name(PRODUCT_NAME)
                .category(Category.ELETRONIC)
                .description(DESCRIPTION)
                .haveInStock(HAVE_IN_STOCK)
                .stock(getStock())
                .build();
    }

    @Override
    public ProductRequestDTO getRequestDTO() {
        return ProductRequestDTO.builder()
                .name(PRODUCT_NAME)
                .category(Category.ELETRONIC)
                .description(DESCRIPTION)
                .haveInStock(HAVE_IN_STOCK)
                .unitPurchasePrice(UNIT_PURCHASE_PRICE)
                .unitPurchaseSale(UNIT_PURCHASE_SALE)
                .stock(getStock())
                .build();
    }

    @Override
    public ProductResponseDTO getResponseDTO() {
        return ProductResponseDTO.builder()
                .name(PRODUCT_NAME)
                .category(Category.ELETRONIC)
                .description(DESCRIPTION)
                .haveInStock(HAVE_IN_STOCK)
                .stock(getStock())
                .build();
    }

    private Stock getStock() {
        return new Stock(STOCK_QUANTITY);
    }
}