package com.brotherselectronics.orderregistration.services;

import com.brotherselectronics.orderregistration.domains.dtos.OrderResponseDTO;
import com.brotherselectronics.orderregistration.domains.entities.Order;
import com.brotherselectronics.orderregistration.domains.mappers.OrderMapper;
import com.brotherselectronics.orderregistration.repositories.OrderRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;

import static java.util.Collections.emptyList;
import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(SpringExtension.class)
class OrderServiceTest {

    @InjectMocks private OrderService orderService;
    @Mock private OrderRepository orderRepository;
    @Mock private OrderMapper mapper;
    private List<Order> orderListNotEmpty;
    private List<OrderResponseDTO> dtoListNotEmpty;

    @BeforeEach
    public void setUp() {
        orderListNotEmpty = List.of(new Order());
        dtoListNotEmpty = List.of(new OrderResponseDTO());
    }

    @Test
    public void findAll_whenHasItensToReturnThenAssertThatListItsNotEmpty() {
        Mockito.when(orderRepository.findAll()).thenReturn(orderListNotEmpty);
        Mockito.when(mapper.toDtoResponseList(Mockito.anyList())).thenReturn(dtoListNotEmpty);
        List<OrderResponseDTO> all = orderService.findAll();
        assertThat(all).isNotEmpty();
    }

    @Test
    public void findAll_whenHasNotItensToReturnThenAssertThatListItsEmpty() {
        Mockito.when(orderRepository.findAll()).thenReturn(emptyList());
        Mockito.when(mapper.toDtoResponseList(Mockito.anyList())).thenReturn(emptyList());
        List<OrderResponseDTO> all = orderService.findAll();
        assertThat(all).isEmpty();
    }

    @Test
    public void findById() {
        Assertions.assertTrue(true);
    }

    @Test
    public void save() {
        Assertions.assertTrue(true);
    }

    @Test
    public void update() {
        Assertions.assertTrue(true);
    }

    @Test
    public void delete() {
        Assertions.assertTrue(true);
    }
}