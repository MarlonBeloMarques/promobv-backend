package com.marlonmarqs.promobv.dto;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import com.marlonmarqs.promobv.domain.GaleriaDeImagens;
import com.marlonmarqs.promobv.domain.Notificacao;
import com.marlonmarqs.promobv.domain.Promocao;

public class PromocaoDTO implements Serializable{
	private static final long serialVersionUID = 1L;

	private Integer id;
	private String descricao;
	private Double preco;
	private String localizacao;
	private String endereco;
	private String titulo;
	
	private Integer idUsuario;
	private String apelidoUsuario;
	private String userUrlProfile;
	private String emailUsuario;
	
	private Optional<String> imagem;
	
	private GaleriaDeImagens galeriaDeImagens;
	
	private List<Notificacao> notificacoes = new ArrayList<>();

	public PromocaoDTO() {

	}
	
	public PromocaoDTO(Promocao obj) {
		id = obj.getId();
		descricao = obj.getDescricao();
		preco = obj.getPreco();
		localizacao = obj.getLocalizacao();
		endereco = obj.getEndereco();
		titulo = obj.getTitulo();
		idUsuario = obj.getUsuario().getId();
		apelidoUsuario = obj.getUsuario().getApelido(); 
		emailUsuario = obj.getUsuario().getEmail();
		userUrlProfile = obj.getUsuario().getUrlProfile();
		imagem = obj.getGaleriaDeImagens() == null? null : obj.getGaleriaDeImagens().getUrlImagens().stream().findFirst();
		galeriaDeImagens = (obj.getGaleriaDeImagens() == null) ? null : obj.getGaleriaDeImagens();
		notificacoes = obj.getNotificacoes();
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public Double getPreco() {
		return preco;
	}

	public void setPreco(Double preco) {
		this.preco = preco;
	}

	public String getLocalizacao() {
		return localizacao;
	}

	public void setLocalizacao(String localizacao) {
		this.localizacao = localizacao;
	}

	public String getTitulo() {
		return titulo;
	}

	public void setTitulo(String titulo) {
		this.titulo = titulo;
	}

	public Integer getIdUsuario() {
		return idUsuario;
	}

	public void setIdUsuario(Integer idUsuario) {
		this.idUsuario = idUsuario;
	}

	public String getApelidoUsuario() {
		return apelidoUsuario;
	}

	public void setApelidoUsuario(String apelidoUsuario) {
		this.apelidoUsuario = apelidoUsuario;
	}

	public String getEmailUsuario() {
		return emailUsuario;
	}

	public void setEmailUsuario(String emailUsuario) {
		this.emailUsuario = emailUsuario;
	}

	public GaleriaDeImagens getGaleriaDeImagens() {
		return galeriaDeImagens;
	}

	public void setGaleriaDeImagens(GaleriaDeImagens galeriaDeImagens) {
		this.galeriaDeImagens = galeriaDeImagens;
	}

	public String getEndereco() {
		return endereco;
	}

	public void setEndereco(String endereco) {
		this.endereco = endereco;
	}

	public List<Notificacao> getNotificacoes() {
		return notificacoes;
	}

	public void setNotificacoes(List<Notificacao> notificacoes) {
		this.notificacoes = notificacoes;
	}

	public String getUserUrlProfile() {
		return userUrlProfile;
	}

	public void setUserUrlProfile(String userUrlProfile) {
		this.userUrlProfile = userUrlProfile;
	}

	public Optional<String> getImagem() {
		return imagem;
	}

	public void setImagem(Optional<String> imagem) {
		this.imagem = imagem;
	}

}
