package com.marlonmarqs.promobv.service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.marlonmarqs.promobv.domain.Categoria;
import com.marlonmarqs.promobv.domain.GaleriaDeImagens;
import com.marlonmarqs.promobv.domain.Notificacao;
import com.marlonmarqs.promobv.domain.Promocao;
import com.marlonmarqs.promobv.domain.Usuario;
import com.marlonmarqs.promobv.domain.enums.TipoNotificacao;
import com.marlonmarqs.promobv.domain.enums.TipoPerfil;
import com.marlonmarqs.promobv.repository.CategoriaRepository;
import com.marlonmarqs.promobv.repository.GaleriaDeImagensRepository;
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
	private GaleriaDeImagensRepository glrRepository;
	
	@Autowired
	private BCryptPasswordEncoder pe;

	public void instantiateTestDatabase() throws ParseException {
		
		Categoria cat1 = new Categoria(null, "Auto e peças");
		Categoria cat2 = new Categoria(null, "Eletrônicos");
		Categoria cat3 = new Categoria(null, "Casa");
		Categoria cat4 = new Categoria(null, "Música");
		Categoria cat5 = new Categoria(null, "Esportes");
		Categoria cat6 = new Categoria(null, "Lazer");
		Categoria cat7 = new Categoria(null, "Artigos Infantis");
		Categoria cat8 = new Categoria(null, "Animais");
		Categoria cat9 = new Categoria(null, "Moda");
		Categoria cat10 = new Categoria(null, "Beleza");
		Categoria cat11 = new Categoria(null, "Comércios");
		Categoria cat12 = new Categoria(null, "Serviço de Limpeza");
		Categoria cat13 = new Categoria(null, "Serviço de Moda");
		Categoria cat14 = new Categoria(null, "Serviço de Saúde");
		Categoria cat15 = new Categoria(null, "Serviço Infantil");
		Categoria cat16 = new Categoria(null, "Serviço de Transporte");
		Categoria cat17 = new Categoria(null, "Serviço de Manutenções");
		Categoria cat18 = new Categoria(null, "Serviço de Comércios");
		
		Usuario user1 = new Usuario(1, "Marlon", "marlonmarqsbr@gmail.com", pe.encode("123"));
		user1.addPerfil(TipoPerfil.ADMIN);
		Usuario user2 = new Usuario(2, "Matheus", "matheus.belo@gmail.com", pe.encode("123"));
		Usuario user3 = new Usuario(3, "Larissa", "larissa.pantoja@gmail.com", pe.encode("123"));
		user1.addPerfil(TipoPerfil.ADMIN);
		
		SimpleDateFormat sdf1 = new SimpleDateFormat("dd/MM/yyyy");
		SimpleDateFormat sdf2 = new SimpleDateFormat("HH:mm:ss");
		
		Promocao promo1 = new Promocao(null, "Aulas de espanhol com material didatico", 50.00, "Rua XXX", "N 453", "Aulas de Espanhol", "95984041795", cat18, user1);
		Promocao promo2 = new Promocao(null, "Aulas de ingles com material didatico", 50.00, "Rua YYY", "N 453", "Aulas de Ingles", "95984041795", cat18, user1);
		Promocao promo3 = new Promocao(null, "Aulas de japones com material didatico", 60.00, "Rua ZZZ", "N 453", "Aulas de Japones", "95984041795", cat18, user2);
		Promocao promo4 = new Promocao(null, "Roupas esportivas", 60.00, "Rua ZZZ", "N 453", "Roupas", "95984041795", cat5, user2);
		Promocao promo5 = new Promocao(null, "Aulas de Música com violino e violão", 80.00, "Rua TV", "N 43", "Aulas de Música", "95984041795", cat18, user3);
		Promocao promo6 = new Promocao(null, "Cadeiras de couro para estudo.", 100.00, "Rua ZZZ", "N 34", "Cadeiras", "95984041795", cat6, user2);
		Promocao promo7 = new Promocao(null, "Diarista, faz comida, lava casa, rouppa.", 120.00, "Rua ZZZ", "N 43", "Diarista", "95984041795", cat18, user2);
		Promocao promo8 = new Promocao(null, "Taxista particular diario", 80.00, "Rua ZZZ", "N 453", "Taxista", "95984041795", cat16, user1);
		Promocao promo9 = new Promocao(null, "Cachorro pitbull filhote castrado", 150.00, "Rua ZZZ", "N 43", "Filhotes de Pitbull", "95984041795", cat8, user1);
		Promocao promo10 = new Promocao(null, "Roupas esportivas para correr e caminhar", 40.00, "Rua ZZZ", "N 45", "Roupas esportivas", "95984041795", cat5, user1);
		Promocao promo11 = new Promocao(null, "Serviços de manutenção a centrais de ar.", 200.00, "Rua TV T 10", "N 553", "Manutenção a centrais de ar", "95984041795", cat17, user1);
		Promocao promo12 = new Promocao(null, "Notebook dell com memória ram ddr3 8 GB, HD de 500 GB.", 500.00, "Rua Tim Maia", "N 431", "Notebook DELL", "95984041795", cat2, user2);
		Promocao promo13 = new Promocao(null, "Roupas Infantis de 8 a 10 anos de idade.", 60.00, "Rua ZZZ", "N 413", "Roupas Infantis", "95984041795", cat7, user1);
		Promocao promo14 = new Promocao(null, "Limpeza de Banheiros", 60.00, "Rua ZZZ", "N 453", "Limpeza de Banheiros", "95984041795", cat12, user1);
		
		GaleriaDeImagens glr1 = new GaleriaDeImagens();
		glr1.setPromocao(promo1);
		glr1.setUrlImagem("https://promobv-dev.s3-sa-east-1.amazonaws.com/promo1-1.jpg");
		
		GaleriaDeImagens glr2 = new GaleriaDeImagens();
		glr2.setPromocao(promo2);
		glr2.setUrlImagem("https://promobv-dev.s3-sa-east-1.amazonaws.com/promo2-1.jpg");
		
		GaleriaDeImagens glr3 = new GaleriaDeImagens();
		glr3.setPromocao(promo3);
		glr3.setUrlImagem("https://promobv-dev.s3-sa-east-1.amazonaws.com/promo3-1.jpg");
		
		GaleriaDeImagens glr4 = new GaleriaDeImagens();
		glr4.setPromocao(promo4);
		glr4.setUrlImagem("https://promobv-dev.s3-sa-east-1.amazonaws.com/promo4-1.jpg");
		
		GaleriaDeImagens glr5 = new GaleriaDeImagens();
		glr5.setPromocao(promo5);
		glr5.setUrlImagem("https://promobv-dev.s3-sa-east-1.amazonaws.com/promo5-1.jpg");
		
		GaleriaDeImagens glr6 = new GaleriaDeImagens();
		glr6.setPromocao(promo6);
		glr6.setUrlImagem("https://promobv-dev.s3-sa-east-1.amazonaws.com/promo6-1.jpg");
		
		GaleriaDeImagens glr7 = new GaleriaDeImagens();
		glr7.setPromocao(promo7);
		glr7.setUrlImagem("https://promobv-dev.s3-sa-east-1.amazonaws.com/promo7-1.jpg");
		
		GaleriaDeImagens glr8 = new GaleriaDeImagens();
		glr8.setPromocao(promo8);
		glr8.setUrlImagem("https://promobv-dev.s3-sa-east-1.amazonaws.com/promo8-1.jpg");
		
		GaleriaDeImagens glr9 = new GaleriaDeImagens();
		glr9.setPromocao(promo9);
		glr9.setUrlImagem("https://promobv-dev.s3-sa-east-1.amazonaws.com/promo9-1.jpg");
		
		GaleriaDeImagens glr10 = new GaleriaDeImagens();
		glr10.setPromocao(promo10);
		glr10.setUrlImagem("https://promobv-dev.s3-sa-east-1.amazonaws.com/promo10-1.jpg");
		
		GaleriaDeImagens glr11 = new GaleriaDeImagens();
		glr11.setPromocao(promo11);
		glr11.setUrlImagem("https://promobv-dev.s3-sa-east-1.amazonaws.com/promo11-1.jpg");
		
		GaleriaDeImagens glr12 = new GaleriaDeImagens();
		glr12.setPromocao(promo12);
		glr12.setUrlImagem("https://promobv-dev.s3-sa-east-1.amazonaws.com/promo12-1.jpg");
		
		GaleriaDeImagens glr13 = new GaleriaDeImagens();
		glr13.setPromocao(promo13);
		glr13.setUrlImagem("https://promobv-dev.s3-sa-east-1.amazonaws.com/promo13-1.jpg");
		
		GaleriaDeImagens glr14 = new GaleriaDeImagens();
		glr14.setPromocao(promo14);
		glr14.setUrlImagem("https://promobv-dev.s3-sa-east-1.amazonaws.com/promo14-1.jpg");
		
		
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

		
		promo1.setGaleriaDeImagens(glr1);
		promo2.setGaleriaDeImagens(glr2);
		promo3.setGaleriaDeImagens(glr3);
		promo4.setGaleriaDeImagens(glr4);
		promo5.setGaleriaDeImagens(glr5);
		promo6.setGaleriaDeImagens(glr6);
		promo7.setGaleriaDeImagens(glr7);
		promo8.setGaleriaDeImagens(glr8);
		promo9.setGaleriaDeImagens(glr9);
		promo10.setGaleriaDeImagens(glr10);
		promo11.setGaleriaDeImagens(glr11);
		promo12.setGaleriaDeImagens(glr12);
		promo13.setGaleriaDeImagens(glr13);
		promo14.setGaleriaDeImagens(glr14);
		
		promo1.setNotificacoes(Arrays.asList(not1));
		promo2.setNotificacoes(Arrays.asList(not2, not3, not4, not5, not6, not7, not8, not9, not10, not11, not12, not13));
		user1.setPromocoes(Arrays.asList(promo1, promo2, promo8, promo9, promo10, promo11, promo13, promo14));
		user1.setNotificacoes(Arrays.asList(not1, not2, not3, not4, not5, not6, not7, not8, not9, not10, not11, not12, not13));
		user2.setPromocoes(Arrays.asList(promo3, promo4, promo6, promo7, promo12));
		user3.setPromocoes(Arrays.asList(promo5));
		
		cat18.setPromocoes(Arrays.asList(promo1, promo2, promo3, promo5, promo7));
		cat5.setPromocoes(Arrays.asList(promo4, promo10));
		cat6.setPromocoes(Arrays.asList(promo6));
		cat16.setPromocoes(Arrays.asList(promo8));
		cat8.setPromocoes(Arrays.asList(promo9));
		cat17.setPromocoes(Arrays.asList(promo11));
		cat2.setPromocoes(Arrays.asList(promo12));
		cat7.setPromocoes(Arrays.asList(promo13));
		cat12.setPromocoes(Arrays.asList(promo14));
		
		usuarioRepository.saveAll(Arrays.asList(user1, user2, user3));
		
		categoriaRepository.saveAll(Arrays.asList(cat1, cat2, cat3, cat4, cat5, cat6, cat7, cat8, cat9, cat10, cat11, cat12, cat13, cat14, cat15, cat16, cat17, cat18));
		promocaoRepository.saveAll(Arrays.asList(promo1, promo2, promo3, promo4, promo5, promo6, promo7, promo8, promo9, promo10, promo11, promo12, promo13, promo14));
		glrRepository.saveAll(Arrays.asList(glr1, glr2, glr3, glr4, glr5, glr6, glr7, glr8, glr9, glr10, glr11, glr12, glr13, glr14));
		notificacaoRepository.saveAll(Arrays.asList(not1, not2, not3, not4, not5, not6, not7, not8, not9, not10, not11, not12, not13));
	}
}
