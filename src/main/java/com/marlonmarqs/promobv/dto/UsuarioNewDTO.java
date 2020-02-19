package com.marlonmarqs.promobv.dto;

import java.io.Serializable;
import java.util.Date;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;

import org.hibernate.validator.constraints.Length;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.marlonmarqs.promobv.domain.Usuario;

public class UsuarioNewDTO implements Serializable {
	private static final long serialVersionUID = 1L;
	
	private Integer id;
	
	@NotEmpty(message="Preenchimento obrigatório")
	@Length(min=5, max=120, message="O tamanho deve ser entre 5 e 120 caracteres")
	private String nome;
	
	@NotEmpty(message="Preenchimento obrigatório")
	@Length(min=5, max=50, message="O tamanho deve ser entre 5 e 50 caracteres")
	private String apelido;
	
	@JsonFormat(pattern="dd/MM/yyyy")
	private Date dataDeNascimento;
	
	@NotEmpty(message="Preenchimento obrigatório")
	private String telefone;
	
	@NotEmpty(message="Preenchimento obrigatório")
	@Email(message="Email inválido")
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