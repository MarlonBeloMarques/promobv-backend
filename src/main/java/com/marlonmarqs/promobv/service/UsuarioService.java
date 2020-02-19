package com.marlonmarqs.promobv.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.marlonmarqs.promobv.domain.Usuario;
import com.marlonmarqs.promobv.dto.UsuarioDTO;
import com.marlonmarqs.promobv.repository.UsuarioRepository;
import com.marlonmarqs.promobv.service.exceptions.ObjectNotFoundException;

@Service
public class UsuarioService {

	@Autowired
	private UsuarioRepository repo;
	
	public Optional<Usuario> find(Integer id) {
		Optional<Usuario> obj = repo.findById(id);
		
		obj.orElseThrow(() -> new ObjectNotFoundException("Objeto não encontrado! Id: " + id
				+ ", Tipo: " + Usuario.class.getName()));
		
		return obj;
	}
	
	public List<Usuario> findAll() {
		return repo.findAll();
	}
	
	public Usuario update(Usuario obj) {
		Optional<Usuario> newObj = find(obj.getId());
		updateData(newObj, obj);
		return repo.save(newObj.get());
	}
	
	public Usuario fromDTO(UsuarioDTO objDto) {
		return new Usuario(objDto.getId(), objDto.getNome(), objDto.getEmail(), null);
	}
	
	private void updateData(Optional<Usuario> newObj, Usuario obj) {
		newObj.get().setNome(obj.getNome());
		newObj.get().setEmail(obj.getEmail());
	}
}
