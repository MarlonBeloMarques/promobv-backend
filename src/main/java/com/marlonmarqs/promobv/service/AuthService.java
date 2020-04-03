package com.marlonmarqs.promobv.service;

import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.marlonmarqs.promobv.domain.Usuario;
import com.marlonmarqs.promobv.repository.UsuarioRepository;
import com.marlonmarqs.promobv.service.exceptions.ObjectNotFoundException;

@Service
public class AuthService {

	@Autowired
	private UsuarioRepository repo;
	
	@Autowired
	private BCryptPasswordEncoder pe;
	
	@Autowired
	private EmailService emailService;
	
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
}
