package com.brotherselectronics.orderregistration.domains.dtos;

import com.brotherselectronics.orderregistration.domains.constraints.ConstraintExists;
import com.brotherselectronics.orderregistration.domains.entities.Order;
import com.brotherselectronics.orderregistration.domains.enums.PaymentType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.Valid;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Builder
public class OrderRequestDTO {

    @NotNull
    //@FutureOrPresent //TODO its return a 400 and not showing anything that could help to know what hapend
    private LocalDateTime saleDate;

    @NotNull
    private List<@Valid OrderItemRequestDTO> orderItens;

    @NotNull
    private boolean payment;

    @NotNull
    private PaymentType paymentType;

    @DecimalMin(value = "0")
    private BigDecimal totalValueOrder;

    @ConstraintExists(entityRepository = Order.class) //TODO its wrong must to be USER
    private String userId;
}