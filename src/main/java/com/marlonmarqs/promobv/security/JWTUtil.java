package com.marlonmarqs.promobv.security;

import java.util.Date;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@Component
public class JWTUtil {

	@Value("${jwt.secret}")
	private String secret;
	
	@Value("${jwt.expiration}")
	private Long expiration;
	
	//gerar token
	public String generateToken(String username) {
		return Jwts.builder()
				 .setSubject(username) //usuario
				 .setExpiration(new Date(System.currentTimeMillis() + expiration)) // expiração tempo
				 .signWith(SignatureAlgorithm.HS512, secret.getBytes()) // como vou assinar meu token, algoritmo e o secreto
				 .compact();
	}
	
	public boolean tokenValido(String token) {
		// claims = armazena as reinvidicações do token, ou seja usuario e tempo de expiração
		Claims claims = getClaims(token);
		if(claims != null) {
			String username = claims.getSubject(); // metodo para retornar o usuario
			Date expirationDate = claims.getExpiration();
			Date now = new Date(System.currentTimeMillis());
			if(username != null && expirationDate != null && now.before(expirationDate)) { // se o email for diferente de nulo e data de expiracao diferente de nulo e data atual for antes da expirada
				return true;
			}
		}
		return false;
	}
	
	public String getUsername(String token) {
		// claims = armazena as reinvidicações do token, ou seja usuario e tempo de expiração
		Claims claims = getClaims(token);
		if(claims != null) {
			return claims.getSubject(); // metodo para retornar o usuario
		}
		return null;
	}
	
	private Claims getClaims(String token) {
		try {
			return Jwts.parser().setSigningKey(secret.getBytes()).parseClaimsJws(token).getBody();//recupera os claims apartir do token
		}
		catch (Exception e) {
			return null;
		}
	}

}
