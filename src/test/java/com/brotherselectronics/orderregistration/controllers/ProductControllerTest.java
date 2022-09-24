package com.brotherselectronics.orderregistration.controllers;

import com.brotherselectronics.orderregistration.domains.dtos.ProductRequestDTO;
import com.brotherselectronics.orderregistration.domains.dtos.ProductResponseDTO;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static com.brotherselectronics.orderregistration.testsutils.GenericFactory.buildObjectOfAnyType;
import static com.brotherselectronics.orderregistration.testsutils.JsonUtils.convertJsonToObject;
import static com.brotherselectronics.orderregistration.testsutils.JsonUtils.convertObjectToString;
import static java.lang.Integer.parseInt;
import static java.util.Arrays.stream;
import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class ProductControllerTest {
    private static final String PATH = "/products";

    @Autowired
    private WebApplicationContext context;
    private MockMvc mockMvc;
    private static ProductResponseDTO product;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders
                .webAppContextSetup(context)
                .apply(springSecurity())
                .build();
    }

    @Test
    @WithMockUser(roles = {"ADMIN"})
    @Order(10)
    void save() throws Exception {
        String reqBody = convertObjectToString(buildObjectOfAnyType(ProductRequestDTO.class));
        MvcResult response = mockMvc.perform(post(PATH)
                        .contentType(APPLICATION_JSON)
                        .content(reqBody))
                .andDo(print())
                .andExpect(status().isCreated())
                .andReturn();

        String resBody = response.getResponse().getContentAsString();
        product = convertJsonToObject(resBody, ProductResponseDTO.class);
        assertThat(product).isNotNull();
        assertThat(product.getId()).isNotNull();
    }

    @Test
    @WithMockUser
    @Order(20)
    void findById() throws Exception {
        mockMvc.perform(get(PATH + "/" + product.getId()))
                .andExpect(status().isOk());
    }

    @Test
    @WithMockUser
    @Order(30)
    void findAll() throws Exception {
        final var size = "10";
        String jsonResponse = mockMvc.perform(get("%s?size=%s&page=1&sort=name".formatted(PATH, size)))
                .andExpect(status().isOk()).andReturn().getResponse().getContentAsString();
        var responseDTOS = new ObjectMapper().readValue(jsonResponse, ProductResponseDTO[].class);
        assertThat(stream(responseDTOS).toList())
                .hasSizeLessThanOrEqualTo(parseInt(size));
    }

    @Test
    @WithMockUser(roles = {"ADMIN"})
    @Order(40)
    void update() throws Exception {
        String reqBody = convertObjectToString(buildObjectOfAnyType(ProductRequestDTO.class));
        mockMvc.perform(put(PATH + "/" + product.getId()).contentType(APPLICATION_JSON).content(reqBody))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    @WithMockUser(roles = {"ADMIN"})
    @Order(50)
    void delete() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.delete(PATH + "/" + product.getId()))
                .andDo(print())
                .andExpect(status().isNoContent());
    }
}