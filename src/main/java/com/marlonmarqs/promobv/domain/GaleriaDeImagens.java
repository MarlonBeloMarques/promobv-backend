package com.marlonmarqs.promobv.domain;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CollectionTable;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.MapsId;
import javax.persistence.OneToOne;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
public class GaleriaDeImagens implements Serializable {
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy =GenerationType.IDENTITY)
	private Integer id;
		
	private Integer amount;

	/*Conjunto, não tem repetição nessa coleção*/
	@ElementCollection
	@CollectionTable(name="IMAGENS")
	private Set<String> urlImagens = new HashSet<>();
	
	@JsonIgnore
	@OneToOne // um para um
	@JoinColumn(name="promocao_id")
	@MapsId //para garantir que esse id seja igual da promocao
	private Promocao promocao;
	
	public GaleriaDeImagens() {
		amount = 0;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Set<String> getUrlImagens() {
		return urlImagens;
	}

	public void setUrlImagem(String urlImagem) {
		this.amount++;
		this.urlImagens.add(urlImagem);
	}

	public Promocao getPromocao() {
		return promocao;
	}

	public void setPromocao(Promocao promocao) {
		this.promocao = promocao;
	}

	public Integer getAmount() {
		return amount;
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
		GaleriaDeImagens other = (GaleriaDeImagens) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

}
