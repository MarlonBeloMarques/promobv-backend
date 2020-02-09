package com.marlonmarqs.promobv.resources;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.marlonmarqs.promobv.domain.Notificacao;
import com.marlonmarqs.promobv.service.NotificacaoService;

@RestController
@RequestMapping(value="/notificacoes")
public class NotificacaoResource {
	

	@Autowired
	private NotificacaoService service;
	
	@RequestMapping(value="/user/{id}", method=RequestMethod.GET) 
	public ResponseEntity<Page<Notificacao>> findAll(
			@PathVariable Integer id,
			@RequestParam(value="page", defaultValue="0")Integer page, // valor padrão 
			@RequestParam(value="linesPerPage", defaultValue="24")Integer linesPerPage, 
			@RequestParam(value="orderBy", defaultValue="data")String orderBy, 
			@RequestParam(value="direction", defaultValue="DESC")String direction) { 
		Page<Notificacao> list = service.findPage(id, page, linesPerPage, orderBy, direction);
		return ResponseEntity.ok().body(list); 
	}
	
}
