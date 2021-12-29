package com.brotherselectronics.orderregistration.domains.dtos;

import com.brotherselectronics.orderregistration.domains.entities.OrderItem;
import com.brotherselectronics.orderregistration.domains.enums.PaymentType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class OrderRequestDTO {

    private LocalDateTime saleDate;
    private List<OrderItem> orderItens;
    private boolean payment;
    private PaymentType paymentType;
    private BigDecimal totalValueOrder;
    private String userId;
}