package com.brotherselectronics.orderregistration.domains.dtos;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class OrderResponseDTO {

    private LocalDateTime saleDate;
    private List<OrderItemResponseDTO> orderItens;
    private boolean payment;
    private String paymentType;
    private BigDecimal totalValueOrder;
    private String userId;
}