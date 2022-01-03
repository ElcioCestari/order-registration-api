package com.brotherselectronics.orderregistration.services;

import com.brotherselectronics.orderregistration.domains.dtos.OrderRequestDTO;
import com.brotherselectronics.orderregistration.domains.dtos.OrderResponseDTO;
import com.brotherselectronics.orderregistration.domains.entities.Order;
import com.brotherselectronics.orderregistration.domains.mappers.OrderMapper;
import com.brotherselectronics.orderregistration.exceptions.NotFoundException;
import com.brotherselectronics.orderregistration.repositories.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrderService implements IBaseService<OrderRequestDTO, OrderResponseDTO, Order>{

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
        Order order = getOrderFromRepositoryOrThrowNotFoundException(id);
        return mapperToResponseDTO(order);
    }

    @Override
    public OrderResponseDTO save(OrderRequestDTO dto) {
        Order order = mapperToOrder(dto);
        orderRepository.save(order);
        return mapperToResponseDTO(order);
    }

    @Override
    public OrderResponseDTO update(OrderRequestDTO dto, String id) {
        Order orderSaved = this.getOrderFromRepositoryOrThrowNotFoundException(id);
        Order orderToSave = mapperToOrder(dto);
        merge(orderToSave, orderSaved);
        orderRepository.save(orderToSave);
        return mapperToResponseDTO(orderToSave);
    }

    @Override
    public void delete(String id) {
        this.getOrderFromRepositoryOrThrowNotFoundException(id);
        orderRepository.deleteById(id);
    }

    private Order mapperToOrder(OrderRequestDTO dto) {
        return (Order) mapper.toEntity(dto);
    }

    private OrderResponseDTO mapperToResponseDTO(Order order) {
        return (OrderResponseDTO) mapper.toDtoResponse(order);
    }

    private Order getOrderFromRepositoryOrThrowNotFoundException(String id) {
        return orderRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Order not found with id: " + id));
    }
}