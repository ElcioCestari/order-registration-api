package com.brotherselectronics.orderregistration.domains.mappers;

import com.brotherselectronics.orderregistration.domains.dtos.OrderRequestDTO;
import com.brotherselectronics.orderregistration.domains.dtos.OrderResponseDTO;
import com.brotherselectronics.orderregistration.domains.entities.Order;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class OrderMapper implements IBaseMapper<Order, OrderRequestDTO, OrderResponseDTO> {

    //TODO corrigir implementação onde esta nulo.
    @Override
    public OrderResponseDTO toDtoResponse(Order entity) {
        return new OrderResponseDTO(
                entity.getSaleDate(),
                null,
                entity.isPayment(),
                null,
                entity.getTotalValueOrder(),
                entity.getUserId());
    }

    @Override
    public OrderRequestDTO toDtoRequest(Order entity) {
        return null;
    }

    @Override
    public Order toEntity(OrderRequestDTO dtoRequest) {
        return new Order(
                dtoRequest.getSaleDate(),
                dtoRequest.getOrderItens(),
                dtoRequest.isPayment(),
                dtoRequest.getPaymentType(),
                dtoRequest.getTotalValueOrder(),
                dtoRequest.getUserId());
    }

    @Override
    public List<OrderResponseDTO> toDtoResponseList(List<OrderRequestDTO> entityList) {
        return null;
    }
}