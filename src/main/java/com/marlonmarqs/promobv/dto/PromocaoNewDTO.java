package com.marlonmarqs.promobv.dto;

import java.io.Serializable;

import javax.validation.constraints.NotEmpty;

import com.marlonmarqs.promobv.domain.Promocao;

public class PromocaoNewDTO implements Serializable{
	private static final long serialVersionUID = 1L;

	private Integer id;
	@NotEmpty(message="Preenchimento obrigatório")
	private String descricao;
	@NotEmpty(message="Preenchimento obrigatório")
	private Double preco;
	@NotEmpty(message="Preenchimento obrigatório")
	private String localizacao;
	@NotEmpty(message="Preenchimento obrigatório")
	private String endereco;
	@NotEmpty(message="Preenchimento obrigatório")
	private String titulo;
	private String numeroContato;
	@NotEmpty(message="Preenchimento obrigatório")
	private Integer idCategoria;
	
	public PromocaoNewDTO() {

	}
	
	public PromocaoNewDTO(Promocao obj) {
		id = obj.getId();
		descricao = obj.getDescricao();
		preco = obj.getPreco();
		localizacao = obj.getLocalizacao();
		endereco = obj.getEndereco();
		titulo = obj.getTitulo();
		numeroContato = obj.getNumeroContato();
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

	public String getEndereco() {
		return endereco;
	}

	public void setEndereco(String endereco) {
		this.endereco = endereco;
	}

	public String getNumeroContato() {
		return numeroContato;
	}

	public void setNumeroContato(String numeroContato) {
		this.numeroContato = numeroContato;
	}

}
