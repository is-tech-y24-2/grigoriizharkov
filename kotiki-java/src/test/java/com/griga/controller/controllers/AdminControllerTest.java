package com.griga.controller.controllers;

import com.griga.dao.colors.Color;
import com.griga.dao.entities.Cat;
import com.griga.dao.entities.Owner;
import com.griga.dao.implementations.CatRepository;
import com.griga.dao.implementations.CatsFriendsRepository;
import com.griga.dao.implementations.OwnerRepository;
import com.griga.dao.implementations.OwnersCatsRepository;
import com.griga.dto.CatDto;
import com.griga.dto.OwnerDto;
import com.griga.service.services.CatService;
import com.griga.service.services.OwnerService;
import com.griga.service.services.SecurityService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.sql.Timestamp;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(AdminController.class)
class AdminControllerTest {
    @MockBean
    SecurityService securityService;
    @MockBean
    CatService catService;
    @MockBean
    OwnerService ownerService;

    @MockBean
    OwnerRepository ownerRepository;
    @MockBean
    CatRepository catRepository;
    @MockBean
    OwnersCatsRepository ownersCatsRepository;
    @MockBean
    CatsFriendsRepository catsFriendsRepository;

    @Autowired
    private MockMvc mockMvc;

    @BeforeEach
    public void setUp() {
        Owner owner = new Owner();
        owner.setUsername("griga");
        owner.setPassword(new BCryptPasswordEncoder().encode("0000"));
        owner.setRole("ROLE_ADMIN");

        OwnerDto ownerDto = new OwnerDto(owner);

        Mockito.when(securityService.loadUserByUsername("griga")).thenReturn(new User(ownerDto.getUsername(), ownerDto.getPassword(),
                Stream.of(ownerDto.getRole()).map(SimpleGrantedAuthority::new).collect(Collectors.toList())));
        Mockito.when(ownerService.findUserByUsername("griga")).thenReturn(ownerDto);
        Mockito.when(ownerRepository.findByUsername("griga")).thenReturn(ownerDto);

    }

    @Test
    public void adminCanUseAdminPage() throws Exception {
        String url = "http://localhost:8080/admin";
        this.mockMvc
                .perform(MockMvcRequestBuilders
                        .get(url)
                        .with(SecurityMockMvcRequestPostProcessors.user("griga")
                                .password("0000")
                                .roles("ADMIN")))
                .andExpect(status().isOk());
    }

    @Test
    public void findAllCats_ShouldReturnOneAddedCat() throws Exception {
        Cat cat = new Cat();
        cat.setName("denis");
        cat.setColor(Color.Black);
        cat.setBirthdate(Timestamp.valueOf("2021-01-12 00:00:00"));
        cat.setSpecies("basic");
        cat.setId(1L);
        CatDto catDto = new CatDto(cat);
        Mockito.when(catService.findAllCats()).thenReturn(List.of(catDto));
        Mockito.when(catRepository.findAll()).thenReturn(List.of(cat));
        String url = "http://localhost:8080/admin/find-all-cats";
        this.mockMvc
                .perform(MockMvcRequestBuilders
                        .get(url)
                        .with(SecurityMockMvcRequestPostProcessors.user("griga")
                                .password("0000")
                                .roles("ADMIN")))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.content().string(
                        "[{\"id\":1,\"name\":\"denis\",\"birthdate\":\"2021-01-11T21:00:00.000+00:00\"," +
                                "\"species\":\"basic\",\"color\":\"Black\"}]"));
    }

}