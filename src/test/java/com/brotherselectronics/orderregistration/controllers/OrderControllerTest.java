package com.brotherselectronics.orderregistration.controllers;

import com.brotherselectronics.fakers.OrderFaker;
import com.brotherselectronics.orderregistration.domains.constraints.ExistsValidator;
import com.brotherselectronics.orderregistration.domains.dtos.OrderRequestDTO;
import com.brotherselectronics.orderregistration.domains.dtos.OrderResponseDTO;
import com.brotherselectronics.orderregistration.domains.entities.Order;
import com.brotherselectronics.orderregistration.exceptions.NotFoundException;
import com.brotherselectronics.orderregistration.repositories.BaseRepository;
import com.brotherselectronics.orderregistration.services.OrderService;
import com.brotherselectronics.orderregistration.utils.InstancesUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.util.List;
import java.util.Optional;

import static com.brotherselectronics.orderregistration.testsutils.JsonUtils.convertObjectToString;
import static java.util.Collections.emptyList;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class OrderControllerTest {
    private static final String END_POINT = "/orders";

    @Autowired
    private WebApplicationContext context;

    private MockMvc mockMvc;
    @MockBean
    private OrderService orderService;
    @MockBean
    private ExistsValidator existsValidator;
    @MockBean
    private InstancesUtils instancesUtils;
    @MockBean
    private BaseRepository orderRepository;

    private List<OrderResponseDTO> orderResponseDTOListNotEmpty;
    private OrderResponseDTO responseDTOStub;
    private OrderRequestDTO requestDTOStub;
    private Order orderStub;

    private String jsonList;
    private String jsonRequestDTOStub;
    private String jsonResponseDTOStub;
    private OrderFaker orderFaker;

    @BeforeEach
    public void setup() {
        mockMvc = MockMvcBuilders.webAppContextSetup(context).apply(springSecurity()).build();
        orderFaker = new OrderFaker();
        responseDTOStub = orderFaker.getResponseDTO();
        orderResponseDTOListNotEmpty = (List<OrderResponseDTO>) orderFaker.getResponseDTOCollection();
        jsonList = convertObjectToString(orderResponseDTOListNotEmpty);
        jsonResponseDTOStub = convertObjectToString(responseDTOStub);
        requestDTOStub = orderFaker.getRequestDTO();
        jsonRequestDTOStub = convertObjectToString(requestDTOStub);
        orderStub = orderFaker.getEntity();
    }

    @Test
    public void findById() {
        assertThat(true);
    }

    @Test
    @WithMockUser("spring")
    public void findAll_whenHasItemsThenReturnAnJsonList() throws Exception {
        when(orderService.findAll()).thenReturn(orderResponseDTOListNotEmpty);
        mockMvc.perform(MockMvcRequestBuilders.get(END_POINT))
                .andExpect(status().isOk());
    }

    @Test
    @WithMockUser("spring")
    public void findAll_whenHasNoItemsThenReturnAnEmptyJson() throws Exception {
        when(orderService.findAll()).thenReturn(emptyList());
        mockMvc.perform(MockMvcRequestBuilders.get(END_POINT))
                .andExpect(status().isOk())
                .andExpect(content().json("[]"));
    }

    @Test
    @WithMockUser("spring")
    public void save_whenReceiveAValidRequestDTOThenReturnAResponseDTO() throws Exception {
        when(orderService.save(any(OrderRequestDTO.class))).thenReturn(responseDTOStub);
        when(instancesUtils.getRepository(any())).thenReturn(this.orderRepository);
        when(orderRepository.findById(anyString())).thenReturn(Optional.ofNullable(orderFaker));
        mockMvc.perform(post(END_POINT)
                        .contentType(APPLICATION_JSON)
                        .content(jsonRequestDTOStub))
                .andExpect(status().isOk());

    }

    @Test
    @WithMockUser("spring")
    public void update_whenReceiveAValidRequestDTOThenReturnAResponseDTO() throws Exception {
        String fakeId = "any id";
        when(orderService.update(any(OrderRequestDTO.class), anyString())).thenReturn(responseDTOStub);
        when(instancesUtils.getRepository(any())).thenReturn(this.orderRepository);
        when(orderRepository.findById(anyString())).thenReturn(Optional.ofNullable(orderFaker));

        mockMvc.perform(put(END_POINT + "/" + fakeId)
                        .contentType(APPLICATION_JSON)
                        .content(jsonRequestDTOStub))
                .andExpect(status().isOk());
    }

    @Test
    @WithMockUser("spring")
    public void update_whenReceiveAnInvalidRequestThenReturnANotFound() throws Exception {
        String fakeId = "any id";
        String msg = "Resource not found with id: " + fakeId;
        when(orderService.update(any(OrderRequestDTO.class), anyString())).thenThrow(new NotFoundException((msg)));
        when(instancesUtils.getRepository(any())).thenReturn(this.orderRepository);
        when(orderRepository.findById(anyString())).thenReturn(Optional.ofNullable(orderFaker));

        mockMvc.perform(put(END_POINT + "/" + fakeId)
                        .contentType(APPLICATION_JSON)
                        .content(jsonRequestDTOStub))
                .andExpect(status().isNotFound());
    }

    @Test
    @WithMockUser("spring")
    public void delete() {
        assertThat(true);
    }
}