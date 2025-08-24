package com.example.womensTales.dto;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TemaDTO {
    private Long id;
    private String titulo;
    private boolean status;
    private String palavraChave;
}
