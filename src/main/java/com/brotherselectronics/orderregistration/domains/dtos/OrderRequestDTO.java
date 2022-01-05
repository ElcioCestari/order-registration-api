package com.brotherselectronics.orderregistration.domains.dtos;

import com.brotherselectronics.orderregistration.domains.enums.PaymentType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.Valid;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Builder
public class OrderRequestDTO {

    private LocalDateTime saleDate;

    @NotNull
    private List<@Valid OrderItemRequestDTO> orderItens;

    @NotNull
    private boolean payment;

    @NotNull
    private PaymentType paymentType;

    @DecimalMin(value = "0")
    private BigDecimal totalValueOrder;

    @NotEmpty
    private String userId;
}