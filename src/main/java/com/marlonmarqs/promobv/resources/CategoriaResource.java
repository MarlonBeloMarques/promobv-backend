package com.marlonmarqs.promobv.resources;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.marlonmarqs.promobv.domain.Categoria;
import com.marlonmarqs.promobv.service.CategoriaService;

@RestController
@RequestMapping(value="/categorias")
public class CategoriaResource {
	

	@Autowired
	private CategoriaService service;
	
	@RequestMapping(value="/{id}", method=RequestMethod.GET)
	public ResponseEntity<?> find(@PathVariable Integer id) {
		Optional<Categoria> obj = service.find(id);
		return ResponseEntity.ok().body(obj);
	}
	
}
