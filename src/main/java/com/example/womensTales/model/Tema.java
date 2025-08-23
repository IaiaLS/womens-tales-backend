package com.example.womensTales.model;

import java.util.List;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Entity
@Table(name = "tb_tema")
public class Tema {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @NotBlank(message = "O atributo TÍTULO é obrigatório!")
    @Size(min = 2, max = 255, message = "O atributo TÍTULO deve ter entre 2 e 255 caracteres!")
    private String titulo;

    private boolean status;

    @Size(min = 2, max = 100, message = "O atributo PALAVRA-CHAVE deve ter entre 2 e 100 caracteres!")
    private String palavraChave;

    @OneToMany(mappedBy = "tema", cascade = CascadeType.ALL)
    @JsonIgnoreProperties("tema")
    private List<Postagem> postagem;

    // Getters e Setters
    public long getId() { return id; }
    public void setId(long id) { this.id = id; }

    public String getTitulo() { return titulo; }
    public void setTitulo(String titulo) { this.titulo = titulo; }

    public boolean isStatus() { return status; }
    public void setStatus(boolean status) { this.status = status; }

    public String getPalavraChave() { return palavraChave; }
    public void setPalavraChave(String palavraChave) { this.palavraChave = palavraChave; }

    public List<Postagem> getPostagem() { return postagem; }
    public void setPostagem(List<Postagem> postagem) { this.postagem = postagem; }
}
