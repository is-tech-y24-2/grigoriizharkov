package com.griga.controller.controllers;

import com.griga.dao.entities.Cat;
import com.griga.dao.entities.CatsFriends;
import com.griga.dao.entities.Owner;
import com.griga.dao.entities.OwnersCats;
import com.griga.dao.implementations.CatRepository;
import com.griga.dao.implementations.CatsFriendsRepository;
import com.griga.dao.implementations.OwnerRepository;
import com.griga.dao.implementations.OwnersCatsRepository;
import com.griga.service.SpringService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import javax.persistence.EntityNotFoundException;

import static org.junit.jupiter.api.Assertions.*;
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
        String url = "http://localhost:8080/cat/getbycolor/Red";
        mockMvc.perform(get(url)).andExpect(status().is4xxClientError());
    }
}