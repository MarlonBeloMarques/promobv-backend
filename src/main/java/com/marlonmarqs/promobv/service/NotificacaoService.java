package com.marlonmarqs.promobv.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;

import com.marlonmarqs.promobv.domain.Notificacao;
import com.marlonmarqs.promobv.domain.Promocao;
import com.marlonmarqs.promobv.repository.NotificacaoRepository;

@Service
public class NotificacaoService {

	@Autowired
	private NotificacaoRepository repo;
	
	@Autowired
	private PromocaoService promocaoService;
	
	public Page<Notificacao> findPage(Integer idUser, Integer page, Integer linesPerPage, String orderBy, String direction){
		List<Notificacao> objs = new ArrayList<>();
		
		PageRequest pageRequest = PageRequest.of(page, linesPerPage, Direction.valueOf(direction), orderBy);
		
		List<Promocao> promocoes = promocaoService.findAllUser(idUser);
		for (Promocao promocao : promocoes) {
		 	objs.addAll(repo.findByPromocao(promocao));
		}
		
		return new PageImpl<>(objs, pageRequest, objs.size());
	}
}
