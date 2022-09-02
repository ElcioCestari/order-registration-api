package com.brotherselectronics.orderregistration.services;

import com.brotherselectronics.fakers.EntityFake;
import com.brotherselectronics.fakers.OrderFaker;
import com.brotherselectronics.orderregistration.domains.dtos.OrderRequestDTO;
import com.brotherselectronics.orderregistration.domains.dtos.OrderResponseDTO;
import com.brotherselectronics.orderregistration.domains.entities.Order;
import com.brotherselectronics.orderregistration.domains.mappers.OrderMapper;
import com.brotherselectronics.orderregistration.exceptions.NotFoundException;
import com.brotherselectronics.orderregistration.repositories.OrderRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;

import static com.brotherselectronics.fakers.BaseEntityFake.FAKE_ID;
import static java.util.Collections.emptyList;
import static java.util.Optional.empty;
import static java.util.Optional.ofNullable;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class)
class OrderServiceTest {
    private static final String FAIL_MSG = "Must to be throw an exception and don't was threw.";
    private static final EntityFake<Order, OrderRequestDTO, OrderResponseDTO> fake = new OrderFaker();

    @InjectMocks
    private OrderService orderService;
    @Mock
    private OrderRepository orderRepository;
    @Mock
    private OrderMapper mapper;

    private List<Order> orderListNotEmpty;
    private List<OrderResponseDTO> dtoListNotEmpty;
    private OrderResponseDTO orderResponseDTO;
    private OrderRequestDTO orderRequestDTO;
    private Order order;

    @BeforeEach
    void setUp() {
        order = fake.getEntity();
        order.setId(FAKE_ID);
        orderListNotEmpty = List.of(order);
        dtoListNotEmpty = List.of(new OrderResponseDTO());
        orderResponseDTO = new OrderResponseDTO();
        orderRequestDTO = new OrderRequestDTO();
    }

    @Test
    void findAll_whenHasItensToReturnThenAssertThatListItsNotEmpty() {
        when(orderRepository.findAll()).thenReturn(orderListNotEmpty);
        when(mapper.toDtoResponseList(Mockito.anyList())).thenReturn(dtoListNotEmpty);
        List<OrderResponseDTO> all = orderService.findAll();
        assertThat(all).isNotEmpty();
    }

    @Test
    void findAll_whenHasNotItensToReturnThenAssertThatListItsEmpty() {
        when(orderRepository.findAll()).thenReturn(emptyList());
        when(mapper.toDtoResponseList(Mockito.anyList())).thenReturn(emptyList());
        List<OrderResponseDTO> all = orderService.findAll();
        assertThat(all).isEmpty();
    }

    @Test
    void findById_whenHasOrderThenAssertThatItsNotNull() {
        when(orderRepository.findById(anyString())).thenReturn(ofNullable(order));
        when(mapper.toDtoResponse(any(Order.class))).thenReturn(orderResponseDTO);
        OrderResponseDTO responseActual = orderService.findById(FAKE_ID);
        assertNotNull(responseActual);
    }

    @Test
    void findById_whenHasNotOrderThenAssertThatItsThrowAnException() {
        when(orderRepository.findById(anyString())).thenReturn(empty());
        when(mapper.toDtoResponse(any(Order.class))).thenReturn(orderResponseDTO);
        try {
            orderService.findById(FAKE_ID);
            fail(FAIL_MSG);
        } catch (NotFoundException e) {
            assertThat(e.getMessage()).isEqualTo("Order not found with id: " + FAKE_ID);
        }
    }

    @Test
    void save_whenCallMethodWithAnyOrderRequestDTOThenSaveAndReturnAnOrderResponseDTO() {
        when(orderRepository.save(any(Order.class))).thenReturn(order);
        when(mapper.toDtoResponse(any(Order.class))).thenReturn(orderResponseDTO);
        when(mapper.toEntity(any(OrderRequestDTO.class))).thenReturn(order);
        OrderResponseDTO responseActual = orderService.save(orderRequestDTO);
        assertNotNull(responseActual);
    }

    @Test
    void update_whenSuccessfulThenReturnAnOrderResponseDTO() {
        when(orderRepository.findById(FAKE_ID)).thenReturn(ofNullable(order));
        when(mapper.toEntity(any(OrderRequestDTO.class))).thenReturn(order);
        when(orderRepository.save(any(Order.class))).thenReturn(order);
        when(mapper.toDtoResponse(any(Order.class))).thenReturn(orderResponseDTO);
        OrderResponseDTO responseActual = orderService.update(orderRequestDTO, FAKE_ID);
        assertNotNull(responseActual);
    }

    @Test
    void update_whenFailThenThrowNotFoundException() {
        when(orderRepository.findById(FAKE_ID)).thenReturn(empty());
        OrderResponseDTO responseActual = null;
        try {
            responseActual = orderService.update(orderRequestDTO, FAKE_ID);
            fail(FAIL_MSG);
        } catch (NotFoundException e) {
            assertThat(e.getMessage()).isEqualTo("Order not found with id: " + FAKE_ID);
        }
        assertNull(responseActual);
    }

    @Test
    void delete_whenSuccessful() {
        when(orderRepository.findById(FAKE_ID)).thenReturn(ofNullable(order));
        try {
            orderService.delete(FAKE_ID);
        } catch (Exception e) {
            e.printStackTrace();
            fail(FAIL_MSG);
        }
    }

    @Test
    void delete_whenFailThenThrowNotFoundException() {
        when(orderRepository.findById(anyString())).thenReturn(empty());
        try {
            orderService.delete(FAKE_ID);
            fail(FAIL_MSG);
        } catch (NotFoundException e) {
            assertThat(e.getMessage()).isEqualTo("Order not found with id: " + FAKE_ID);
        }
    }
}