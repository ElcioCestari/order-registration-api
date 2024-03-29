package com.brotherselectronics.orderregistration.controllers;

import com.brotherselectronics.orderregistration.domains.dtos.ProductRequestDTO;
import com.brotherselectronics.orderregistration.domains.dtos.ProductResponseDTO;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static com.brotherselectronics.orderregistration.testsutils.GenericFactory.buildObjectOfAnyType;
import static com.brotherselectronics.orderregistration.testsutils.JsonUtils.*;
import static java.util.Arrays.stream;
import static java.util.UUID.randomUUID;
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
    @WithMockUser(authorities = {"ADMIN"})
    @Order(10)
    void save() throws Exception {
        String reqBody = convertObjectToString(buildObjectOfAnyType(ProductRequestDTO.class));
        var response = mockMvc.perform(post(PATH)
                        .contentType(APPLICATION_JSON)
                        .content(reqBody))
                .andDo(print())
                .andExpect(status().isCreated())
                .andReturn().getResponse().getContentAsString();

        product = convertJsonToObject(response, ProductResponseDTO.class);
        assertThat(product).isNotNull();
        assertThat(product.getId()).isNotNull();
    }

    @Test
    @WithMockUser(authorities = {"ADMIN"})
    @Order(11)
    void save_whenSendInvalidBody_thenReturnBadRequest() throws Exception {
        ProductRequestDTO invalidBody = buildObjectOfAnyType(ProductRequestDTO.class);
        invalidBody.setName("");
        mockMvc.perform(post(PATH)
                        .contentType(APPLICATION_JSON)
                        .content(convertObjectToString(invalidBody)))
                .andDo(print())
                .andExpect(status().is4xxClientError());
    }

    @Test
    @WithMockUser(authorities = {"ADMIN"})
    @Order(20)
    void findById() throws Exception {
        mockMvc.perform(get(PATH + "/" + product.getId()))
                .andExpect(status().isOk());
    }

    @Test
    @WithMockUser(authorities = {"ADMIN"})
    @Order(30)
    void findAll_dontGivenParamInRequestDontToBeReturnBadRequest() throws Exception {
        String jsonResponse = mockMvc.perform(get("%s/all".formatted(PATH)))
                .andExpect(status().isOk()).andReturn().getResponse().getContentAsString();
        var dtos = new ObjectMapper().readValue(jsonResponse, ProductResponseDTO[].class);
        assertThat(stream(dtos).toList()).isNotEmpty();
    }

    @Test
    @WithMockUser(authorities = {"ADMIN"})
    @Order(40)
    void update() throws Exception {
        String reqBody = convertObjectToString(buildObjectOfAnyType(ProductRequestDTO.class));
        mockMvc.perform(put(PATH + "/" + product.getId()).contentType(APPLICATION_JSON).content(reqBody))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    @WithMockUser(authorities = {"ADMIN"})
    @Order(41)
    void patchUpdate() throws Exception {
        ProductRequestDTO body = ProductRequestDTO.builder()
                .name(randomUUID().toString())
                .build();

        String response = mockMvc.perform(patch(PATH + "/" + product.getId())
                        .contentType(APPLICATION_JSON)
                        .content(convertObjectToString(body)))
                .andDo(print())
                .andExpect(status().isOk())
                .andReturn()
                .getResponse()
                .getContentAsString();

        var productResponseDTO = stringToObject(response, ProductResponseDTO.class);
        assertThat(productResponseDTO).isNotNull();
        assertThat(productResponseDTO.getName()).isEqualTo(body.getName());
    }

    @Test
    @WithMockUser(authorities = {"ADMIN"})
    @Order(42)
    void patchUpdate_whenSendInvalidBodyThenReturnBadRequest() throws Exception {
        ProductRequestDTO body = ProductRequestDTO.builder()
                .name("")
                .build();

        String response = mockMvc.perform(patch(PATH + "/" + product.getId())
                        .contentType(APPLICATION_JSON)
                        .content(convertObjectToString(body)))
                .andDo(print())
                .andExpect(status().is4xxClientError())
                .andReturn()
                .getResponse()
                .getContentAsString();

        assertThat(response).isNotNull();
    }

    @Test
    @WithMockUser(authorities = {"ADMIN"})
    @Order(50)
    void delete() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.delete(PATH + "/" + product.getId()))
                .andDo(print())
                .andExpect(status().isNoContent());
    }
}