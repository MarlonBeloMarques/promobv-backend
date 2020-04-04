package com.marlonmarqs.promobv.service;

import javax.mail.internet.MimeMessage;

import org.springframework.mail.SimpleMailMessage;

import com.marlonmarqs.promobv.domain.Usuario;

public interface EmailService {

	void sendEmail(SimpleMailMessage msg);
	
	void sendNewPasswordEmail(Usuario usuario, String newPass);
	
	//HTML
	
	void sendNewPasswordEmailHtml(Usuario usuario, String newPass);

	void sendHtmlEmail(MimeMessage msg);
}
