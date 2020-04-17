package com.marlonmarqs.promobv.resources;

import java.net.URI;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.marlonmarqs.promobv.domain.Usuario;
import com.marlonmarqs.promobv.dto.UsuarioNewDTO;
import com.marlonmarqs.promobv.dto.UsuarioUpdateDTO;
import com.marlonmarqs.promobv.service.UsuarioService;

@RestController
@RequestMapping(value = "/usuarios")
public class UsuarioResource {

	@Autowired
	private UsuarioService service;
	
	@RequestMapping(value="/{id}", method=RequestMethod.GET)
	public ResponseEntity<Optional<Usuario>> find(@PathVariable Integer id) {
		Optional<Usuario> obj = service.find(id);
		return ResponseEntity.ok().body(obj);
	}
	
	@RequestMapping(value="/email", method=RequestMethod.GET)
	public ResponseEntity<Usuario> find(@RequestParam(value="value") String email){ // recebe um valor que é o email
		Usuario obj = service.findByEmail(email); // chama o serviço e retorna o obj
		return ResponseEntity.ok().body(obj); // na requisicao
	}
	
	@RequestMapping(method=RequestMethod.POST) 
	public ResponseEntity<Void> insert(@Valid @RequestBody UsuarioNewDTO objDto) {	
		Usuario obj = service.fromDTO(objDto);
		obj = service.insert(obj);
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest()
				.path("/{id}").buildAndExpand(obj.getId()).toUri(); // enumerar de forma crescente o id do uri
		return ResponseEntity.created(uri).build(); 
	}
	
	@RequestMapping(value="/{id}", method=RequestMethod.PUT)
	public ResponseEntity<Void> update(@Valid @RequestBody UsuarioUpdateDTO objDto, @PathVariable Integer id) {
		Usuario obj = service.fromDTO(objDto);
		obj.setId(id);
		obj = service.update(obj);
		return ResponseEntity.noContent().build();
	}
	
	@PreAuthorize("hasAnyRole('ADMIN')")
	@RequestMapping(value="/{id}", method=RequestMethod.DELETE)
	public ResponseEntity<Void> delete(@PathVariable Integer id) { 
		service.delete(id);
		return ResponseEntity.noContent().build();
	}
	
	@RequestMapping(value="/picture", method=RequestMethod.POST)
	public ResponseEntity<Void> uploadProfilePicture(@RequestParam(name="file") MultipartFile file){ // @RequestParam(name="file") = reconhcer que chegou uma requisição do http 
		URI uri = service.uploadProfilePicture(file);
		return ResponseEntity.created(uri).build();
	}
}
