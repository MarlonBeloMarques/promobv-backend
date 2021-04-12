package com.marlonmarqs.promobv.resources;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import com.marlonmarqs.promobv.dto.CredenciaisDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import com.marlonmarqs.promobv.dto.NewPasswordDTO;
import com.marlonmarqs.promobv.security.JWTUtil;
import com.marlonmarqs.promobv.security.UserSS;
import com.marlonmarqs.promobv.service.AuthService;
import com.marlonmarqs.promobv.service.UserService;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;

@RestController
@RequestMapping(value = "/auth")
public class AuthResource {
	
	@Autowired
	private JWTUtil jwtUtil;
	
	@Autowired
	private AuthService service;

	@Autowired
	AuthenticationManager authenticationManager;

	@RequestMapping(value = "/login", method = RequestMethod.POST)
	public void authenticateUser(@RequestBody CredenciaisDTO creds, HttpServletResponse res)  throws IOException {

		try {
			UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(creds.getEmail(), creds.getSenha(), new ArrayList<>());

			Authentication auth = authenticationManager.authenticate(authToken); // verificar se esses dados são validos
			SecurityContextHolder.getContext().setAuthentication(auth);

			String username = ((UserSS) auth.getPrincipal()).getUsername();
			String token = jwtUtil.generateToken(username);

			res.addHeader("Authorization", "Bearer " + token); // repassa o token como cabeçalho da resposta
			//liberar cabeçalho
			res.addHeader("access-control-expose-headers", "Authorization");
		} catch (BadCredentialsException e) {
			res.setStatus(401);
			res.setContentType("application/json");
			res.getWriter().append(json());
		}
	}
	
	//tem que ta logado para acessar
	@RequestMapping(value = "/refresh_token", method = RequestMethod.POST)
	public ResponseEntity<Void> refreshToken(HttpServletResponse response) {
		UserSS user = UserService.authenticated();
		String token = jwtUtil.generateToken(user.getUsername());
		response.addHeader("Authorization", "Bearer" + token);
		
		return ResponseEntity.noContent().build();
	}
	
	@RequestMapping(value = "/new_password", method = RequestMethod.POST)
	public ResponseEntity<Void> newPassword(@Valid @RequestBody NewPasswordDTO objDto, HttpServletRequest request, Errors errors) {
		service.sendNewPassword(objDto.getEmail(), objDto.getPassword(), request, errors); //pega o email para gerar a nova sneha
		return ResponseEntity.noContent().build(); // retorna nada
	}
	
	@RequestMapping(value="/updatePassword", method=RequestMethod.GET)
	public ResponseEntity<String> updatePassword(@RequestParam(value="token") String token){ // recebe um valor que é o email
		String msg = service.updatePassword(token); // chama o serviço e retorna o obj
		return ResponseEntity.ok().body(msg); // na requisicao
	}
	
	@RequestMapping(value="/check_email", method=RequestMethod.GET)
	public ResponseEntity<Boolean> checkEmail(@RequestParam(value="value") String email){ // recebe um valor que é o email
		Boolean resp = service.checkEmail(email); // chama o serviço e retorna o obj
		return ResponseEntity.ok().body(resp); // na requisicao
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
