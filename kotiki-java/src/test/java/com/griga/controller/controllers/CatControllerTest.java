package com.griga.controller.controllers;

import com.griga.service.SpringService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(CatController.class)
class CatControllerTest {
    @MockBean
    SpringService service;
    @Autowired
    private MockMvc mockMvc;

    @Test
    void getByColor_ThrowsException() throws Exception {
        String url = "http://localhost:8080/cat/get-by-color/Red";
        mockMvc.perform(get(url)).andExpect(status().is4xxClientError());
    }
}