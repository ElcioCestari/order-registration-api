package com.brotherselectronics.orderregistration.services;

import com.brotherselectronics.orderregistration.domains.dtos.OrderRequestDTO;
import com.brotherselectronics.orderregistration.domains.dtos.OrderResponseDTO;
import com.brotherselectronics.orderregistration.domains.entities.Order;
import com.brotherselectronics.orderregistration.domains.mappers.OrderMapper;
import com.brotherselectronics.orderregistration.exceptions.NotFoundException;
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
import java.util.Optional;

import static java.util.Collections.emptyList;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class)
class OrderServiceTest {
    private static final String FAKE_ID = "asdfadsfadfdasfadfafa";
    private static final String FAIL_MSG = "Must to be throw an exception and don't was threw.";

    @InjectMocks private OrderService orderService;
    @Mock private OrderRepository orderRepository;
    @Mock private OrderMapper mapper;

    private List<Order> orderListNotEmpty;
    private List<OrderResponseDTO> dtoListNotEmpty;
    private OrderResponseDTO orderResponseDTO;
    private OrderRequestDTO orderRequestDTO;
    private Optional<Order> orderOptional;
    private Order order;

    @BeforeEach
    public void setUp() {
        order = new Order();
        order.setId(FAKE_ID);
        orderListNotEmpty = List.of(order);
        dtoListNotEmpty = List.of(new OrderResponseDTO());
        orderResponseDTO = new OrderResponseDTO();
        orderRequestDTO = new OrderRequestDTO();
        orderOptional = Optional.ofNullable(order);
    }

    @Test
    public void findAll_whenHasItensToReturnThenAssertThatListItsNotEmpty() {
        when(orderRepository.findAll()).thenReturn(orderListNotEmpty);
        when(mapper.toDtoResponseList(Mockito.anyList())).thenReturn(dtoListNotEmpty);
        List<OrderResponseDTO> all = orderService.findAll();
        assertThat(all).isNotEmpty();
    }

    @Test
    public void findAll_whenHasNotItensToReturnThenAssertThatListItsEmpty() {
        when(orderRepository.findAll()).thenReturn(emptyList());
        when(mapper.toDtoResponseList(Mockito.anyList())).thenReturn(emptyList());
        List<OrderResponseDTO> all = orderService.findAll();
        assertThat(all).isEmpty();
    }

    @Test
    public void findById_whenHasOrderThenAssertThatItsNotNull() {
        when(orderRepository.findById(anyString())).thenReturn(orderOptional);
        when(mapper.toDtoResponse(any(Order.class))).thenReturn(orderResponseDTO);
        OrderResponseDTO responseActual = orderService.findById(FAKE_ID);
        assertNotNull(responseActual);
    }

    @Test
    public void findById_whenHasNotOrderThenAssertThatItsThrowAnException() {
        when(orderRepository.findById(anyString())).thenReturn(Optional.ofNullable(null));
        when(mapper.toDtoResponse(any(Order.class))).thenReturn(orderResponseDTO);
        try {
            OrderResponseDTO responseActual = orderService.findById(FAKE_ID);
            fail(FAIL_MSG);
        } catch (NotFoundException e) {
            assertThat(e.getMessage()).isEqualTo("Order not found with id: " + FAKE_ID);
        }
    }

    @Test
    public void save_whenCallMethodWithAnyOrderRequestDTOThenSaveAndReturnAnOrderResponseDTO() {
        when(orderRepository.save(any(Order.class))).thenReturn(order);
        when(mapper.toDtoResponse(any(Order.class))).thenReturn(orderResponseDTO);
        when(mapper.toEntity(any(OrderRequestDTO.class))).thenReturn(order);
        OrderResponseDTO responseActual = orderService.save(orderRequestDTO);
        assertNotNull(responseActual);
    }

    @Test
    public void update_whenSuccessfulThenReturnAnOrderResponseDTO() {
        when(orderRepository.findById(FAKE_ID)).thenReturn(orderOptional);
        when(mapper.toEntity(any(OrderRequestDTO.class))).thenReturn(order);
        when(orderRepository.save(any(Order.class))).thenReturn(order);
        when(mapper.toDtoResponse(any(Order.class))).thenReturn(orderResponseDTO);
        OrderResponseDTO responseActual = orderService.update(orderRequestDTO, FAKE_ID);
        assertNotNull(responseActual);
    }

    @Test
    public void update_whenFailThenThrowNotFoundException() {
        when(orderRepository.findById(FAKE_ID)).thenReturn(Optional.ofNullable(null));
        OrderResponseDTO responseActual = null;
        try {
            responseActual = orderService.update(orderRequestDTO, FAKE_ID);
            fail(FAIL_MSG);
        } catch (NotFoundException e) {
            assertThat(e.getMessage()).isEqualTo("Order id: ["+FAKE_ID+"] not found");
        }
        assertNull(responseActual);
    }

    @Test
    public void delete_whenSucessfull() {
        when(orderRepository.findById(FAKE_ID)).thenReturn(orderOptional);
        try {
            orderService.delete(FAKE_ID);
        } catch (Exception e) {
            e.printStackTrace();
            fail(FAIL_MSG);
        }
    }

    @Test
    public void delete_whenFailThenThrowNotFoundException() {
        when(orderRepository.findById(anyString())).thenReturn(Optional.ofNullable(null));
        try {
            orderService.delete(FAKE_ID);
            fail(FAIL_MSG);
        } catch (NotFoundException e) {
            assertThat(e.getMessage()).isEqualTo("Order id: ["+FAKE_ID+"] not found");
        }
    }
}