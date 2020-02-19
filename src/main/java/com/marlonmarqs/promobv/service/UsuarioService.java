package com.marlonmarqs.promobv.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.marlonmarqs.promobv.domain.Categoria;
import com.marlonmarqs.promobv.domain.Usuario;
import com.marlonmarqs.promobv.domain.enums.TipoPerfil;
import com.marlonmarqs.promobv.dto.UsuarioDTO;
import com.marlonmarqs.promobv.dto.UsuarioNewDTO;
import com.marlonmarqs.promobv.repository.UsuarioRepository;
import com.marlonmarqs.promobv.service.exceptions.DataIntegrityException;
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
	
	public Usuario insert(Usuario obj) throws DataIntegrityException {
		obj.setId(null);
		if(repo.findByEmail(obj.getEmail()) != null)
			throw new DataIntegrityException("Não é possível se cadastrar, pois já existe um usuário com esse email");	
		else
			return obj = repo.save(obj);
	}
	
	public void delete(Integer id) throws DataIntegrityException {
		Optional<Usuario> obj = find(id);

		if(obj.get().getPromocoes().isEmpty()) {
			repo.deleteById(id);
		} else {
			throw new DataIntegrityException("Não é possível excluir porque há entidades relacionadas");	
		}
	}
	
	public Usuario update(Usuario obj) {
		Optional<Usuario> newObj = find(obj.getId());
		updateData(newObj, obj);
		return repo.save(newObj.get());
	}
	
	public Usuario fromDTO(UsuarioDTO objDto) {
		return new Usuario(objDto.getId(), objDto.getNome(), objDto.getEmail(), null);
	}
	
	public Usuario fromDTO(UsuarioNewDTO objDto) {
		return new Usuario (null, objDto.getNome(), objDto.getApelido(), objDto.getDataDeNascimento(), objDto.getTelefone(), objDto.getEmail(), TipoPerfil.CLIENTE);
	}
	
	private void updateData(Optional<Usuario> newObj, Usuario obj) {
		newObj.get().setNome(obj.getNome());
		newObj.get().setEmail(obj.getEmail());
	}
}
