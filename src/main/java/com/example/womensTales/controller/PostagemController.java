package com.example.womensTales.controller;

import com.example.womensTales.dto.PostagemDTO;
import com.example.womensTales.service.PostagemService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/postagens")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class PostagemController {

    private final PostagemService postagemService;

    public PostagemController(PostagemService postagemService) {
        this.postagemService = postagemService;
    }

    @GetMapping
    public ResponseEntity<List<PostagemDTO>> getAll() {
        return ResponseEntity.ok(postagemService.getAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<PostagemDTO> getById(@PathVariable Long id) {
        return ResponseEntity.ok(postagemService.getById(id));
    }

    @GetMapping("/titulo/{titulo}")
    public ResponseEntity<List<PostagemDTO>> getByTitulo(@PathVariable String titulo) {
        return ResponseEntity.ok(postagemService.getByTitulo(titulo));
    }

    @PostMapping
    public ResponseEntity<PostagemDTO> create(
            @RequestBody @Valid PostagemDTO dto,
            @RequestHeader("Authorization") String authHeader) {

        String token = authHeader.replace("Bearer ", "");
        PostagemDTO nova = postagemService.create(dto, token);
        return ResponseEntity.status(HttpStatus.CREATED).body(nova);
    }

    @PutMapping
    public ResponseEntity<PostagemDTO> update(@Valid @RequestBody PostagemDTO dto,
                                              @RequestHeader("Authorization") String authHeader) {
        return ResponseEntity.ok(postagemService.update(dto, authHeader.replace("Bearer ", "")));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id,
                                       @RequestHeader("Authorization") String authHeader) {
        postagemService.delete(id, authHeader.replace("Bearer ", ""));
        return ResponseEntity.noContent().build();
    }
}
