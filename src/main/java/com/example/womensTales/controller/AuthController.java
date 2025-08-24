package com.example.womensTales.controller;


import com.example.womensTales.dto.UsuarioLoginDTO;
import com.example.womensTales.security.JwtService;
import com.example.womensTales.service.UsuarioService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/auth")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class AuthController {

    private final UsuarioService usuarioService;
    private final JwtService jwtService;

    public AuthController(UsuarioService usuarioService, JwtService jwtService) {
        this.usuarioService = usuarioService;
        this.jwtService = jwtService;
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody UsuarioLoginDTO loginRequest) {
        return usuarioService.autenticarUsuario(loginRequest.getUsuario(), loginRequest.getSenha())
                .map(userDetails -> {
                    String accessToken = jwtService.generateToken(userDetails);
                    String refreshToken = jwtService.generateToken(userDetails); // pode ser diferente depois

                    return ResponseEntity.ok(Map.of(
                            "accessToken", accessToken,
                            "refreshToken", refreshToken
                    ));
                })
                .orElse(ResponseEntity.status(401).body(Map.of("error", "Usuário ou senha inválidos")));
    }


}

