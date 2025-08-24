package com.example.womensTales.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UsuarioCreateDTO {

    @NotBlank(message = "O nome é obrigatório")
    @Size(min = 5, message = "O nome deve ter no mínimo 5 caracteres")
    private String nome;

    @Size(min = 5, message = "O apelido deve ter no mínimo 5 caracteres")
    private String apelido;

    private String profissao;

    private String foto;

    @NotBlank(message = "O usuário é obrigatório")
    private String usuario;

    @NotBlank(message = "A senha é obrigatória")
    @Size(min = 8, message = "A senha deve ter no mínimo 8 caracteres")
    private String senha;
}
