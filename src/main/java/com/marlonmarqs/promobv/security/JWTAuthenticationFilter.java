package com.marlonmarqs.promobv.security;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.marlonmarqs.promobv.service.exceptions.AuthorizationException;
import org.apache.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.marlonmarqs.promobv.dto.CredenciaisDTO;
import com.marlonmarqs.promobv.service.exceptions.BusinessRuleException;

//filtro de autenticação extendendo especifico para login
public class JWTAuthenticationFilter extends UsernamePasswordAuthenticationFilter {

	//padrão framework, injetando pelo construtor
	private AuthenticationManager authenticationManager;

	private JWTUtil jwtUtil;

	public JWTAuthenticationFilter(AuthenticationManager authenticationManager, JWTUtil jwtUtil) {
		setAuthenticationFailureHandler(new JWTAuthenticationFailureHandler());
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
											Authentication auth) throws IOException, ServletException, BusinessRuleException{

		Boolean ativado = ((UserSS) auth.getPrincipal()).getAtivado();

		if(!ativado) {
			throw new AuthorizationException("Seu email não está ativado.");
		}
			
		//gerar um token e acresentar na resposta da requisição
		                                //getprincipal retorna o usuario do spring security, fazendo um casting com o UserSS, pega o email e armazena em username
		String username = ((UserSS) auth.getPrincipal()).getUsername();
		String token = jwtUtil.generateToken(username); // pega o token
		res.addHeader("Authorization", "Bearer" + token); // repassa o token como cabeçalho da resposta
		//liberar cabeçalho
		//res.addHeader("acess-control-expose-headers", "Authorization");
	}
	
	private class JWTAuthenticationFailureHandler implements AuthenticationFailureHandler {
		 
        @Override
        public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception)
                throws IOException, ServletException {
            response.setStatus(401);
            response.setContentType("application/json"); 
            response.getWriter().append(json());
        }
        
        private String json() {
            long date = new Date().getTime();
            return "{\"timestamp\": " + date + ", "
                + "\"status\": 401, "
                + "\"error\": \"Não autorizado\", "
                + "\"message\": \"Email ou senha inválidos\", "
                + "\"path\": \"/login\"}";
        }
    }
} 
