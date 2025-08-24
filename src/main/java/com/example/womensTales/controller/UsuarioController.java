package com.example.womensTales.controller;

import java.util.List;

import com.example.womensTales.dto.UsuarioCreateDTO;
import com.example.womensTales.dto.UsuarioDTO;
import com.example.womensTales.service.UsuarioService;

import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/usuarios")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class UsuarioController {

    private final UsuarioService usuarioService;

    public UsuarioController(UsuarioService usuarioService) {
        this.usuarioService = usuarioService;
    }

    @GetMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<UsuarioDTO>> getAll() {
        return ResponseEntity.ok(usuarioService.getAllUsuarios());
    }

    @PostMapping
    public ResponseEntity<UsuarioDTO> postUsuario(@Valid @RequestBody UsuarioCreateDTO usuario ){
        return usuarioService.cadastrarUsuario(usuario)
                .map(resposta -> ResponseEntity.status(HttpStatus.CREATED).body(resposta))
                .orElse(ResponseEntity.status(HttpStatus.BAD_REQUEST).build());
    }

    @PutMapping
    public ResponseEntity<UsuarioDTO> atualizar(
            @RequestBody UsuarioCreateDTO usuarioDTO,
            @RequestHeader("Authorization") String authHeader) {

        String token = authHeader.replace("Bearer ", "");
        UsuarioDTO atualizado = usuarioService.atualizarUsuario(usuarioDTO, token);
        return ResponseEntity.ok(atualizado);
    }
}
