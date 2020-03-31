package com.marlonmarqs.promobv.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;

import com.marlonmarqs.promobv.domain.Categoria;
import com.marlonmarqs.promobv.domain.Promocao;
import com.marlonmarqs.promobv.domain.Usuario;
import com.marlonmarqs.promobv.dto.PromocaoNewDTO;
import com.marlonmarqs.promobv.dto.PromocaoUpdateDTO;
import com.marlonmarqs.promobv.repository.CategoriaRepository;
import com.marlonmarqs.promobv.repository.PromocaoRepository;
import com.marlonmarqs.promobv.repository.UsuarioRepository;
import com.marlonmarqs.promobv.security.UserSS;
import com.marlonmarqs.promobv.service.exceptions.AuthorizationException;
import com.marlonmarqs.promobv.service.exceptions.ObjectNotFoundException;

@Service
public class PromocaoService {

	@Autowired
	private PromocaoRepository repo;

	@Autowired
	private UsuarioRepository userRepository;

	@Autowired
	private CategoriaRepository catRepository;

	@Autowired
	private UsuarioService userService;

	@Autowired
	private CategoriaService categoriaService;

	public Optional<Promocao> find(Integer id) {
		Optional<Promocao> obj = repo.findById(id);

		obj.orElseThrow(() -> new ObjectNotFoundException(
				"Objeto não encontrado! Id: " + id + ", Tipo: " + Promocao.class.getName()));

		return obj;
	}

	public Promocao insert(Promocao obj) {
		obj.setId(null);
		obj = repo.save(obj);
		catRepository.save(obj.getCategoria());
		userRepository.save(obj.getUsuario());
		return obj;
	}

	public Promocao update(Promocao obj) {
		Optional<Promocao> newObj = find(obj.getId());
		updateData(newObj, obj);

		return repo.save(newObj.get());
	}

	public Promocao fromDTO(PromocaoNewDTO objDto) {
		
		UserSS user = UserService.authenticated();
		// não ta autenticado
		if(user == null) {
			throw new AuthorizationException("Acesso negado");
		}
		
		Optional<Usuario> usuario = userService.find(user.getId());
		Optional<Categoria> cat = categoriaService.find(objDto.getIdCategoria());

		Promocao obj = new Promocao(null, objDto.getDescricao(), objDto.getPreco(), objDto.getLocalizacao(), objDto.getEndereco(),
				objDto.getTitulo(), cat.get(), usuario.get());

		return obj;
	}

	public Promocao fromDTO(PromocaoUpdateDTO objDto) {
		return new Promocao(null, objDto.getDescricao(), objDto.getPreco(), objDto.getLocalizacao(), objDto.getEndereco(), objDto.getTitulo());
	}

	private void updateData(Optional<Promocao> newObj, Promocao obj) {
		if (obj.getTitulo() != null)
			newObj.get().setTitulo(obj.getTitulo());
		if (obj.getDescricao() != null)
			newObj.get().setDescricao(obj.getDescricao());
		if (obj.getLocalizacao() != null)
			newObj.get().setLocalizacao(obj.getLocalizacao());
		if (obj.getPreco() != null)
			newObj.get().setPreco(obj.getPreco());
	}
	
	public void delete(Integer id) {
		repo.deleteById(id);
	}
	
	public void disable(Integer id) {
		Optional<Promocao> obj = find(id);
		
		obj.get().setPublicada(false);
		
		repo.save(obj.get());
	}
	
	public void active(Integer id) {
		Optional<Promocao> obj = find(id);
		obj.get().setPublicada(true);
		repo.save(obj.get());
	}

	public List<Promocao> findAll() {
		List<Promocao> objs = repo.findAll();
		return objs;
	}

	public List<Promocao> findAllUser() {
		
		UserSS user = UserService.authenticated();
		// não ta autenticado
		if(user == null) {
			throw new AuthorizationException("Acesso negado");
		}
		
		List<Promocao> objs = repo.findByUsuario(userService.find(user.getId()));
		return objs;
	}

	public Page<Promocao> findPage(Integer idCat, Integer page, Integer linesPerPage, String orderBy, String direction) {

		PageRequest pageRequest = PageRequest.of(page, linesPerPage, Direction.valueOf(direction), orderBy);
		Optional<Categoria> cat = categoriaService.find(idCat);
		return repo.findByCategoria(true, cat.get().getId(), pageRequest);
	}

	public Page<Promocao> findPage(Integer page, Integer linesPerPage, String orderBy, String direction) {
		
		PageRequest pageRequest = PageRequest.of(page, linesPerPage, Direction.valueOf(direction), orderBy);
		return repo.findAll(true, pageRequest);
	}
}
