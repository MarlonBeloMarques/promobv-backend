package com.marlonmarqs.promobv.security;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.marlonmarqs.promobv.dto.CredenciaisDTO;

//filtro de autenticação extendendo especifico para login
public class JWTAuthenticationFilter extends UsernamePasswordAuthenticationFilter {

	//padrão framework, injetando pelo construtor
	private AuthenticationManager authenticationManager;

	private JWTUtil jwtUtil;

	public JWTAuthenticationFilter(AuthenticationManager authenticationManager, JWTUtil jwtUtil) {
		this.authenticationManager = authenticationManager;
		this.jwtUtil = jwtUtil;
	}

	// tenta autenticar
	@Override
	public Authentication attemptAuthentication(HttpServletRequest req,
												HttpServletResponse res) throws AuthenticationException{

		try {
			// vai tentar pegar o objeto de requisicao e converter para o tipo em seguida
			CredenciaisDTO creds = new ObjectMapper()
					.readValue(req.getInputStream(), CredenciaisDTO.class);

			//instanciar
			UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(creds.getEmail(), creds.getSenha(), new ArrayList<>());

			Authentication auth = authenticationManager.authenticate(authToken); // verificar se esses dados são validos
			return auth;
		}
		catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

	// se for um sucesso a autenticação
	@Override
	protected void successfulAuthentication(HttpServletRequest req,
											HttpServletResponse res,
											FilterChain chain,
											Authentication auth) throws IOException, ServletException{

		//gerar um token e acresentar na resposta da requisição
		                                //getprincipal retorna o usuario do spring security, fazendo um casting com o UserSS, pega o email e armazena em username
		String username = ((UserSS) auth.getPrincipal()).getUsername();
		String token = jwtUtil.generateToken(username); // pega o token
		res.addHeader("Authorization", "Bearer" + token); // repassa o token como cabeçalho da resposta
	}
} 
