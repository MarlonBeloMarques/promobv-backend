package com.marlonmarqs.promobv.resources;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.marlonmarqs.promobv.domain.Notificacao;
import com.marlonmarqs.promobv.domain.Promocao;
import com.marlonmarqs.promobv.service.PromocaoService;

@RestController
@RequestMapping(value = "/promocoes")
public class PromocaoResource {

	@Autowired
	private PromocaoService service;
	
	@RequestMapping(value="/{id}", method=RequestMethod.GET)
	public ResponseEntity<?> find(@PathVariable Integer id) {
		Optional<Promocao> obj = service.find(id);
		return ResponseEntity.ok().body(obj);
	}
	
	@RequestMapping(method=RequestMethod.GET)
	public ResponseEntity<?> findAll() {
		List<Promocao> objs = service.findAll();
		return ResponseEntity.ok().body(objs);
	}
	
	@RequestMapping(value="/user/{id}", method=RequestMethod.GET)
	public ResponseEntity<?> findAllUser(@PathVariable Integer id) {
		List<Promocao> obj = service.findAllUser(id);
		return ResponseEntity.ok().body(obj);
	}
	
	@RequestMapping(value="/categoria/{id}", method=RequestMethod.GET) 
	public ResponseEntity<Page<Promocao>> findAll(
			@PathVariable Integer id,
			@RequestParam(value="page", defaultValue="0")Integer page, // valor padrão 
			@RequestParam(value="linesPerPage", defaultValue="24")Integer linesPerPage, 
			@RequestParam(value="orderBy", defaultValue="id")String orderBy, 
			@RequestParam(value="direction", defaultValue="DESC")String direction) { 
		Page<Promocao> list = service.findPage(id, page, linesPerPage, orderBy, direction);
		return ResponseEntity.ok().body(list); 
	}
}
