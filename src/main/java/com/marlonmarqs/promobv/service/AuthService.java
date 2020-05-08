package com.marlonmarqs.promobv.service;

import java.util.Calendar;
import java.util.Random;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.validation.Errors;

import com.marlonmarqs.promobv.domain.Usuario;
import com.marlonmarqs.promobv.domain.VerificaToken;
import com.marlonmarqs.promobv.event.OnUpdatePasswordCompleteEvent;
import com.marlonmarqs.promobv.repository.UsuarioRepository;
import com.marlonmarqs.promobv.service.exceptions.BusinessRuleException;
import com.marlonmarqs.promobv.service.exceptions.ObjectNotFoundException;

@Service
public class AuthService {

	@Autowired
	private UsuarioRepository repo;
	
	@Autowired
	private UsuarioService service;
	
	@Autowired
	private BCryptPasswordEncoder pe;
	
	@Autowired
	private EmailService emailService;
	
	@Autowired
	ApplicationEventPublisher eventPublisher;
	
	private Random rand = new Random();
	
	// verifica se o email existe
	public void sendNewPassword(String email) {
		
		//pega a pessoa com o email
		Usuario usuario = repo.findByEmail(email);
		if (usuario == null) {
			throw new ObjectNotFoundException("Email não encontrado");
		}

		// gerar nova senha
		String newPass = newPassword();
		usuario.setSenha(pe.encode(newPass));

		repo.save(usuario);
		//emailService.sendNewPasswordEmail(usuario, newPass);
		emailService.sendNewPasswordEmailHtml(usuario, newPass);
	}
	
	// gerar senha aleatoria
	private String newPassword() {

		char[] vet = new char[10];
		for (int i = 0; i < 10; i++) {
			vet[i] = randomChar();
		}
		return new String(vet);
	}
	
	private char randomChar() {

		int opt = rand.nextInt(3);
		if (opt == 0) { // gera um digito
			// ir no site unicode-table para ver o codigo do digito
			return (char) (rand.nextInt(10) + 48); // sorteia um numero de 0 a 9 e soma com o codigo 48 = codigo 0
		} else if (opt == 1) { // gera letra maiuscula
			return (char) (rand.nextInt(26) + 65);
		} else { // gera letra minuscula
			return (char) (rand.nextInt(26) + 97);
		}
	}
	
	public Boolean checkEmail(String email) {
		Usuario user = repo.findByEmail(email);
		
		if(user==null) {
			throw new ObjectNotFoundException("Email não encontrado");
		}
		
		return true;
	}
	
	public void sendNewPassword(String email, String newPassword, HttpServletRequest request, Errors errors) {
		
		Usuario usuario = repo.findByEmail(email);
		if(usuario == null) {
			throw new ObjectNotFoundException("Email não encontrado");
		}
		
		try {
			String newPass = pe.encode(newPassword);
			usuario.setSenhaSecundaria(newPass);
		
			repo.save(usuario);
			
			String baseUrl = request.getRequestURL().substring(0, request.getRequestURL().length() - request.getRequestURI().length()) + request.getContextPath();
			String appUrl = baseUrl + "/auth";
			eventPublisher.publishEvent(new OnUpdatePasswordCompleteEvent(usuario, request.getLocale(), appUrl));		
		} catch (Exception e) {
			System.out.println(e);
		}
	}
	
	public String updatePassword(String token) throws ObjectNotFoundException, BusinessRuleException {
		
		VerificaToken verificaToken = service.getVerificationToken(token);
		if(verificaToken == null) {
			throw new ObjectNotFoundException("Token não encontrado!");
		}
		
		Usuario user = verificaToken.getUser();
		Calendar cal = Calendar.getInstance();
		if((verificaToken.getDataDeValidade().getTime() - cal.getTime().getTime()) <= 0) {
			throw new BusinessRuleException("Token expirado!");
		}
		
		if(user.getSenhaSecundaria() == null) {
			throw new ObjectNotFoundException("Nova senha não encontrada!");
		}
		
		user.setSenha(user.getSenhaSecundaria());
		user.setSenhaSecundaria(null);
		repo.save(user);
		
		return "Sua senha foi atualizada com sucesso!";
	}
}
