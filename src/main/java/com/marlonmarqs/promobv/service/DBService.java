package com.marlonmarqs.promobv.service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.marlonmarqs.promobv.domain.Categoria;
import com.marlonmarqs.promobv.domain.Notificacao;
import com.marlonmarqs.promobv.domain.Promocao;
import com.marlonmarqs.promobv.domain.Usuario;
import com.marlonmarqs.promobv.domain.enums.TipoNotificacao;
import com.marlonmarqs.promobv.domain.enums.TipoPerfil;
import com.marlonmarqs.promobv.repository.CategoriaRepository;
import com.marlonmarqs.promobv.repository.NotificacaoRepository;
import com.marlonmarqs.promobv.repository.PromocaoRepository;
import com.marlonmarqs.promobv.repository.UsuarioRepository;

@Service
public class DBService {
	
	@Autowired
	private CategoriaRepository categoriaRepository;
	
	@Autowired
	private PromocaoRepository promocaoRepository;
	
	@Autowired
	private UsuarioRepository usuarioRepository;
	
	@Autowired
	private NotificacaoRepository notificacaoRepository;
	
	@Autowired
	private BCryptPasswordEncoder pe;

	public void instantiateTestDatabase() throws ParseException {
		
		Categoria cat1 = new Categoria(null, "Auto e peças");
		Categoria cat2 = new Categoria(null, "Eletrônicos");
		
		Usuario user1 = new Usuario(1, "Marlon", "marlon.belohd@gmail.com", pe.encode("123"));
		user1.addPerfil(TipoPerfil.ADMIN);
		Usuario user2 = new Usuario(2, "Matheus", "matheus.belo@gmail.com", pe.encode("123"));
		Usuario user3 = new Usuario(3, "Larissa", "larissa.pantoja@gmail.com", pe.encode("123"));
		user1.addPerfil(TipoPerfil.ADMIN);
		
		SimpleDateFormat sdf1 = new SimpleDateFormat("dd/MM/yyyy");
		SimpleDateFormat sdf2 = new SimpleDateFormat("HH:mm:ss");
		
		Promocao promo1 = new Promocao(null, "Aulas de espanhol com material didatico", 50.00, "Rua XXX", "N 453", "Aulas de Espanhol", cat1, user1);
		Promocao promo2 = new Promocao(null, "Aulas de ingles com material didatico", 50.00, "Rua YYY", "N 453", "Aulas de Ingles", cat1, user1);
		Promocao promo3 = new Promocao(null, "Aulas de japones com material didatico", 60.00, "Rua ZZZ", "N 453", "Aulas de Japones", cat1, user2);
		Promocao promo4 = new Promocao(null, "Roupas esportivas", 60.00, "Rua ZZZ", "N 453", "Roupas", cat2, user2);
		
		Notificacao not1 = new Notificacao(null, sdf1.parse("20/01/2020"), sdf2.parse("12:30:00"), promo1, user1, TipoNotificacao.CURTIDA);
		Notificacao not2 = new Notificacao(null, sdf1.parse("20/01/2020"), sdf2.parse("14:30:00"), promo2, user1,  TipoNotificacao.CURTIDA);
		
		Notificacao not3 = new Notificacao(null, sdf1.parse("20/01/2020"), sdf2.parse("14:30:00"), promo2, user1,  TipoNotificacao.DENUNCIA);
		Notificacao not4 = new Notificacao(null, sdf1.parse("20/01/2020"), sdf2.parse("14:30:00"), promo2, user1,  TipoNotificacao.DENUNCIA);
		Notificacao not5 = new Notificacao(null, sdf1.parse("20/01/2020"), sdf2.parse("14:30:00"), promo2, user1,  TipoNotificacao.DENUNCIA);
		Notificacao not6 = new Notificacao(null, sdf1.parse("20/01/2020"), sdf2.parse("14:30:00"), promo2, user1,  TipoNotificacao.DENUNCIA);
		Notificacao not7 = new Notificacao(null, sdf1.parse("20/01/2020"), sdf2.parse("14:30:00"), promo2, user1,  TipoNotificacao.DENUNCIA);
		Notificacao not8 = new Notificacao(null, sdf1.parse("20/01/2020"), sdf2.parse("14:30:00"), promo2, user1,  TipoNotificacao.DENUNCIA);
		Notificacao not9 = new Notificacao(null, sdf1.parse("20/01/2020"), sdf2.parse("14:30:00"), promo2, user1,  TipoNotificacao.DENUNCIA);
		Notificacao not10 = new Notificacao(null, sdf1.parse("20/01/2020"), sdf2.parse("14:30:00"), promo2, user1,  TipoNotificacao.DENUNCIA);
		Notificacao not11 = new Notificacao(null, sdf1.parse("20/01/2020"), sdf2.parse("14:30:00"), promo2, user1,  TipoNotificacao.DENUNCIA);
		Notificacao not12 = new Notificacao(null, sdf1.parse("20/01/2020"), sdf2.parse("14:30:00"), promo2, user1,  TipoNotificacao.DENUNCIA);
		Notificacao not13 = new Notificacao(null, sdf1.parse("20/01/2020"), sdf2.parse("14:30:00"), promo2, user1,  TipoNotificacao.DENUNCIA);

		
		promo1.setNotificacoes(Arrays.asList(not1));
		promo2.setNotificacoes(Arrays.asList(not2, not3, not4, not5, not6, not7, not8, not9, not10, not11, not12, not13));
		user1.setPromocoes(Arrays.asList(promo1, promo2));
		user1.setNotificacoes(Arrays.asList(not1, not2, not3, not4, not5, not6, not7, not8, not9, not10, not11, not12, not13));
		user2.setPromocoes(Arrays.asList(promo3, promo4));
		cat1.setPromocoes(Arrays.asList(promo1, promo2, promo3));
		cat2.setPromocoes(Arrays.asList(promo4));
		
		usuarioRepository.saveAll(Arrays.asList(user1, user2, user3));
		categoriaRepository.saveAll(Arrays.asList(cat1, cat2));
		promocaoRepository.saveAll(Arrays.asList(promo1, promo2, promo3, promo4));
		notificacaoRepository.saveAll(Arrays.asList(not1, not2, not3, not4, not5, not6, not7, not8, not9, not10, not11, not12, not13));
	}
}
