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

    private final OrderMapper mapper;
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

        var dtoResponse = (OrderResponseDTO) mapper.toDtoResponse(order);
        return dtoResponse;
    }

    @Override
    public OrderResponseDTO save(OrderRequestDTO dto) {
        Order order =(Order) mapper.toEntity(dto);
        orderRepository.save(order);
        var dtoResponse = (OrderResponseDTO) mapper.toDtoResponse(order);
        return dtoResponse;
    }

    @Override
    public OrderResponseDTO update(OrderRequestDTO dto, String id) {
        Order orderSaved = orderRepository.findById(id).orElseThrow(
                () -> new NotFoundException("Order id: ["+id+"] not found"));

        Order orderToSave = (Order) mapper.toEntity(dto);
        orderToSave.setId(orderSaved.getId());
        orderRepository.save(orderToSave);
        return (OrderResponseDTO) mapper.toDtoResponse(orderToSave);
    }

    @Override
    public void delete(String id) {
        orderRepository.findById(id).orElseThrow(
                () -> new NotFoundException("Order id: ["+id+"] not found"));

        orderRepository.deleteById(id);
    }
}