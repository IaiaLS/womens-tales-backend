package com.example.womensTales.controller;


import com.example.womensTales.model.UsuarioLogin;
import com.example.womensTales.security.JwtService;
import com.example.womensTales.service.UsuarioService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.Optional;

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
    public ResponseEntity<?> login(@RequestBody UsuarioLogin loginRequest) {
        Optional<UsuarioLogin> usuario = usuarioService.autenticarUsuario(Optional.of(loginRequest));

        if (usuario.isEmpty()) {
            return ResponseEntity.status(401).body(Map.of("error", "Usuário ou senha inválidos"));
        }

        String accessToken = jwtService.generateToken(loginRequest.getUsuario());
        String refreshToken = jwtService.generateToken(loginRequest.getUsuario());

        return ResponseEntity.ok(Map.of(
                "accessToken", accessToken,
                "refreshToken", refreshToken
        ));
    }
}

