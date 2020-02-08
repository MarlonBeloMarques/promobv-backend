package com.marlonmarqs.promobv;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.marlonmarqs.promobv.domain.Categoria;
import com.marlonmarqs.promobv.repository.CategoriaRepository;

@SpringBootApplication
public class PromoBvApplication implements CommandLineRunner {

	@Autowired
	private CategoriaRepository categoriaRepository;
	
	public static void main(String[] args) {
		SpringApplication.run(PromoBvApplication.class, args);
	}
	
	@Override
	public void run(String... args) throws Exception {
		
		Categoria cat1 = new Categoria(null, "Auto e peças");
		Categoria cat2 = new Categoria(null, "Eletrônicos");
		
		categoriaRepository.saveAll(Arrays.asList(cat1, cat2));
	}

}
