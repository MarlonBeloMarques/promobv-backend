package com.marlonmarqs.promobv.service;

import java.net.URI;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.marlonmarqs.promobv.domain.Usuario;
import com.marlonmarqs.promobv.domain.enums.TipoPerfil;
import com.marlonmarqs.promobv.dto.UsuarioNewDTO;
import com.marlonmarqs.promobv.dto.UsuarioUpdateDTO;
import com.marlonmarqs.promobv.repository.UsuarioRepository;
import com.marlonmarqs.promobv.security.UserSS;
import com.marlonmarqs.promobv.service.exceptions.AuthorizationException;
import com.marlonmarqs.promobv.service.exceptions.DataIntegrityException;
import com.marlonmarqs.promobv.service.exceptions.ObjectNotFoundException;

@Service
public class UsuarioService {

	@Autowired
	private UsuarioRepository repo;
	
	@Autowired
	private BCryptPasswordEncoder pe;
	
	@Autowired
	private S3Service s3Service;
	
	public Optional<Usuario> find(Integer id) {
		
		UserSS user = UserService.authenticated();
		// faz a busca e verifica se não é nulo ou se não é admin e se o id é diferente do qual foi buscado
		if(user==null || !user.hasRole(TipoPerfil.ADMIN) && !id.equals(user.getId())) {
			throw new AuthorizationException("Acesso negado");
		}

		
		Optional<Usuario> obj = repo.findById(id);
		
		obj.orElseThrow(() -> new ObjectNotFoundException("Objeto não encontrado! Id: " + id
				+ ", Tipo: " + Usuario.class.getName()));
		
		return obj;
	}
	
	public List<Usuario> findAll() {
		return repo.findAll();
	}
	
	public Usuario insert(Usuario obj) {
		obj.setId(null);
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
	
	public Usuario fromDTO(UsuarioUpdateDTO objDto) {
		return new Usuario(null, objDto.getNome(), objDto.getCpf(), objDto.getTelefone(), objDto.getDataDeNascimento());
	}
	
	public Usuario fromDTO(UsuarioNewDTO objDto) {
		return new Usuario (null, objDto.getApelido(), objDto.getEmail(), pe.encode(objDto.getSenha()));
	}
	
	private void updateData(Optional<Usuario> newObj, Usuario obj) {
		if(obj.getNome() != null)
			newObj.get().setNome(obj.getNome());
		if(obj.getCpf() != null)
			newObj.get().setCpf(obj.getCpf());
		if(obj.getDataDeNascimento() != null)
			newObj.get().setDataDeNascimento(obj.getDataDeNascimento());
		if(obj.getTelefone() != null)
			newObj.get().setTelefone(obj.getTelefone());
		if(obj.getDataDeNascimento() != null)
			newObj.get().setDataDeNascimento(obj.getDataDeNascimento());
	}
	
	public URI uploadProfilePicture(MultipartFile multipartFile) {
		
		UserSS user = UserService.authenticated();
		if(user == null) {
			throw new AuthorizationException("Acesso negado");
		}

		URI uri = s3Service.uploadFile(multipartFile);

		Optional<Usuario> usuario = repo.findById(user.getId()); // instancia um cliente
		usuario.get().setImageUrl(uri.toString());
		repo.save(usuario.get());

		return uri;
	}
}
