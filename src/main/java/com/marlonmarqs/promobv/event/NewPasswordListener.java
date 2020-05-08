package com.marlonmarqs.promobv.event;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

import com.marlonmarqs.promobv.domain.Usuario;
import com.marlonmarqs.promobv.service.EmailService;
import com.marlonmarqs.promobv.service.UsuarioService;

@Component
public class NewPasswordListener implements ApplicationListener<OnUpdatePasswordCompleteEvent> {
	
	@Autowired
	private UsuarioService service;
	
	 @Autowired
	private EmailService emailService;
	  
	@Override
	public void onApplicationEvent(OnUpdatePasswordCompleteEvent event) {
		this.confirmNewPassword(event);
	}
	
	private void confirmNewPassword(OnUpdatePasswordCompleteEvent event) {
		Usuario user = event.getUser();
		String token = UUID.randomUUID().toString();
		service.criaVerificacaoToken(user, token);
			
		String confirmationUrl = event.getAppUrl() + "/updatePassword?token=" + token;
		
		emailService.sendNewPasswordEmailHtml(user, confirmationUrl);
	}
}
