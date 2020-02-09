package com.marlonmarqs.promobv.domain;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import com.marlonmarqs.promobv.domain.enums.TipoNotificacao;

@Entity
public class Notificacao implements Serializable {
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Integer id;
	private Date data;
	private Date hora;
	
	private Integer tipo;
	
	@ManyToOne
	@JoinColumn(name="promocao_id")
	private Promocao promocao;
	
	@ManyToOne
	@JoinColumn(name="usuario_id")
	private Usuario usuario;
	
	private Notificacao() {
		
	}

	public Notificacao(Integer id, Date data, Date hora, TipoNotificacao tipo) {
		super();
		this.id = id;
		this.data = data;
		this.hora = hora;
		this.tipo = tipo.getCod();
	}
	
	public Notificacao(Integer id, Date data, Date hora, Promocao promocao, Usuario usuario, TipoNotificacao tipo) {
		super();
		this.id = id;
		this.data = data;
		this.hora = hora;
		this.promocao = promocao;
		this.usuario = usuario;
		this.tipo = tipo.getCod();
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Date getData() {
		return data;
	}

	public void setData(Date data) {
		this.data = data;
	}

	public Date getHora() {
		return hora;
	}

	public void setHora(Date hora) {
		this.hora = hora;
	}

	public Promocao getPromocao() {
		return promocao;
	}

	public void setPromocao(Promocao promocao) {
		this.promocao = promocao;
	}

	public Integer getTipo() {
		return tipo;
	}

	public void setTipo(Integer tipo) {
		this.tipo = tipo;
	}

	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
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
		Notificacao other = (Notificacao) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}
	
	
}
