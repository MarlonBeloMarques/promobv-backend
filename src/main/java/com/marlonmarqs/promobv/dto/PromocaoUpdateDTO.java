package com.marlonmarqs.promobv.dto;

import java.io.Serializable;

import com.marlonmarqs.promobv.domain.Categoria;
import com.marlonmarqs.promobv.domain.GaleriaDeImagens;
import com.marlonmarqs.promobv.domain.Promocao;
import com.marlonmarqs.promobv.domain.Usuario;

public class PromocaoUpdateDTO implements Serializable{
	private static final long serialVersionUID = 1L;

	private Integer id;
	private String descricao;
	private Double preco;
	private String localizacao;
	private String titulo;
	
	private Integer idCategoria;
	
	public PromocaoUpdateDTO() {

	}
	
	public PromocaoUpdateDTO(Promocao obj) {
		id = obj.getId();
		descricao = obj.getDescricao();
		preco = obj.getPreco();
		localizacao = obj.getLocalizacao();
		titulo = obj.getTitulo();
		idCategoria = obj.getCategoria().getId();
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

	public Integer getIdCategoria() {
		return idCategoria;
	}

	public void setIdCategoria(Integer idCategoria) {
		this.idCategoria = idCategoria;
	}

}
