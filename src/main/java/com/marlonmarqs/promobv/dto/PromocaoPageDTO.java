package com.marlonmarqs.promobv.dto;

import java.io.Serializable;

import com.marlonmarqs.promobv.domain.GaleriaDeImagens;
import com.marlonmarqs.promobv.domain.Promocao;

public class PromocaoPageDTO implements Serializable{
	private static final long serialVersionUID = 1L;

	private Integer id;
	private Double preco;
	private String localizacao;
	private String titulo;
		
	private GaleriaDeImagens galeriaDeImagens;

	public PromocaoPageDTO() {

	}
	
	public PromocaoPageDTO(Promocao obj) {
		id = obj.getId();
		preco = obj.getPreco();
		localizacao = obj.getLocalizacao();
		titulo = obj.getTitulo();
		galeriaDeImagens = (obj.getGaleriaDeImagens() == null) ? null : obj.getGaleriaDeImagens();
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
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


	public GaleriaDeImagens getGaleriaDeImagens() {
		return galeriaDeImagens;
	}

	public void setGaleriaDeImagens(GaleriaDeImagens galeriaDeImagens) {
		this.galeriaDeImagens = galeriaDeImagens;
	}

}
