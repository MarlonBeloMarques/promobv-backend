package com.marlonmarqs.promobv.domain;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import javax.persistence.CollectionTable;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import com.fasterxml.jackson.annotation.JsonFormat;
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
	private String cpf;
	
	private String urlProfile;
	
	@JsonFormat(pattern = "dd/MM/yyyy")
	private Date dataDeNascimento;
	private String telefone;
		
	@Column(unique = true)
	private String email;
	
	@JsonIgnore
	private String senha;
		
	private Boolean emailValidado;
	
	@ElementCollection(fetch=FetchType.EAGER) //ao ser buscado o usuario, vem junto os perfis
	@CollectionTable(name="PERFIS")
	private Set<Integer> perfis = new HashSet<Integer>();
	
	@JsonIgnore
	@OneToMany(mappedBy="usuario")
	private List<Promocao> promocoes = new ArrayList<>();
	
	@JsonIgnore
	@OneToMany(mappedBy="usuario")
	private List<Notificacao> notificacoes = new ArrayList<>();
	
	public Usuario() {
		//todo perfil por padrão usuario
		addPerfil(TipoPerfil.CLIENTE);
		emailValidado = false;
	}

	public Usuario(Integer id, String nome, String apelido, Date dataDeNascimento, String telefone, String email, String senha, String cpf) {
		super();
		this.id = id;
		this.nome = nome;
		this.apelido = apelido;
		this.dataDeNascimento = dataDeNascimento;
		this.telefone = telefone;
		this.email = email;
		this.senha = senha;
		this.cpf = cpf;
		addPerfil(TipoPerfil.CLIENTE);
	}
	
	public Usuario(Integer id, String nome, String cpf, String telefone, Date dataDeNascimento) {
		super();
		this.id = id;
		this.nome = nome;
		this.cpf = cpf;
		this.telefone = telefone;
		this.dataDeNascimento = dataDeNascimento;
		addPerfil(TipoPerfil.CLIENTE);
	}
	
	public Usuario(Integer id, String nome, String apelido, Date dataDeNascimento, String telefone, String email, Promocao promocao, Notificacao notificacao) {
		super();
		this.id = id;
		this.nome = nome;
		this.apelido = apelido;
		this.dataDeNascimento = dataDeNascimento;
		this.telefone = telefone;
		this.email = email;
		this.promocoes.add(promocao);
		this.notificacoes.add(notificacao);
		addPerfil(TipoPerfil.CLIENTE);
	}
	
	public Usuario(Integer id, String apelido, String email, String senha) {
		super();
		this.id = id;
		this.apelido = apelido;
		this.email = email;
		this.senha = senha;
		addPerfil(TipoPerfil.CLIENTE);
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

	public Boolean getEmailValidado() {
		return emailValidado;
	}

	public void setEmailValidado(Boolean emailValidado) {
		this.emailValidado = emailValidado;
	}

	public String getSenha() {
		return senha;
	}

	public void setSenha(String senha) {
		this.senha = senha;
	}
	
	public Set<TipoPerfil> getPerfis(){ // retorna os perfis do cliente
		return perfis.stream().map(x -> TipoPerfil.toEnum(x)).collect(Collectors.toSet());
	}

	public void addPerfil(TipoPerfil perfil) {
		perfis.add(perfil.getCod());
	}

	public String getCpf() {
		return cpf;
	}

	public void setCpf(String cpf) {
		this.cpf = cpf;
	}

	public String getUrlProfile() {
		return urlProfile;
	}

	public void setUrlProfile(String urlProfile) {
		this.urlProfile = urlProfile;
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
