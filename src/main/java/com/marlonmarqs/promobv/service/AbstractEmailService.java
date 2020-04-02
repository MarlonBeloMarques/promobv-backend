package com.marlonmarqs.promobv.service;

import java.util.Date;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;

import com.marlonmarqs.promobv.domain.Usuario;

//classe abstrata que tem um contrato com a interface emailService

public abstract class AbstractEmailService implements EmailService {

	// o remetente
	@Value("${default.sender}")
	private String sender;
	
	@Override
	public void sendNewPasswordEmail(Usuario u, String newPass) {
		SimpleMailMessage sm = prepareNewPasswordEmail(u, newPass); // prepara o sistema para gerar nova senha
		sendEmail(sm); 
	}

	protected SimpleMailMessage prepareNewPasswordEmail(Usuario usuario, String newPass) {
		SimpleMailMessage sm = new SimpleMailMessage(); // cria e estancia um simplemailmessage
		sm.setTo(usuario.getEmail()); // destinatario
		sm.setFrom(sender); // remetente
		sm.setSubject("Solicitação de nova senha"); //subtitulo
		sm.setSentDate(new Date(System.currentTimeMillis())); // tempo;
		sm.setText("Nova senha: " + newPass); //texto
		return sm;//retorna um message
	}
}
