package com.marlonmarqs.promobv;

import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.marlonmarqs.promobv.domain.Categoria;
import com.marlonmarqs.promobv.domain.Notificacao;
import com.marlonmarqs.promobv.domain.Promocao;
import com.marlonmarqs.promobv.domain.Usuario;
import com.marlonmarqs.promobv.domain.enums.TipoPerfil;
import com.marlonmarqs.promobv.repository.CategoriaRepository;
import com.marlonmarqs.promobv.repository.NotificacaoRepository;
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
	
	@Autowired
	private NotificacaoRepository notificacaoRepository;
	
	public static void main(String[] args) {
		SpringApplication.run(PromoBvApplication.class, args);
	}
	
	@Override
	public void run(String... args) throws Exception {
		
		Categoria cat1 = new Categoria(null, "Auto e peças");
		Categoria cat2 = new Categoria(null, "Eletrônicos");
		
		Usuario user1 = new Usuario(1, "Marlon", "marlon.belohd@gmail.com", TipoPerfil.CLIENTE);
		Usuario user2 = new Usuario(2, "Matheus", "matheus.belo@gmail.com", TipoPerfil.CLIENTE);
		
		SimpleDateFormat sdf1 = new SimpleDateFormat("dd/MM/yyyy");
		SimpleDateFormat sdf2 = new SimpleDateFormat("HH:mm:ss");
		
		Notificacao not1 = new Notificacao(null, sdf1.parse("20/01/2020"), sdf2.parse("12:30:00"));
		Notificacao not2 = new Notificacao(null, sdf1.parse("20/01/2020"), sdf2.parse("14:30:00"));
		
		Promocao promo1 = new Promocao(null, "Aulas de espanhol com material didatico", 50.00, "Rua XXX", "Aulas de Espanhol", cat1, user1);
		Promocao promo2 = new Promocao(null, "Aulas de ingles com material didatico", 50.00, "Rua YYY", "Aulas de Ingles", cat1, user1);
		
		user1.setPromocoes(Arrays.asList(promo1, promo2));
		cat1.setPromocoes(Arrays.asList(promo1, promo2));
		
		usuarioRepository.saveAll(Arrays.asList(user1, user2));
		categoriaRepository.saveAll(Arrays.asList(cat1, cat2));
		notificacaoRepository.saveAll(Arrays.asList(not1, not2));
		promocaoRepository.saveAll(Arrays.asList(promo1, promo2));
	}

}
