package com.example.womensTales.model;

import java.time.LocalDate;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;


import org.hibernate.annotations.UpdateTimestamp;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;



@Entity
@Table(name = "tb_postagens")
public class Postagem {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	
	@Size(min = 2, max= 255, message = "O ATRIBUTO TIPO DEVE TER NO MINIMO 2 E NO MAXIMO 255 CARACTERES!")
	private String tipo;
	
	@NotBlank(message = "O ATRIBUTO TÍTULO É OBRIGATÓRIO!")
	@Size(min = 2, max = 100, message = "O ATRIBUTO TÍTULO DEVE TER NO MINIMO 1 E NO MAXIMO 100 CARACTERES!")
	private String titulo;
	
	@NotBlank(message = "O ATRIBUTO TEXTO É OBRIGATÓRIO!")
	@Size(min = 2, message = "O ATRIBUTO TEXTO DEVE TER NO MINIMO 2 CARACTERES!")
	private String texto;
	
	@Size(min = 2, message = "O ATRIBUTO MIDIA DEVE TER NO MINIMO 2 CARACTERES!")
	private String midia;
	
	@UpdateTimestamp
	private LocalDate data;
	
	@ManyToOne
	@JsonIgnoreProperties("postagem")
	private Tema tema;
	
	@ManyToOne
	@JsonIgnoreProperties("postagem")
	private Usuario usuario;
	

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getTipo() {
		return tipo;
	}

	public void setTipo(String tipo) {
		this.tipo = tipo;
	}

	public String getTitulo() {
		return titulo;
	}

	public void setTitulo(String titulo) {
		this.titulo = titulo;
	}

	public String getTexto() {
		return texto;
	}

	public void setTexto(String texto) {
		this.texto = texto;
	}

	public String getMidia() {
		return midia;
	}

	public void setMidia(String midia) {
		this.midia = midia;
	}

	public LocalDate getData() {
		return data;
	}

	public void setData(LocalDate data) {
		this.data = data;
	}

	public Tema getTema() {
		return tema;
	}

	public void setTema(Tema tema) {
		this.tema = tema;
	}
	
	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

	

}
