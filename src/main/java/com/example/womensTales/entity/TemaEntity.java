package com.example.womensTales.entity;


import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.util.List;

@Entity
@Table(name = "tb_tema")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TemaEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    @Size(min = 2, max = 255)
    private String titulo;

    @Column(nullable = false)
    private boolean status;

    @Size(min = 2, max = 100)
    private String palavraChave;

    @OneToMany(mappedBy = "tema")
    private List<PostagemEntity> postagens;
}
