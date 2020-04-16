package com.marlonmarqs.promobv.dto;

import java.io.Serializable;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.marlonmarqs.promobv.domain.Notificacao;

public class NotificacaoDTO implements Serializable{
	private static final long serialVersionUID = 1L;

	private Integer id;
	
	@JsonFormat(pattern="dd/MM/yyyy")
	private Date data;
	@JsonFormat(pattern="HH:mm:ss")
	private Date hora;

	private Integer tipo;
	private String userApelido;
	private String userUrlProfile;
	private String promoTitulo;
	
	public NotificacaoDTO() {
		
	}
	
	public NotificacaoDTO(Notificacao obj) {
		id = obj.getId();
		data = obj.getData();
		hora = obj.getHora();
		tipo = (obj.getTipo() == null) ? null : obj.getTipo();
		userApelido = obj.getUsuario().getApelido();
		promoTitulo = obj.getPromocao().getTitulo();
		userUrlProfile = obj.getUsuario().getUrlProfile();
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

	public String getUserApelido() {
		return userApelido;
	}

	public void setUserApelido(String userApelido) {
		this.userApelido = userApelido;
	}

	public String getPromoTitulo() {
		return promoTitulo;
	}

	public void setPromoTitulo(String promoTitulo) {
		this.promoTitulo = promoTitulo;
	}

	public String getUserUrlProfile() {
		return userUrlProfile;
	}

	public void setUserUrlProfile(String userUrlProfile) {
		this.userUrlProfile = userUrlProfile;
	}

}
