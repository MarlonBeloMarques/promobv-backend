package com.marlonmarqs.promobv.domain.enums;

public enum TipoNotificacao {

	CURTIDA(1, "Curtida"),
	DENUNCIA(2, "Denuncia");
	
	private Integer cod;
	private String descricao;
	
	private TipoNotificacao(Integer cod, String descricao) {
		this.cod = cod;
		this.descricao = descricao;
	}
	
	public Integer getCod() {
		return cod;
	}
	
	public String getDescricao() {
		return descricao;
	}
	
	public static TipoNotificacao toEnum(Integer cod) {
		
		if(cod == null) {
			return null;
		}
		
		for (TipoNotificacao x : TipoNotificacao.values()) {
			if(cod.equals(x.getCod())) {
				return x;
			}
		}
		
		throw new IllegalArgumentException("Id inválido: " + cod);
	}
}
