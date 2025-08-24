package com.example.womensTales.entity;


import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.time.LocalDate;

@Entity
@Table(name = "tb_postagem")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PostagemEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String categoria;

    @NotBlank
    @Size(min = 5, max = 100)
    private String titulo;

    @Lob
    @NotBlank
    private String texto;

    private String midia;

    @Column(nullable = false)
    private LocalDate data;

    @ManyToOne
    @JoinColumn(name = "tema_id", nullable = false)
    private TemaEntity tema;

    @ManyToOne
    @JoinColumn(name = "usuario_id", nullable = false)
    private UsuarioEntity usuario;

    @PrePersist
    public void prePersist() {
        this.data = LocalDate.now();
    }

}
