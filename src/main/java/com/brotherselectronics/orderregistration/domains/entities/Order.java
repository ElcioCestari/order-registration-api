package com.brotherselectronics.orderregistration.domains.entities;

import com.brotherselectronics.orderregistration.domains.enums.PaymentType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Order extends BaseEntity {
    private LocalDateTime saleDate;
    private List<OrderItem> orderItens;
    private boolean payment;
    private PaymentType paymentType;
    private BigDecimal totalValueOrder;
    private String userId;
}
