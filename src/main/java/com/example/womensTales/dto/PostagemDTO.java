package com.example.womensTales.dto;

import lombok.*;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PostagemDTO {
    private Long id;
    private String titulo;
    private String texto;
    private String categoria;
    private String midia;
    private LocalDate data;
    private Long usuarioId;
    private Long temaId;
}
