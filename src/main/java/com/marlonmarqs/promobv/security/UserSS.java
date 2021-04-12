package com.marlonmarqs.promobv.security;

import java.util.*;
import java.util.stream.Collectors;

import com.marlonmarqs.promobv.domain.Usuario;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.marlonmarqs.promobv.domain.enums.TipoPerfil;
import org.springframework.security.oauth2.core.user.OAuth2User;

public class UserSS implements UserDetails, OAuth2User {
	private static final long serialVersionUID = 1L;

	private Integer id;
	private String email;
	private String senha;
	private Boolean ativado;
	private Collection<? extends GrantedAuthority> authorities;
	private Map<String, Object> attributes;

	public UserSS() {

	}

	public UserSS(Integer id, String email, String senha, Boolean ativado, Set<TipoPerfil> perfis) {
		super();
		this.id = id;
		this.email = email;
		this.senha = senha;
		this.ativado = ativado;
		this.authorities = perfis.stream().map(x -> new SimpleGrantedAuthority(x.getDescricao())).collect(Collectors.toList());
	}

	public static UserSS create(Usuario usuario) {
		return new UserSS(usuario.getId(), usuario.getEmail(), usuario.getSenha(), usuario.getAtivado(), new HashSet<>());
	}

	public static UserSS create(Usuario usuario, Map<String, Object> attributes) {
		UserSS userSS = UserSS.create(usuario);
		userSS.setAttributes(attributes);
		return userSS;
	}

	public Integer getId() {
		return id;
	}
	
	public Boolean getAtivado() {
		return ativado;
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return authorities;
	}

	@Override
	public Map<String, Object> getAttributes() {
		return attributes;
	}

	public void setAttributes(Map<String, Object> attributes) {
		this.attributes = attributes;
	}

	@Override
	public String getPassword() {
		return senha;
	}

	@Override
	public String getUsername() {
		return email;
	}

	@Override
	public String getName() {
		return String.valueOf(id);
	}

	@Override
	public boolean isAccountNonExpired() {
		// colocamos por padrão true
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		// por apdrão não esta bloqueada
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		// por padrão não estão expiradas
		return true;
	}

	@Override
	public boolean isEnabled() {
		// por padrão ele esta ativo
		return true;
	}
	
	//testa se o usuairo possui o dado perfil
	public boolean hasRole(TipoPerfil perfil) {
		return getAuthorities().contains(new SimpleGrantedAuthority(perfil.getDescricao()));
	}

}
