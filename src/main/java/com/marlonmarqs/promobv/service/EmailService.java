package com.marlonmarqs.promobv.service;

import org.springframework.mail.SimpleMailMessage;

import com.marlonmarqs.promobv.domain.Usuario;

public interface EmailService {

	void sendEmail(SimpleMailMessage msg);
	
	void sendNewPasswordEmail(Usuario usuario, String newPass);
}
