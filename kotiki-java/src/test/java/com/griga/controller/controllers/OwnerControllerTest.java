package com.griga.controller.controllers;

import com.griga.dao.colors.Color;
import com.griga.dao.entities.Cat;
import com.griga.dao.entities.Owner;
import com.griga.dao.implementations.CatRepository;
import com.griga.dao.implementations.CatsFriendsRepository;
import com.griga.dao.implementations.OwnerRepository;
import com.griga.dao.implementations.OwnersCatsRepository;
import com.griga.dto.CatDTO;
import com.griga.dto.OwnerDTO;
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

@WebMvcTest(OwnerController.class)
class OwnerControllerTest {
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

    private static OwnerDTO ownerDTO;

    @BeforeEach
    public void setUp() {
        Owner owner = new Owner();
        owner.setUsername("lexa");
        owner.setPassword(new BCryptPasswordEncoder().encode("0000"));
        owner.setRole("ROLE_USER");
        owner.setId(1L);

        ownerDTO = new OwnerDTO(owner);

        Mockito.when(securityService.loadUserByUsername("lexa")).thenReturn(new User(ownerDTO.getUsername(), ownerDTO.getPassword(),
                Stream.of(ownerDTO.getRole()).map(SimpleGrantedAuthority::new).collect(Collectors.toList())));
        Mockito.when(ownerService.findUserByUsername("lexa")).thenReturn(ownerDTO);
        Mockito.when(ownerRepository.findByUsername("lexa")).thenReturn(ownerDTO);
    }

    @Test
    public void userCantUseAdminPage() throws Exception {
        String url = "http://localhost:8080/admin";
        this.mockMvc
                .perform(MockMvcRequestBuilders
                        .get(url)
                        .with(SecurityMockMvcRequestPostProcessors.user("lexa")
                                .password("0000")
                                .roles("USER")))
                .andExpect(status().isForbidden());
    }

    @Test
    public void findCatsByColor_ShouldReturnOneBlackCat() throws Exception {
        Cat cat = new Cat();
        cat.setName("denis");
        cat.setColor(Color.Black);
        cat.setBirthdate(Timestamp.valueOf("2021-01-12 00:00:00"));
        cat.setSpecies("basic");
        cat.setId(1L);
        CatDTO catDTO = new CatDTO(cat);
        Mockito.when(ownerService.findUserByUsername("lexa")).thenReturn(ownerDTO);
        Mockito.when(catService.findAllCatsByOwnerId(1L)).thenReturn(List.of(catDTO));
        Mockito.when(ownerRepository.findByUsername("lexa")).thenReturn(ownerDTO);
        Mockito.when(catRepository.findAllByColor(Color.Black)).thenReturn(List.of(catDTO));
        String url = "http://localhost:8080/owner/find-cats-by-color/Black";
        this.mockMvc
                .perform(MockMvcRequestBuilders
                        .get(url)
                        .with(SecurityMockMvcRequestPostProcessors.user("lexa")
                                .password("0000")
                                .roles("USER")))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.content().string(
                        "[{\"id\":1,\"name\":\"denis\",\"birthdate\":\"2021-01-11T21:00:00.000+00:00\"," +
                                "\"species\":\"basic\",\"color\":\"Black\"}]"));
    }


}