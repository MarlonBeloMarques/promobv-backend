// configrações especificas do profile de teste

package com.marlonmarqs.promobv.config;

import java.text.ParseException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import com.marlonmarqs.promobv.service.DBService;
import com.marlonmarqs.promobv.service.EmailService;
import com.marlonmarqs.promobv.service.MockEmailService;

@Configuration
@Profile("test")
public class TestConfig {

	@Autowired
	private DBService dbService;
	
	@Bean
	public boolean instantiateDatabase() throws ParseException {
		
		dbService.instantiateTestDatabase();
		return true;
	}
	
	@Bean // vai esta disponivel como componente no sistema
	//apenas notificado no system.print
	public EmailService emailService() {
		return new MockEmailService();
	}
}
