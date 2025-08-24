package com.example.womensTales.controller;

import com.example.womensTales.dto.TemaDTO;
import com.example.womensTales.entity.UsuarioEntity;
import com.example.womensTales.enums.RoleEnum;
import com.example.womensTales.repository.UsuarioRepository;
import com.example.womensTales.security.JwtService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
class TemaControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private JwtService jwtService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private ObjectMapper objectMapper;

    private String token;

    @BeforeEach
    void setup() {
        usuarioRepository.deleteAll();

        // cria um usuário fake no banco de teste (H2)
        UsuarioEntity user = UsuarioEntity.builder()
                .usuario("testeuser")
                .nome("Teste User")
                .senha(passwordEncoder.encode("12345678"))
                .role(RoleEnum.USER)
                .build();

        usuarioRepository.save(user);

        // gera token válido
        token = "Bearer " + jwtService.generateToken(
                new org.springframework.security.core.userdetails.User(
                        user.getUsuario(),
                        user.getSenha(),
                        java.util.List.of(() -> "ROLE_USER")
                )
        );
    }

    @Test
    void deveCriarETrazerTemaComSucesso() throws Exception {
        TemaDTO tema = TemaDTO.builder()
                .titulo("Direitos das Mulheres")
                .status(true)
                .palavraChave("igualdade")
                .build();

        // cria tema
        mockMvc.perform(post("/temas")
                        .header("Authorization", token)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(tema)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").exists())
                .andExpect(jsonPath("$.titulo").value("Direitos das Mulheres"));

        // busca todos
        mockMvc.perform(get("/temas")
                        .header("Authorization", token))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].titulo").value("Direitos das Mulheres"));
    }
}
