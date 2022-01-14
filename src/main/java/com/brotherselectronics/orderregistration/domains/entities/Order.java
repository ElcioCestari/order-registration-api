package com.brotherselectronics.orderregistration.domains.entities;

import com.brotherselectronics.orderregistration.domains.enums.PaymentType;
import lombok.*;
import org.springframework.data.mongodb.core.mapping.Document;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Document("Orders")
@Builder
@EqualsAndHashCode(callSuper=true)
public class Order extends BaseEntityImp {
    private LocalDateTime saleDate;
    private List<OrderItem> orderItens;
    private boolean payment;
    private PaymentType paymentType;
    private BigDecimal totalValueOrder;
    private String userId;

}
