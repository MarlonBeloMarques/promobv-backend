package com.marlonmarqs.promobv.security;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

public class JWTAuthorizationFilter extends BasicAuthenticationFilter {
	
	private JWTUtil jwtUtil;
	
	private UserDetailsService userDetailsService;
	
	public JWTAuthorizationFilter(AuthenticationManager authenticationManager, JWTUtil jwtUtil, UserDetailsService userDetailsService) {
		super(authenticationManager);
		this.jwtUtil = jwtUtil;
		this.userDetailsService = userDetailsService;
		// obs: precisa do detailsservice por que o filtro vai analisar o token e verificar se é valido, para isso precisa fazer a busca de usuario pelo email
		
	}
	
	//intecepta e ver se ta autorizado
	@Override
	protected void doFilterInternal(HttpServletRequest request,
									HttpServletResponse response,
									FilterChain chain) throws IOException, ServletException {
		
		String header = request.getHeader("Authorization"); // pega o cabeçalho
		
		//verifica se é valido
		if(header != null && header.startsWith("Bearer")) { // verifica se a estrutura do cabeçalho começa com bearer
			UsernamePasswordAuthenticationToken auth = getAuthentication(header.substring(6)); // vai converter para tipo security token pegando somente o codigo token
			if(auth != null) {
				SecurityContextHolder.getContext().setAuthentication(auth);
			}
		}
		chain.doFilter(request, response);
	}
	
	private UsernamePasswordAuthenticationToken getAuthentication(String token) {
		if(jwtUtil.tokenValido(token)) {
			String username = jwtUtil.getUsername(token);
			UserDetails user = userDetailsService.loadUserByUsername(username); // buscar e passar para user
			
			return new UsernamePasswordAuthenticationToken(user, null, user.getAuthorities());
		}
		
		return null;
	}

}
