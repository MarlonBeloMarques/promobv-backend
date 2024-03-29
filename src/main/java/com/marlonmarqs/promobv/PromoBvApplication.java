package com.marlonmarqs.promobv;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.marlonmarqs.promobv.service.S3Service;

@SpringBootApplication
public class PromoBvApplication implements CommandLineRunner {

	@Autowired
	private S3Service s3Service;	
	
	public static void main(String[] args) {
		SpringApplication.run(PromoBvApplication.class, args);
	}
	
	@Override
	public void run(String... args) throws Exception {
				
	}

}
