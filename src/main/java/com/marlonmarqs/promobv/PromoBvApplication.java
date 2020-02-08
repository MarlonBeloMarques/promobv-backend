package com.marlonmarqs.promobv;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.marlonmarqs.promobv.domain.Categoria;
import com.marlonmarqs.promobv.domain.Promocao;
import com.marlonmarqs.promobv.domain.Usuario;
import com.marlonmarqs.promobv.repository.CategoriaRepository;
import com.marlonmarqs.promobv.repository.PromocaoRepository;
import com.marlonmarqs.promobv.repository.UsuarioRepository;

@SpringBootApplication
public class PromoBvApplication implements CommandLineRunner {

	@Autowired
	private CategoriaRepository categoriaRepository;
	
	@Autowired
	private PromocaoRepository promocaoRepository;
	
	@Autowired
	private UsuarioRepository usuarioRepository;
	
	public static void main(String[] args) {
		SpringApplication.run(PromoBvApplication.class, args);
	}
	
	@Override
	public void run(String... args) throws Exception {
		
		Categoria cat1 = new Categoria(null, "Auto e peças");
		Categoria cat2 = new Categoria(null, "Eletrônicos");
		
		Usuario user1 = new Usuario(1, "Marlon", "marlon.belohd@gmail.com");
		Usuario user2 = new Usuario(2, "Matheus", "matheus.belo@gmail.com");
		
		Promocao promo1 = new Promocao(null, "Aulas de espanhol com material didatico", 50.00, "Rua XXX", "Aulas de Espanhol", cat1, user1);
		
		usuarioRepository.saveAll(Arrays.asList(user1, user2));
		categoriaRepository.saveAll(Arrays.asList(cat1, cat2));
		promocaoRepository.save(promo1);
	}

}
