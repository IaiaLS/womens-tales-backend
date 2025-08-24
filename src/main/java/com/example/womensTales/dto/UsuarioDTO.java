package com.example.womensTales.dto;


import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UsuarioDTO {
    private Long id;
    private String nome;
    private String apelido;
    private String profissao;
    private String foto;
    private String usuario;
    private String role;
}
