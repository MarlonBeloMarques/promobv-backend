package com.marlonmarqs.promobv.dto;

import java.io.Serializable;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.marlonmarqs.promobv.domain.Usuario;

public class UsuarioNewDTO implements Serializable {
	private static final long serialVersionUID = 1L;
	
	private Integer id;
	private String nome;
	private String apelido;
	
	@JsonFormat(pattern="dd/MM/yyyy")
	private Date dataDeNascimento;
	private String telefone;
	private String email;
	
	private Integer tipo;
	
	
	public UsuarioNewDTO() {
		
	}
	
	public UsuarioNewDTO(Usuario obj) {
		id = obj.getId();
		nome = obj.getNome();
		apelido = obj.getApelido();
		dataDeNascimento = obj.getDataDeNascimento();
		telefone = obj.getTelefone();
		email = obj.getEmail();
		this.tipo = (obj.getTipo() == null) ? null : obj.getTipo();
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getApelido() {
		return apelido;
	}

	public void setApelido(String apelido) {
		this.apelido = apelido;
	}

	public Date getDataDeNascimento() {
		return dataDeNascimento;
	}

	public void setDataDeNascimento(Date dataDeNascimento) {
		this.dataDeNascimento = dataDeNascimento;
	}

	public String getTelefone() {
		return telefone;
	}

	public void setTelefone(String telefone) {
		this.telefone = telefone;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public Integer getTipo() {
		return tipo;
	}

	public void setTipo(Integer tipo) {
		this.tipo = tipo;
	}

	
}
