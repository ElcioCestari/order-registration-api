package com.brotherselectronics.orderregistration.controllers;

import com.brotherselectronics.orderregistration.services.ProductService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

@WebMvcTest(controllers = ProductController.class)
class ProductControllerTest {
    private static final String END_POINT = "/products";

    @Autowired private MockMvc mockMvc;
    @MockBean private ProductService service;

    @BeforeEach
    public void setUp() {
    }

    @Test
    public void findById() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get(END_POINT))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    public void findAll() {
    }

    @Test
    public void save() {
    }

    @Test
    public void update() {
    }

    @Test
    public void delete() {
    }
}