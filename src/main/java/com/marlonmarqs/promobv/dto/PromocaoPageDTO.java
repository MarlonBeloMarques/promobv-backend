package com.marlonmarqs.promobv.dto;

import java.io.Serializable;
import java.util.Optional;

import com.marlonmarqs.promobv.domain.Promocao;

public class PromocaoPageDTO implements Serializable{
	private static final long serialVersionUID = 1L;

	private Integer id;
	private Double preco;
	private String localizacao;
	private String titulo;
	
	private Optional<String> imagem;
		
	public PromocaoPageDTO() {

	}
	
	public PromocaoPageDTO(Promocao obj) {
		id = obj.getId();
		preco = obj.getPreco();
		localizacao = obj.getLocalizacao();
		titulo = obj.getTitulo();
		imagem = obj.getGaleriaDeImagens() == null ? null : obj.getGaleriaDeImagens().getUrlImagens().stream().findFirst();
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

	public Optional<String> getImagem() {
		return imagem;
	}

	public void setImagem(Optional<String> imagem) {
		this.imagem = imagem;
	}

}
