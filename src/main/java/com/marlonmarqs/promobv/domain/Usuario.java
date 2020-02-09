package com.marlonmarqs.promobv.domain;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.marlonmarqs.promobv.domain.enums.TipoPerfil;

@Entity
public class Usuario implements Serializable {
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Integer id;
	private String nome;
	private String apelido;
	private Date dataDeNascimento;
	private String telefone;
	private String email;
	
	private Integer tipo;
	
	@JsonIgnore
	@OneToMany(mappedBy="usuario")
	private List<Promocao> promocoes = new ArrayList<>();
	
	@JsonIgnore
	@OneToMany(mappedBy="usuario")
	private List<Notificacao> notificacoes = new ArrayList<>();
	
	public Usuario() {
	}

	public Usuario(Integer id, String nome, String apelido, Date dataDeNascimento, String telefone, String email, TipoPerfil tipo) {
		super();
		this.id = id;
		this.nome = nome;
		this.apelido = apelido;
		this.dataDeNascimento = dataDeNascimento;
		this.telefone = telefone;
		this.email = email;
		this.tipo = tipo.getCod();
	}
	
	public Usuario(Integer id, String nome, String apelido, Date dataDeNascimento, String telefone, String email, Promocao promocao, Notificacao notificacao, TipoPerfil tipo) {
		super();
		this.id = id;
		this.nome = nome;
		this.apelido = apelido;
		this.dataDeNascimento = dataDeNascimento;
		this.telefone = telefone;
		this.email = email;
		this.promocoes.add(promocao);
		this.notificacoes.add(notificacao);
		this.tipo = tipo.getCod();
	}
	
	public Usuario(Integer id, String nome, String email, TipoPerfil tipo) {
		super();
		this.id = id;
		this.nome = nome;
		this.email = email;
		this.tipo = tipo.getCod();
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

	public List<Promocao> getPromocoes() {
		return promocoes;
	}

	public void setPromocoes(List<Promocao> promocoes) {
		this.promocoes = promocoes;
	}

	public List<Notificacao> getNotificacoes() {
		return notificacoes;
	}

	public void setNotificacoes(List<Notificacao> notificacoes) {
		this.notificacoes = notificacoes;
	}

	public Integer getTipo() {
		return tipo;
	}

	public void setTipo(Integer tipo) {
		this.tipo = tipo;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Usuario other = (Usuario) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}
	
	
}
