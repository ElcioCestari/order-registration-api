package com.brotherselectronics.orderregistration.domains.mappers;

import com.brotherselectronics.orderregistration.domains.dtos.OrderItemRequestDTO;
import com.brotherselectronics.orderregistration.domains.dtos.OrderItemResponseDTO;
import com.brotherselectronics.orderregistration.domains.entities.OrderItem;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface OrderItemMapper extends IBaseMapper<OrderItem, OrderItemRequestDTO, OrderItemResponseDTO> {

}