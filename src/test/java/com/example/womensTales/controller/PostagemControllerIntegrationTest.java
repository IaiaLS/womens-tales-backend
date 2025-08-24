package com.example.womensTales.controller;

import com.example.womensTales.dto.PostagemDTO;
import com.example.womensTales.entity.TemaEntity;
import com.example.womensTales.entity.UsuarioEntity;
import com.example.womensTales.enums.RoleEnum;
import com.example.womensTales.repository.TemaRepository;
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

import java.time.LocalDate;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
class PostagemControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private TemaRepository temaRepository;

    @Autowired
    private JwtService jwtService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private ObjectMapper objectMapper;

    private String token;
    private Long temaId;

    @BeforeEach
    void setup() {
        usuarioRepository.deleteAll();
        temaRepository.deleteAll();

        // Cria usuário fake
        UsuarioEntity user = UsuarioEntity.builder()
                .usuario("testeuser")
                .nome("Teste User")
                .senha(passwordEncoder.encode("12345678"))
                .role(RoleEnum.USER)
                .build();
        usuarioRepository.save(user);

        // Cria tema fake
        TemaEntity tema = TemaEntity.builder()
                .titulo("Direitos das Mulheres")
                .status(true)
                .palavraChave("igualdade")
                .build();
        temaRepository.save(tema);
        temaId = tema.getId();

        // Gera token JWT
        token = "Bearer " + jwtService.generateToken(
                new org.springframework.security.core.userdetails.User(
                        user.getUsuario(),
                        user.getSenha(),
                        java.util.List.of(() -> "ROLE_USER")
                )
        );
    }

    @Test
    void deveCriarEListarPostagemComSucesso() throws Exception {
        PostagemDTO postagem = PostagemDTO.builder()
                .titulo("Primeira Postagem")
                .texto("Texto de exemplo para a postagem")
                .categoria("Notícia")
                .midia("https://imagem.com/img1.jpg")
                .temaId(temaId) // obrigatório
                .data(LocalDate.now()) // pode ser sobrescrito no service
                .build();

        // Criar postagem
        mockMvc.perform(post("/postagens")
                        .header("Authorization", token)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(postagem)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").exists())
                .andExpect(jsonPath("$.titulo").value("Primeira Postagem"));

        // Listar todas
        mockMvc.perform(get("/postagens")
                        .header("Authorization", token))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].titulo").value("Primeira Postagem"));
    }
}
