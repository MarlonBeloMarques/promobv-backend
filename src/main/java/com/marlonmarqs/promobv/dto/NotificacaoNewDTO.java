package com.marlonmarqs.promobv.dto;

import java.io.Serializable;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.marlonmarqs.promobv.domain.Notificacao;

public class NotificacaoNewDTO implements Serializable{
	private static final long serialVersionUID = 1L;

	private Integer id;
	
	@JsonFormat(pattern="dd/MM/yyyy")
	private Date data;
	@JsonFormat(pattern="HH:mm:ss")
	private Date hora;

	private Integer tipo;
	
	private Integer idUsuario;
	private Integer idPromocao;
	
	public NotificacaoNewDTO() {
		
	}
	
	public NotificacaoNewDTO(Notificacao obj) {
		id = obj.getId();
		data = obj.getData();
		hora = obj.getHora();
		tipo = (obj.getTipo() == null) ? null : obj.getTipo();
		idUsuario = obj.getUsuario().getId();
		idPromocao = obj.getPromocao().getId();
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

	public Integer getTipo() {
		return tipo;
	}

	public void setTipo(Integer tipo) {
		this.tipo = tipo;
	}

	public Integer getIdUsuario() {
		return idUsuario;
	}

	public void setIdUsuario(Integer idUsuario) {
		this.idUsuario = idUsuario;
	}

	public Integer getIdPromocao() {
		return idPromocao;
	}

	public void setIdPromocao(Integer idPromocao) {
		this.idPromocao = idPromocao;
	}

}
