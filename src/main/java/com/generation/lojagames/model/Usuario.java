package com.generation.lojagames.model;

import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Entity
@Table(name = "tb_usuario")
public class Usuario {
	
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	
	@NotNull(message = "O atributo NOME é OBRIGATÓRIO")
	private String nome;
	
	@NotNull(message = "O atributo EMAIL é OBRIGATÓRIO")
	@Email(message = "O atributo NOME deve ser um EMAIL VÁLIDO")
	private String usuario;
	
	
	@NotNull(message = "O atributo SENHA é OBRIGATÓRIO")
	@Size(min = 8, message = "O atributo SENHA deve ter no MÍNIMO 8 caractéres")
	private String senha;
	
	
	@NotNull(message = "O atributo FOTO é OBRIGATÓRIO")
	private String foto;
	
	@NotBlank(message = "O atributo DATA DE NASCIMENTO é OBRIGATÓRIO")
	@Column(name = "data_nascimento")
	private LocalDate dataNascimento;

	public Long getId() {
		return id;
	}


	public void setId(Long id) {
		this.id = id;
	}


	public String getNome() {
		return nome;
	}


	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getUsuario() {
		return usuario;
	}


	public void setUsuario(String usuario) {
		this.usuario = usuario;
	}


	public String getSenha() {
		return senha;
	}


	public void setSenha(String senha) {
		this.senha = senha;
	}


	public String getFoto() {
		return foto;
	}


	public void setFoto(String foto) {
		this.foto = foto;
	}


	public LocalDate getDataNascimento() {
		return dataNascimento;
	}


	public void setDataNascimento(LocalDate dataNascimento) {
		this.dataNascimento = dataNascimento;
	}
	
	
	
}
