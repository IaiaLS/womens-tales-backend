package com.example.womensTales.dto;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UsuarioLoginDTO {
    private String usuario;
    private String senha;
}
