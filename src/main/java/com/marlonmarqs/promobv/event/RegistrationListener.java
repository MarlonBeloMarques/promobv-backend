package com.marlonmarqs.promobv.event;

import java.util.Date;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationListener;
import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.stereotype.Component;

import com.marlonmarqs.promobv.domain.Usuario;
import com.marlonmarqs.promobv.service.SmtpEmailService;
import com.marlonmarqs.promobv.service.UsuarioService;

@Component
public class RegistrationListener implements ApplicationListener<OnRegistrationCompleteEvent> {
  
    @Autowired
    private UsuarioService service;
    
    @Autowired
    private MailSender mailSender;
    
    // o remetente
 	@Value("${default.sender}")
 	private String sender;
 	
	private static final Logger LOG = LoggerFactory.getLogger(SmtpEmailService.class); // Logger referente a essa classe
 
    @Override
    public void onApplicationEvent(OnRegistrationCompleteEvent event) {
        this.confirmRegistration(event);
    }
 
    private void confirmRegistration(OnRegistrationCompleteEvent event) {
        Usuario user = event.getUser();
        String token = UUID.randomUUID().toString();
        service.criaVerificacaoToken(user, token);
         
        String recipientAddress = user.getEmail();
        String subject = "Registration Confirmation";
        
        String confirmationUrl  = event.getAppUrl() + "/regitrationConfirm?token=" + token;
        
        SimpleMailMessage email = new SimpleMailMessage();
        email.setTo(recipientAddress);
        email.setFrom(sender);
        email.setSubject(subject);
		email.setSentDate(new Date(System.currentTimeMillis())); // tempo;
        email.setText("\n" + confirmationUrl);
        LOG.info("Enviando email...");
        mailSender.send(email);
        LOG.info("Email enviado");
    }
}
