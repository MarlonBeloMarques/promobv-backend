package com.marlonmarqs.promobv.dto;

import java.io.Serializable;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.marlonmarqs.promobv.domain.Usuario;
import com.marlonmarqs.promobv.service.validation.UsuarioUpdate;

@UsuarioUpdate
public class UsuarioUpdateDTO implements Serializable {
	private static final long serialVersionUID = 1L;
	
	private Integer id;	
	private String nome;
	private String cpf;
	@JsonFormat(pattern = "dd/MM/yyyy")
	private Date dataDeNascimento;	
	private String telefone;
	private String email;
	
	public UsuarioUpdateDTO() {
		
	}
	
	public UsuarioUpdateDTO(Usuario obj) {
		id = obj.getId();
		nome = obj.getNome();
		cpf = obj.getCpf();
		dataDeNascimento = obj.getDataDeNascimento();
		telefone = obj.getTelefone();
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


	public String getTelefone() {
		return telefone;
	}

	public void setTelefone(String telefone) {
		this.telefone = telefone;
	}

	public String getCpf() {
		return cpf;
	}

	public void setCpf(String cpf) {
		this.cpf = cpf;
	}

	public Date getDataDeNascimento() {
		return dataDeNascimento;
	}

	public void setDataDeNascimento(Date dataDeNascimento) {
		this.dataDeNascimento = dataDeNascimento;
	}

	public String getEmail() {
		return email;
	}

}
