package com.brotherselectronics.fakes;

import com.brotherselectronics.orderregistration.domains.dtos.OrderItemResponseDTO;
import com.brotherselectronics.orderregistration.domains.dtos.OrderResponseDTO;
import com.brotherselectronics.orderregistration.domains.entities.Order;
import com.brotherselectronics.orderregistration.domains.entities.OrderItem;
import com.brotherselectronics.orderregistration.domains.enums.PaymentType;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

public class OrderFake extends BaseEntityFake {
    public static final String PRODUCT_ID = "ANY ID";
    public static final int PRODUCT_QUANTITY = 10;
    public static final boolean PAYMENT = true;
    public static final BigDecimal TOTAL_VALUE_ORDER = BigDecimal.valueOf(15545.58);
    public static String DATA_QUALQUER = "2022-01-03T23:59";

    public static OrderResponseDTO getResponseDTOFake() {
        return OrderResponseDTO.builder()
                .saleDate(LocalDateTime.parse(DATA_QUALQUER))
                .orderItens(getOrderItensResponseDTO())
                .payment(true)
                .totalValueOrder(TOTAL_VALUE_ORDER)
                .build();
    }

    public static List<OrderItemResponseDTO> getOrderItensResponseDTO() {
        return Arrays.asList(getOrderItemResponseDTO());
    }

    private static OrderItemResponseDTO getOrderItemResponseDTO() {
        return OrderItemResponseDTO.builder()
                .quantity(PRODUCT_QUANTITY)
                .build();
    }

    public static List<OrderResponseDTO> getOrderResponseDTOListNotEmpty() {
        return List.of(getResponseDTOFake());
    }

    public static Order getOrder() {
        return Order.builder()
                .saleDate(LocalDateTime.now())
                .orderItens(getOrderItemList())
                .payment(PAYMENT)
                .paymentType(PaymentType.CHECK)
                .totalValueOrder(TOTAL_VALUE_ORDER)
                .build();
    }

    public static List<OrderItem> getOrderItemList() {
        return List.of(getOrderItem());
    }

    public static OrderItem getOrderItem() {
        return OrderItem.builder()
                .productId(PRODUCT_ID)
                .quantity(PRODUCT_QUANTITY)
                .build();
    }

    public static Order getOrder(String new_order_id) {
        Order order = OrderFake.getOrder();
        order.setId(new_order_id);
        return order;
    }
}
