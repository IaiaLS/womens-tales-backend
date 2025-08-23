package com.example.womensTales.model;

import java.util.List;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import io.swagger.v3.oas.annotations.media.Schema;

@Entity
@Table(name = "tb_usuario")
public class Usuario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "O atributo NOME é obrigatório!")
    @Size(min = 5, message = "O atributo NOME deve ter no mínimo 5 caracteres!")
    private String nome;

    @Size(min = 5, message = "O atributo APELIDO deve ter no mínimo 5 caracteres!")
    private String apelido;

    private String profissao;

    private String foto;

    @Schema(example = "email@email.com")
    @Email(message = "Informe um email válido")
    @NotBlank(message = "O atributo USUÁRIO é obrigatório!")
    private String usuario;

    @NotBlank(message = "O atributo SENHA é obrigatório!")
    @Size(min = 8, message = "O atributo SENHA deve ter no mínimo 8 caracteres!")
    private String senha;

    @OneToMany(mappedBy = "usuario", cascade = CascadeType.ALL)
    @JsonIgnoreProperties("usuario")
    private List<Postagem> postagem;

    // Getters e Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }

    public String getApelido() { return apelido; }
    public void setApelido(String apelido) { this.apelido = apelido; }

    public String getProfissao() { return profissao; }
    public void setProfissao(String profissao) { this.profissao = profissao; }

    public String getFoto() { return foto; }
    public void setFoto(String foto) { this.foto = foto; }

    public String getUsuario() { return usuario; }
    public void setUsuario(String usuario) { this.usuario = usuario; }

    public String getSenha() { return senha; }
    public void setSenha(String senha) { this.senha = senha; }

    public List<Postagem> getPostagem() { return postagem; }
    public void setPostagem(List<Postagem> postagem) { this.postagem = postagem; }
}
