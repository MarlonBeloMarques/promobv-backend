package com.marlonmarqs.promobv.domain;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

import com.fasterxml.jackson.annotation.JsonManagedReference;

@Entity
public class Promocao implements Serializable {
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy =GenerationType.IDENTITY)
	private Integer id;
	private String descricao;
	private Double preco;
	private String localizacao;
	private String endereco;
	private String titulo;
	private boolean publicada;
	
	@ManyToOne
	@JoinColumn(name="usuario_id")
	private Usuario usuario;
	
	@ManyToOne
	@JoinColumn(name="categoria_id")
	private Categoria categoria;
	
	//@JsonIgnore
	@JsonManagedReference // anotação para os primeiros objetos instanciados
	@OneToMany(mappedBy="promocao", cascade={CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH, CascadeType.REMOVE})
	private List<Notificacao> notificacoes = new ArrayList<>();
	
	@OneToOne(cascade=CascadeType.ALL, mappedBy="promocao") // necessario, pois da erro de identidade transiente quando vai salvar uma promocao e a galeria
	private GaleriaDeImagens galeriaDeImagens;
	
	public Promocao() {
		setPublicada(true);
	}

	public Promocao(Integer id, String descricao, Double preco, String localizacao, String endereco, String titulo) {
		super();
		this.id = id;
		this.descricao = descricao;
		this.preco = preco;
		this.localizacao = localizacao;
		this.titulo = titulo;
		this.endereco = endereco;
		setPublicada(true);

	}
	
	public Promocao(Integer id, String descricao, Double preco, String localizacao, String endereco, String titulo, Categoria categoria, Usuario usuario, GaleriaDeImagens galeriaDeImagens, Notificacao notificacao) {
		super();
		this.id = id;
		this.descricao = descricao;
		this.preco = preco;
		this.localizacao = localizacao;
		this.endereco = endereco;
		this.titulo = titulo;
		this.categoria = categoria;
		this.usuario = usuario;
		this.galeriaDeImagens = galeriaDeImagens;
		this.notificacoes.add(notificacao);
		setPublicada(true);

	}
	
	public Promocao(Integer id, String descricao, Double preco, String localizacao, String endereco, String titulo, Categoria categoria, Usuario usuario) {
		super();
		this.id = id;
		this.descricao = descricao;
		this.preco = preco;
		this.localizacao = localizacao;
		this.titulo = titulo;
		this.categoria = categoria;
		this.usuario = usuario;
		this.endereco = endereco;
		setPublicada(true);

	}
	
	public Promocao(Integer id, String descricao, Double preco, String localizacao, String endereco, String titulo, Categoria categoria) {
		this.id = id;
		this.descricao = descricao;
		this.preco = preco;
		this.localizacao = localizacao;
		this.titulo = titulo;
		this.categoria = categoria;
		this.endereco = endereco;
		setPublicada(true);

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

	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

	public Categoria getCategoria() {
		return categoria;
	}

	public void setCategoria(Categoria categoria) {
		this.categoria = categoria;
	}

	public GaleriaDeImagens getGaleriaDeImagens() {
		return galeriaDeImagens;
	}

	public void setGaleriaDeImagens(GaleriaDeImagens galeriaDeImagens) {
		this.galeriaDeImagens = galeriaDeImagens;
	}

	public List<Notificacao> getNotificacoes() {	
		List<Notificacao> objs = new ArrayList<Notificacao>();
		for (Notificacao notificacao : notificacoes) {
			if(notificacao.getTipo() == 1) {
				objs.add(notificacao);
			}
		}
		
		return objs;
	}
	
	public List<Notificacao> getDenuncias() {
		List<Notificacao> objs = new ArrayList<Notificacao>();
		for (Notificacao notificacao : notificacoes) {
			if(notificacao.getTipo() == 2) {
				objs.add(notificacao);
			}
		}
		
		return objs;
	}

	public void setNotificacoes(List<Notificacao> notificacoes) {
		this.notificacoes = notificacoes;
	}

	public boolean isPublicada() {
		return publicada;
	}

	public void setPublicada(boolean publicada) {
		this.publicada = publicada;
	}

	public String getEndereco() {
		return endereco;
	}

	public void setEndereco(String endereco) {
		this.endereco = endereco;
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
		Promocao other = (Promocao) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}
	
}
