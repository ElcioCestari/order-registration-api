package com.brotherselectronics.fakers;

import com.brotherselectronics.orderregistration.domains.dtos.OrderItemRequestDTO;
import com.brotherselectronics.orderregistration.domains.dtos.OrderItemResponseDTO;
import com.brotherselectronics.orderregistration.domains.dtos.OrderRequestDTO;
import com.brotherselectronics.orderregistration.domains.dtos.OrderResponseDTO;
import com.brotherselectronics.orderregistration.domains.entities.Order;
import com.brotherselectronics.orderregistration.domains.entities.OrderItem;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

import static com.brotherselectronics.orderregistration.domains.enums.PaymentType.CHECK;
import static java.math.BigDecimal.valueOf;
import static java.time.LocalDateTime.now;
import static java.util.UUID.randomUUID;

public class OrderFaker extends BaseEntityFake implements EntityFake<Order, OrderRequestDTO, OrderResponseDTO> {
    public static final BigDecimal TOTAL_VALUE_ORDER = valueOf(15545.58);
    public static final boolean PAYMENT = true;
    private static final String USER_ID = randomUUID().toString();
    public static String ANY_DATE = "2022-01-03T23:59";

    private static final OrderItemFaker ORDER_ITEM_FAKER = new OrderItemFaker();

    @Override
    public Order getEntity() {
        var orderItems = (List<OrderItem>) ORDER_ITEM_FAKER.getEntityCollection();
        return Order.builder()
                .saleDate(now())
                .orderItens(orderItems)
                .payment(PAYMENT)
                .paymentType(CHECK)
                .totalValueOrder(TOTAL_VALUE_ORDER)
                .build();
    }

    @Override
    public OrderRequestDTO getRequestDTO() {
        var orderItems = (List<OrderItemRequestDTO>) ORDER_ITEM_FAKER.getRequestDTOCollection();
        return OrderRequestDTO.builder()
                .saleDate(now())
                .orderItens(orderItems)
                .payment(PAYMENT)
                .paymentType(CHECK)
                .totalValueOrder(TOTAL_VALUE_ORDER)
                .userId(USER_ID)
                .build();
    }

    @Override
    public OrderResponseDTO getResponseDTO() {
        var orderItemsResponseDTO = (List<OrderItemResponseDTO>) ORDER_ITEM_FAKER.getResponseDTOCollection();
        return OrderResponseDTO.builder()
                .saleDate(LocalDateTime.parse(ANY_DATE))
                .orderItens(orderItemsResponseDTO)
                .payment(true)
                .totalValueOrder(TOTAL_VALUE_ORDER)
                .build();
    }
}
