package com.brotherselectronics.orderregistration.controllers;

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

import java.util.Collections;
import java.util.Set;

import static com.brotherselectronics.orderregistration.testsutils.JsonUtils.convertJsonToObject;
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
    @WithMockUser(authorities = {"ADMIN"})
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
    @WithMockUser(roles = {"ADMIN"})
    @Order(10)
    void save_whenSendInValidAuthority_thenReturnBadRequest() throws Exception {
        mockMvc.perform(post(PATH)
                        .contentType(APPLICATION_JSON)
                        .content("""
                                        {
                                            "password":"yedUsFwdkelQbxeTeQOvaScfqIOOmaa",
                                            "username":"%s",
                                            "authorities": ["BANANA"]
                                        }
                                """.formatted(randomUUID().toString())))
                .andDo(print())
                .andExpect(status().is4xxClientError());
    }

    @Test
    @WithMockUser(authorities = {"ADMIN"})
    @Order(20)
    void findById() throws Exception {
        mockMvc.perform(get(PATH + "/" + responseDTO.getId()))
                .andExpect(status().isOk());
    }

    @Test
    @WithMockUser(authorities = {"ADMIN"})
    @Order(21)
    void findById_whenNotFound_thenAssertThatStatusCodeIs404() throws Exception {
        mockMvc.perform(get(PATH + "/" + "undefined"))
                .andExpect(status().isNotFound());
    }

    @Test
    @WithMockUser(authorities = {"ADMIN"})
    @Order(30)
    void findAll() throws Exception {
        String jsonResponse = mockMvc.perform(get("%s?size=%s&page=1&sort=name".formatted(PATH, "10")))
                .andExpect(status().isOk()).andReturn().getResponse().getContentAsString();
        var dtos = new ObjectMapper().readValue(jsonResponse, SystemUserResponseDTO[].class);
        assertThat(stream(dtos).toList()).isNotEmpty();
    }

    @Test
    @WithMockUser(authorities = {"ADMIN"})
    @Order(30)
    void findAll_dontGivenParamInRequestDontToBeReturnBadRequest() throws Exception {
        String jsonResponse = mockMvc.perform(get("%s".formatted(PATH)))
                .andExpect(status().isOk()).andReturn().getResponse().getContentAsString();
        var dtos = new ObjectMapper().readValue(jsonResponse, SystemUserResponseDTO[].class);
        assertThat(stream(dtos).toList()).isNotEmpty();
    }

    @Test
    @WithMockUser(authorities = {"ADMIN"})
    @Order(41)
    void update_whenSendUsername_thenIgnoreNewUsername() throws Exception {
        var response = mockMvc
                .perform(get(PATH + "/" + responseDTO.getId()))
                .andDo(print())
                .andExpect(status().isOk())
                .andReturn()
                .getResponse()
                .getContentAsString();

        final var userBeforeUpdate = new ObjectMapper()
                .readValue(response, SystemUserResponseDTO.class);

        final Set<String> authoritiesBeforeUpdate = Collections.unmodifiableSet(userBeforeUpdate.getAuthorities());

        response = mockMvc.perform(put(PATH + "/" + responseDTO.getId())
                        .contentType(APPLICATION_JSON)
                        .content("""
                                        {
                                            "username": "elcio",
                                            "password": "elcio",
                                            "authorities": [
                                                "ADMIN"
                                            ],
                                            "accountNonExpired": true,
                                            "accountNonLocked": true,
                                            "credentialsNonExpired": true,
                                            "enabled": true
                                        }
                                """))
                .andDo(print())
                .andExpect(status().isOk())
                .andReturn()
                .getResponse()
                .getContentAsString();

        final var userAfterUpdate = new ObjectMapper().readValue(response, SystemUserResponseDTO.class);
        assertThat(userAfterUpdate).isNotNull();
        assertThat(userAfterUpdate.getId()).isEqualTo(userBeforeUpdate.getId());
        assertThat(userAfterUpdate.getUsername()).isEqualTo(userBeforeUpdate.getUsername());
        assertThat(userAfterUpdate.getAuthorities()).isNotEqualTo(authoritiesBeforeUpdate);
    }

    @Test
    @WithMockUser(authorities = {"ADMIN"})
    @Order(42)
    void update_whenNotFound_thenAssertThatStatusCodeIsNotFound() throws Exception {
        mockMvc.perform(put(PATH + "/undefined")
                        .contentType(APPLICATION_JSON)
                        .content("""
                                        {
                                            "username": "elcio",
                                            "password": "elcio",
                                            "authorities": [
                                                "ADMIN"
                                            ],
                                            "accountNonExpired": true,
                                            "accountNonLocked": true,
                                            "credentialsNonExpired": true,
                                            "enabled": true
                                        }
                                """))
                .andDo(print())
                .andExpect(status().isNotFound());
    }

    @Test
    @WithMockUser(authorities = {"ADMIN"})
    @Order(50)
    void delete() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.delete(PATH + "/" + responseDTO.getId()))
                .andDo(print())
                .andExpect(status().isNoContent());
    }
}