package com.brotherselectronics.fakers;

import com.brotherselectronics.orderregistration.domains.dtos.OrderItemRequestDTO;
import com.brotherselectronics.orderregistration.domains.dtos.OrderItemResponseDTO;
import com.brotherselectronics.orderregistration.domains.dtos.OrderRequestDTO;
import com.brotherselectronics.orderregistration.domains.dtos.OrderResponseDTO;
import com.brotherselectronics.orderregistration.domains.entities.Order;
import com.brotherselectronics.orderregistration.domains.entities.OrderItem;
import com.brotherselectronics.orderregistration.domains.enums.PaymentType;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

public class OrderFaker extends BaseEntityFake implements EntityFake<Order, OrderRequestDTO,OrderResponseDTO> {
    public static final BigDecimal TOTAL_VALUE_ORDER = BigDecimal.valueOf(15545.58);
    public static final boolean PAYMENT = true;
    private static final String USER_ID = "ANY ID";
    public static String ANY_DATE = "2022-01-03T23:59";

    private static final OrderItemFaker ORDER_ITEM_FAKER = new OrderItemFaker();


    @Override
    public Order getEntity() {
        var orderItens = (List<OrderItem>) ORDER_ITEM_FAKER.getEntityCollection();
        return Order.builder()
                .saleDate(LocalDateTime.now())
                .orderItens(orderItens)
                .payment(PAYMENT)
                .paymentType(PaymentType.CHECK)
                .totalValueOrder(TOTAL_VALUE_ORDER)
                .build();
    }

    @Override
    public OrderRequestDTO getRequestDTO() {
        var orderItens =(List<OrderItemRequestDTO>)  ORDER_ITEM_FAKER.getRequestDTOCollection();
        return OrderRequestDTO.builder()
                .saleDate(LocalDateTime.now())
                .orderItens( orderItens)
                .payment(PAYMENT)
                .paymentType(PaymentType.CHECK)
                .totalValueOrder(TOTAL_VALUE_ORDER)
                .userId(USER_ID)
                .build();
    }

    @Override
    public OrderResponseDTO getResponseDTO() {
        var orderItensResponseDTO = (List<OrderItemResponseDTO>) ORDER_ITEM_FAKER.getResponseDTOCollection();
        return OrderResponseDTO.builder()
                .saleDate(LocalDateTime.parse(ANY_DATE))
                .orderItens(orderItensResponseDTO)
                .payment(true)
                .totalValueOrder(TOTAL_VALUE_ORDER)
                .build();
    }
}
