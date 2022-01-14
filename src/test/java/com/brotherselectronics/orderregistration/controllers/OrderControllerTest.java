//package com.brotherselectronics.orderregistration.controllers;
//
//import com.brotherselectronics.fakers.OrderFaker;
//import com.brotherselectronics.orderregistration.domains.constraints.ExistsValidator;
//import com.brotherselectronics.orderregistration.domains.dtos.OrderRequestDTO;
//import com.brotherselectronics.orderregistration.domains.dtos.OrderResponseDTO;
//import com.brotherselectronics.orderregistration.domains.entities.Order;
//import com.brotherselectronics.orderregistration.exceptions.NotFoundException;
//import com.brotherselectronics.orderregistration.repositories.BaseRepository;
//import com.brotherselectronics.orderregistration.services.OrderService;
//import com.brotherselectronics.orderregistration.utils.InstancesUtils;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
//import org.springframework.boot.test.mock.mockito.MockBean;
//import org.springframework.http.MediaType;
//import org.springframework.test.web.servlet.MockMvc;
//import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
//
//import java.util.List;
//import java.util.Optional;
//
//import static com.brotherselectronics.orderregistration.testsutils.JsonUtils.convertObjectToString;
//import static java.util.Collections.emptyList;
//import static org.assertj.core.api.Assertions.assertThat;
//import static org.mockito.ArgumentMatchers.any;
//import static org.mockito.ArgumentMatchers.anyString;
//import static org.mockito.Mockito.when;
//import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
//import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
//import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
//import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
//
//@WebMvcTest(controllers = OrderController.class)
//class OrderControllerTest {
//    private static final String END_POINT = "/orders";
//
//    @Autowired private MockMvc mockMvc;
//    @MockBean private OrderService orderService;
//    @MockBean private ExistsValidator existsValidator;
//    @MockBean private InstancesUtils instancesUtils;
//    @MockBean private BaseRepository orderRepository;
//
//    private List<OrderResponseDTO> orderResponseDTOListNotEmpty;
//    private OrderResponseDTO responseDTOStub;
//    private OrderRequestDTO requestDTOStub;
//    private Order orderStub;
//
//    private String jsonList;
//    private String jsonRequestDTOStub;
//    private String jsonResponseDTOStub;
//    private OrderFaker orderFaker;
//
//    @BeforeEach
//    public void setup() {
//        orderFaker = new OrderFaker();
//        responseDTOStub = orderFaker.getResponseDTO();
//        orderResponseDTOListNotEmpty = (List<OrderResponseDTO>) orderFaker.getResponseDTOCollection();
//        jsonList = convertObjectToString(orderResponseDTOListNotEmpty);
//        jsonResponseDTOStub = convertObjectToString(responseDTOStub);
//        requestDTOStub = orderFaker.getRequestDTO();
//        jsonRequestDTOStub = convertObjectToString(requestDTOStub);
//        orderStub = orderFaker.getEntity();
//    }
//
//    @Test
//    public void findById() {
//        assertThat(true);
//    }
//
//    @Test
//    public void findAll_whenHasItensThenReturnAnJsonList() throws Exception {
//        when(orderService.findAll()).thenReturn(orderResponseDTOListNotEmpty);
//        mockMvc.perform(MockMvcRequestBuilders.get(END_POINT))
//                .andExpect(status().isOk());
//    }
//
//    @Test
//    public void findAll_whenHasNoItensThenReturnAnEmptyJson() throws Exception {
//        when(orderService.findAll()).thenReturn(emptyList());
//        mockMvc.perform(MockMvcRequestBuilders.get(END_POINT))
//                .andExpect(status().isOk())
//                .andExpect(content().json("[]"));
//    }
//
//    @Test
//    public void save_whenReceiveAValidRequestDTOThenReturnAResponseDTO() throws Exception {
//        when(orderService.save(any(OrderRequestDTO.class))).thenReturn(responseDTOStub);
//        when(instancesUtils.getRepository(any())).thenReturn(this.orderRepository);
//        when(orderRepository.findById(anyString())).thenReturn(Optional.ofNullable(orderFaker));
//        mockMvc.perform(post(END_POINT)
//                .contentType(MediaType.APPLICATION_JSON)
//                .content(jsonRequestDTOStub))
//                .andExpect(status().isOk());
//
//    }
//
//    @Test
//    public void update_whenReceiveAValidRequestDTOThenReturnAResponseDTO() throws Exception {
//        String fakeId = "any id";
//        when(orderService.update(any(OrderRequestDTO.class), anyString())).thenReturn(responseDTOStub);
//        when(instancesUtils.getRepository(any())).thenReturn(this.orderRepository);
//        when(orderRepository.findById(anyString())).thenReturn(Optional.ofNullable(orderFaker));
//
//        mockMvc.perform(put(END_POINT+"/"+fakeId)
//                .contentType(MediaType.APPLICATION_JSON)
//                .content(jsonRequestDTOStub))
//                .andExpect(status().isOk());
//    }
//
//    @Test
//    public void update_whenReceiveAnInvalidRequestThenReturnANotFound() throws Exception {
//        String fakeId = "any id";
//        String msg = "Resource not found with id: " + fakeId;
//        when(orderService.update(any(OrderRequestDTO.class), anyString())).thenThrow(new NotFoundException((msg)));
//        when(instancesUtils.getRepository(any())).thenReturn(this.orderRepository);
//        when(orderRepository.findById(anyString())).thenReturn(Optional.ofNullable(orderFaker));
//
//        mockMvc.perform(put(END_POINT+"/"+fakeId)
//                .contentType(MediaType.APPLICATION_JSON)
//                .content(jsonRequestDTOStub))
//                .andExpect(status().isNotFound());
//    }
//
//    @Test
//    public void delete(){
//        assertThat(true);
//    }
//}