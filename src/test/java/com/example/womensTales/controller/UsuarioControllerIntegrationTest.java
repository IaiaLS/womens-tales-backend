package com.example.womensTales.controller;

import com.example.womensTales.dto.UsuarioCreateDTO;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
class UsuarioControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void deveCadastrarUsuarioComSucesso() throws Exception {
        UsuarioCreateDTO novoUsuario = UsuarioCreateDTO.builder()
                .usuario("anasilvajp@gmail.com")
                .senha("12345678")
                .nome("Ana Silva")
                .apelido("Anasilva")
                .profissao("Engenheira")
                .foto("url_da_foto")
                .build();

        mockMvc.perform(post("/usuarios")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(novoUsuario)))
                .andExpect(status().is(201))
                .andExpect(jsonPath("$.usuario").value("anasilvajp@gmail.com"))
                .andExpect(jsonPath("$.nome").value("Ana Silva"));
    }
}
