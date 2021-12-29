package com.brotherselectronics.orderregistration.services;

import com.brotherselectronics.orderregistration.domains.dtos.OrderRequestDTO;
import com.brotherselectronics.orderregistration.domains.dtos.OrderResponseDTO;
import com.brotherselectronics.orderregistration.domains.entities.Order;
import com.brotherselectronics.orderregistration.domains.mappers.IBaseMapper;
import com.brotherselectronics.orderregistration.domains.mappers.OrderMapper;
import com.brotherselectronics.orderregistration.exceptions.NotFoundException;
import com.brotherselectronics.orderregistration.repositories.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrderService implements IBaseService<OrderRequestDTO, OrderResponseDTO>{

    private final IBaseMapper mapper;
    private final OrderRepository orderRepository;

    @Autowired
    public OrderService(OrderMapper mapper, OrderRepository orderRepository) {
        this.mapper = mapper;
        this.orderRepository = orderRepository;
    }

    @Override
    public List<OrderResponseDTO> findAll() {
        List<Order> orderList = orderRepository.findAll();
        return mapper.toDtoResponseList(orderList);
    }

    @Override
    public OrderResponseDTO findById(String id) {

        Order order = orderRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Order not found with id: " +  id));

        OrderResponseDTO dtoResponse = (OrderResponseDTO) mapper.toDtoResponse(order);

        return dtoResponse;
    }

    @Override
    public OrderResponseDTO save(OrderRequestDTO dto) {

        Order order =(Order) mapper.toEntity(dto);

        orderRepository.save(order);

        OrderResponseDTO dtoResponse = (OrderResponseDTO) mapper.toDtoResponse(order);

        return dtoResponse;
    }

    @Override
    public OrderResponseDTO update(OrderRequestDTO dto, String id) {

        Order order = (Order) mapper.toEntity(dto);

        if(order.getId().equals(id)) {
            Order save = orderRepository.save(order);
            return (OrderResponseDTO) mapper.toDtoResponse(save);
        }

        throw new IllegalArgumentException("Order id: ["+id+"] not found");
    }

    @Override
    public void delete(String id) {
        orderRepository.deleteById(id);
    }
}