// configrações especificas do profile do docker

package com.marlonmarqs.promobv.config;

import com.marlonmarqs.promobv.service.DBService;
import com.marlonmarqs.promobv.service.EmailService;
import com.marlonmarqs.promobv.service.SmtpEmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import java.text.ParseException;

@Configuration
@Profile("docker")
public class DockerConfig {

	@Autowired
	private DBService dbService;
	
	@Value("${spring.jpa.hibernate.ddl-auto}")
	private String strategy;
	
	@Bean
	public boolean instantiateDatabase() throws ParseException {
		
		if(!"create".equals(strategy))
			return false;
		
		dbService.instantiateTestDatabase();
		return true;
	}
	
	@Bean
	//envio de email com smtp, obs: chega no email
	public EmailService emailService() {
		return new SmtpEmailService();
	}
}
