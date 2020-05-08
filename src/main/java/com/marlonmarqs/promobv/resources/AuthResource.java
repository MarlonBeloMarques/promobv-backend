package com.marlonmarqs.promobv.resources;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.marlonmarqs.promobv.dto.NewPasswordDTO;
import com.marlonmarqs.promobv.security.JWTUtil;
import com.marlonmarqs.promobv.security.UserSS;
import com.marlonmarqs.promobv.service.AuthService;
import com.marlonmarqs.promobv.service.UserService;

@RestController
@RequestMapping(value = "/auth")
public class AuthResource {
	
	@Autowired
	private JWTUtil jwtUtil;
	
	@Autowired
	private AuthService service;
	
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
}
