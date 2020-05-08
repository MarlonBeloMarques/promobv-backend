package com.marlonmarqs.promobv.event;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

import com.marlonmarqs.promobv.domain.Usuario;
import com.marlonmarqs.promobv.service.EmailService;
import com.marlonmarqs.promobv.service.UsuarioService;

@Component
public class RegistrationListener implements ApplicationListener<OnRegistrationCompleteEvent> {
  
    @Autowired
    private UsuarioService service;
    
    @Autowired
	private EmailService emailService;
    
    @Override
    public void onApplicationEvent(OnRegistrationCompleteEvent event) {
        this.confirmRegistration(event);
    }
 
    private void confirmRegistration(OnRegistrationCompleteEvent event) {
        Usuario user = event.getUser();
        String token = UUID.randomUUID().toString();
        service.criaVerificacaoToken(user, token);
                 
        String confirmationUrl  = event.getAppUrl() + "/regitrationConfirm?token=" + token;
        
        emailService.sendRegistrationEmailHtml(user, confirmationUrl);
    }
}
