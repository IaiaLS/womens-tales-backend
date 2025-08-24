package com.example.womensTales.entity;


import com.example.womensTales.enums.RoleEnum;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.util.List;

@Entity
@Table(name = "tb_usuario")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UsuarioEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    @Size(min = 5)
    private String nome;

    private String apelido;
    private String profissao;
    private String foto;

    @Column(unique = true, nullable = false)
    private String usuario;

    @NotBlank
    @Size(min = 8)
    private String senha;

    @Enumerated(EnumType.STRING)
    private RoleEnum role;

    @OneToMany(mappedBy = "usuario", cascade = CascadeType.ALL)
    private List<PostagemEntity> postagens;
}
