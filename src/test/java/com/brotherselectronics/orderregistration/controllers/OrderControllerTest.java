package com.brotherselectronics.orderregistration.controllers;

import com.brotherselectronics.orderregistration.domains.dtos.OrderRequestDTO;
import com.brotherselectronics.orderregistration.domains.dtos.OrderResponseDTO;
import com.brotherselectronics.orderregistration.services.OrderService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.List;

import static com.brotherselectronics.orderregistration.testsutils.JsonUtils.*;
import static java.util.Collections.emptyList;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = OrderController.class)
class OrderControllerTest {
    private static final String END_POINT = "/orders";

    @Autowired private MockMvc mockMvc;
    @MockBean private OrderService orderService;

    private List<OrderResponseDTO> orderResponseDTOListNotEmpty;
    private OrderResponseDTO responseDTOStub;
    private OrderRequestDTO requestDTOStub;

    private String jsonList;
    private String jsonRequestDTOStub;
    private String jsonResponseDTOStub;

    @BeforeEach
    public void setup() {
        responseDTOStub = new OrderResponseDTO();
        orderResponseDTOListNotEmpty = List.of(responseDTOStub);
        jsonList = convertObjectToString(orderResponseDTOListNotEmpty);
        jsonResponseDTOStub = convertObjectToString(responseDTOStub);
        requestDTOStub = new OrderRequestDTO();
        jsonRequestDTOStub = convertObjectToString(requestDTOStub);
    }

    @Test
    public void findById() {
        assertThat(true);
    }

    @Test
    public void findAll_whenHasItensThenReturnAnJsonList() throws Exception {
        when(orderService.findAll()).thenReturn(orderResponseDTOListNotEmpty);
        mockMvc.perform(MockMvcRequestBuilders.get(END_POINT))
                .andExpect(status().isOk())
                .andExpect(content().json(jsonList));
    }

    @Test
    public void findAll_whenHasNoItensThenReturnAnEmptyJson() throws Exception {
        when(orderService.findAll()).thenReturn(emptyList());
        mockMvc.perform(MockMvcRequestBuilders.get(END_POINT))
                .andExpect(status().isOk())
                .andExpect(content().json("[]"));
    }

    @Test
    public void save_whenReceiveAValidRequestDTOThenRetorneAResponseDTO() throws Exception {
        when(orderService.save(any(OrderRequestDTO.class))).thenReturn(responseDTOStub);
        mockMvc.perform(post(END_POINT)
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonRequestDTOStub))
                .andExpect(status().isOk())
                .andExpect(content().json(jsonResponseDTOStub));
    }

    @Test
    public void update(){
        assertThat(true);
    }

    @Test
    public void delete(){
        assertThat(true);
    }
}