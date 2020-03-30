package com.marlonmarqs.promobv.resources;

import java.net.URI;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.marlonmarqs.promobv.domain.Notificacao;
import com.marlonmarqs.promobv.dto.NotificacaoDTO;
import com.marlonmarqs.promobv.dto.NotificacaoNewDTO;
import com.marlonmarqs.promobv.service.NotificacaoService;

@RestController
@RequestMapping(value="/notificacoes")
public class NotificacaoResource {
	

	@Autowired
	private NotificacaoService service;
	
	@RequestMapping(method=RequestMethod.GET) 
	public ResponseEntity<Page<NotificacaoDTO>> findAll(
			@RequestParam(value="page", defaultValue="0")Integer page, // valor padrão 
			@RequestParam(value="linesPerPage", defaultValue="24")Integer linesPerPage, 
			@RequestParam(value="orderBy", defaultValue="data")String orderBy, 
			@RequestParam(value="direction", defaultValue="DESC")String direction) { 
		Page<NotificacaoDTO> list = service.findPage(page, linesPerPage, orderBy, direction);
		return ResponseEntity.ok().body(list); 
	}
	
	@RequestMapping(method=RequestMethod.POST) 
	public ResponseEntity<Void> insert(@Valid @RequestBody NotificacaoNewDTO objDto) {	
		Notificacao obj = service.fromDTO(objDto);
		obj = service.insert(obj);
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest()
				.path("/{id}").buildAndExpand(obj.getId()).toUri(); // enumerar de forma crescente o id do uri
		return ResponseEntity.created(uri).build(); 
	}
	
	@RequestMapping(value="/{id}", method=RequestMethod.DELETE)
	public ResponseEntity<Void> delete(@PathVariable Integer id) { 
		service.remove(id);
		return ResponseEntity.noContent().build();
	}
	
}
