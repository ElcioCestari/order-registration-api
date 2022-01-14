package com.brotherselectronics.fakers;

import com.brotherselectronics.orderregistration.domains.dtos.OrderItemRequestDTO;
import com.brotherselectronics.orderregistration.domains.dtos.OrderItemResponseDTO;
import com.brotherselectronics.orderregistration.domains.entities.BaseEntityImp;
import com.brotherselectronics.orderregistration.domains.entities.OrderItem;

public class OrderItemFaker extends BaseEntityImp implements EntityFake<OrderItem, OrderItemRequestDTO, OrderItemResponseDTO>{

    public static final int QUANTITTY = 10;
    private static final String FAKE_PRODUCT_ID = "any id";

    @Override
    public OrderItem getEntity() {
        return OrderItem.builder()
                .quantity(QUANTITTY)
                .productId(FAKE_PRODUCT_ID)
                .build();
    }

    @Override
    public OrderItemRequestDTO getRequestDTO() {
        return OrderItemRequestDTO.builder()
                .quantity(QUANTITTY)
                .productId(FAKE_PRODUCT_ID)
                .build();
    }

    @Override
    public OrderItemResponseDTO getResponseDTO() {
        return OrderItemResponseDTO.builder()
                .quantity(QUANTITTY)
                .productId(FAKE_PRODUCT_ID)
                .build();
    }

}