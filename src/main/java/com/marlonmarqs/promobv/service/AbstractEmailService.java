package com.marlonmarqs.promobv.service;

import java.util.Date;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import com.marlonmarqs.promobv.domain.Usuario;

//classe abstrata que tem um contrato com a interface emailService

public abstract class AbstractEmailService implements EmailService {

	// o remetente
	@Value("${default.sender}")
	private String sender;
	
	//html
	@Autowired
	private TemplateEngine templateEngine;

	@Autowired
	private JavaMailSender javaMailSender;
	
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
	
	//HTML
	protected String htmlFromTemplateNewSenha(String link) {
		Context context = new Context(); // acessar o timeleaf
		context.setVariable("link", link);
		return templateEngine.process("email/novaSenha", context); // pega o html
	}
	
	protected String htmlFromTemplateRegistration(String link) {
		Context context = new Context(); // acessar o timeleaf
		context.setVariable("link", link); // apelido e referencia obj nova senha
		return templateEngine.process("email/registration", context); // pega o html
	}

	@Override
	public void sendNewPasswordEmailHtml(Usuario u, String link) {
		try {
			//tenta enviar a nova senha
			MimeMessage mm = prepareMimelMessagenewPassword(u, link);
				sendHtmlEmail(mm);
			}
			catch(MessagingException e){
				//envia modo sem html
				//sendNewPasswordEmail(u, newPass);
			}
	}

	protected MimeMessage prepareMimelMessagenewPassword(Usuario usuario, String link) throws MessagingException {
		MimeMessage mimeMessage = javaMailSender.createMimeMessage(); //cria mensagem
		MimeMessageHelper mmh = new MimeMessageHelper(mimeMessage, true);//instancia
		mmh.setTo(usuario.getEmail()); // destinatario
		mmh.setFrom(sender); // remetente
		mmh.setSubject("Solicitação de nova senha"); // titulo
		mmh.setSentDate(new Date(System.currentTimeMillis())); //temppo
		mmh.setText(htmlFromTemplateNewSenha(link), true);// conteudo
		return mimeMessage;
	}
	
	protected MimeMessage prepareMimelMessagenRegistration(Usuario usuario, String link) throws MessagingException {
		MimeMessage mimeMessage = javaMailSender.createMimeMessage(); //cria mensagem
		MimeMessageHelper mmh = new MimeMessageHelper(mimeMessage, true);//instancia
		mmh.setTo(usuario.getEmail()); // destinatario
		mmh.setFrom(sender); // remetente
		mmh.setSubject("Concluir cadastro no PromoBV"); // titulo
		mmh.setSentDate(new Date(System.currentTimeMillis())); //temppo
		mmh.setText(htmlFromTemplateRegistration(link), true);// conteudo
		return mimeMessage;
	}
	
	@Override
	public void sendRegistrationEmailHtml(Usuario u, String link) {
		try {
			//tenta enviar a nova senha
			MimeMessage mm = prepareMimelMessagenRegistration(u, link);
				sendHtmlEmail(mm);
			}
		catch (Exception e) {
			// TODO: handle exception
		}
			
	}
		
}
