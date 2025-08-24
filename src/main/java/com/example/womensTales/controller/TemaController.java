package com.example.womensTales.controller;

import com.example.womensTales.dto.TemaDTO;
import com.example.womensTales.service.TemaService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/temas")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class TemaController {

    private final TemaService temaService;

    public TemaController(TemaService temaService) {
        this.temaService = temaService;
    }

    @GetMapping
    public ResponseEntity<List<TemaDTO>> getAll() {
        return ResponseEntity.ok(temaService.getAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<TemaDTO> getById(@PathVariable Long id) {
        return ResponseEntity.ok(temaService.getById(id));
    }

    @GetMapping("/titulo/{titulo}")
    public ResponseEntity<List<TemaDTO>> getByTitulo(@PathVariable String titulo) {
        return ResponseEntity.ok(temaService.getByTitulo(titulo));
    }

    @PostMapping()
    public ResponseEntity<TemaDTO> create(@Valid @RequestBody TemaDTO dto,
                                            @RequestHeader("Authorization") String authHeader) {
        return ResponseEntity.status(HttpStatus.CREATED).body(temaService.create(dto, authHeader.replace("Bearer ", "")));
    }

    @PutMapping
    public ResponseEntity<TemaDTO> update(@Valid @RequestBody TemaDTO dto,
                                          @RequestHeader("Authorization") String authHeader) {
        return ResponseEntity.ok(temaService.update(dto, authHeader.replace("Bearer ", "")));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id,
                                       @RequestHeader("Authorization") String authHeader) {
        temaService.delete(id, authHeader.replace("Bearer ", ""));
        return ResponseEntity.noContent().build();
    }
}
