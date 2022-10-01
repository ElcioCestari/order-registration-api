package com.brotherselectronics.orderregistration.controllers;

import com.brotherselectronics.orderregistration.domains.dtos.ProductRequestDTO;
import com.brotherselectronics.orderregistration.domains.dtos.SystemUserRequestDTO;
import com.brotherselectronics.orderregistration.domains.dtos.SystemUserResponseDTO;
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
import static com.brotherselectronics.orderregistration.testsutils.JsonUtils.convertJsonToObject;
import static com.brotherselectronics.orderregistration.testsutils.JsonUtils.convertObjectToString;
import static java.lang.Integer.parseInt;
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
class UserControllerTest {
    private static final String PATH = "/users";

    @Autowired
    private WebApplicationContext context;
    private MockMvc mockMvc;
    private static SystemUserResponseDTO responseDTO;

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
        var response = mockMvc.perform(post(PATH)
                        .contentType(APPLICATION_JSON)
                        .content("""
                                        {
                                            "password":"yedUsFwdkelQbxeTeQOvaScfqIOOmaa",
                                            "username":"%s"
                                        }
                                """.formatted(randomUUID().toString())))
                .andDo(print())
                .andExpect(status().isCreated())
                .andReturn().getResponse().getContentAsString();

        responseDTO = convertJsonToObject(response, SystemUserResponseDTO.class);
        assertThat(responseDTO).isNotNull();
        assertThat(responseDTO.getUsername()).isNotNull();
        assertThat(responseDTO.getId()).isNotNull();
    }

    @Test
    @WithMockUser
    @Order(20)
    void findById() throws Exception {
        mockMvc.perform(get(PATH + "/" + responseDTO.getId()))
                .andExpect(status().isOk());
    }

    @Test
    @WithMockUser
    @Order(30)
    void findAll() throws Exception {
        final var size = "10";
        String jsonResponse = mockMvc.perform(get("%s?size=%s&page=1&sort=name".formatted(PATH, size)))
                .andExpect(status().isOk()).andReturn().getResponse().getContentAsString();
        var responseDTOS = new ObjectMapper().readValue(jsonResponse, SystemUserResponseDTO[].class);
        assertThat(stream(responseDTOS).toList())
                .hasSizeGreaterThanOrEqualTo(parseInt(size));
    }

    @Test
    @WithMockUser
    @Order(30)
    void findAll_dontGivenParamInRequestDontToBeReturnBadRequest() throws Exception {
        String jsonResponse = mockMvc.perform(get("%s".formatted(PATH)))
                .andExpect(status().isOk()).andReturn().getResponse().getContentAsString();
        var responseDTOS = new ObjectMapper().readValue(jsonResponse, SystemUserResponseDTO[].class);
        assertThat(stream(responseDTOS).toList()).isNotEmpty();
    }

    @Test
    @WithMockUser(roles = {"ADMIN"})
    @Order(40)
    void update() throws Exception {
        String reqBody = convertObjectToString(buildObjectOfAnyType(ProductRequestDTO.class));
        mockMvc.perform(put(PATH + "/" + responseDTO.getId()).contentType(APPLICATION_JSON).content(reqBody))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    @WithMockUser(roles = {"ADMIN"})
    @Order(50)
    void delete() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.delete(PATH + "/" + responseDTO.getId()))
                .andDo(print())
                .andExpect(status().isNoContent());
    }
}