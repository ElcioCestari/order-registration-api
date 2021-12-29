package com.brotherselectronics.orderregistration.domains.mappers;

import com.brotherselectronics.orderregistration.domains.dtos.OrderRequestDTO;
import com.brotherselectronics.orderregistration.domains.dtos.OrderResponseDTO;
import com.brotherselectronics.orderregistration.domains.entities.Order;

import java.util.List;

public class OrderMapper implements IBaseMapper<Order, OrderRequestDTO, OrderResponseDTO> {

    @Override
    public OrderResponseDTO toDtoResponse(Order entity) {
        return null;
    }

    @Override
    public OrderRequestDTO toDtoRequest(Order entity) {
        return null;
    }

    @Override
    public Order toEntity(OrderRequestDTO dtoRequest) {
        return null;
    }

    @Override
    public List<OrderResponseDTO> toDtoResponseList(List<OrderRequestDTO> entityList) {
        return null;
    }
}