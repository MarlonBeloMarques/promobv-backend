package com.marlonmarqs.promobv.dto;

import java.io.Serializable;

import com.marlonmarqs.promobv.domain.GaleriaDeImagens;
import com.marlonmarqs.promobv.domain.Promocao;

public class PromocaoDTO implements Serializable{
	private static final long serialVersionUID = 1L;

	private Integer id;
	private String descricao;
	private Double preco;
	private String localizacao;
	private String titulo;
	
	private Integer idUsuario;
	private String nomeUsuario;
	private String emailUsuario;
	
	private GaleriaDeImagens galeriaDeImagens;

	public PromocaoDTO() {

	}
	
	public PromocaoDTO(Promocao obj) {
		id = obj.getId();
		descricao = obj.getDescricao();
		preco = obj.getPreco();
		localizacao = obj.getLocalizacao();
		titulo = obj.getTitulo();
		idUsuario = obj.getUsuario().getId();
		nomeUsuario = obj.getUsuario().getNome(); 
		emailUsuario = obj.getUsuario().getEmail();
		galeriaDeImagens = (obj.getGaleriaDeImagens() == null) ? null : obj.getGaleriaDeImagens();
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

	public String getNomeUsuario() {
		return nomeUsuario;
	}

	public void setNomeUsuario(String nomeUsuario) {
		this.nomeUsuario = nomeUsuario;
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

}