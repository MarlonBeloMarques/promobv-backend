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
import com.marlonmarqs.promobv.dto.PromocaoDTO;
import com.marlonmarqs.promobv.repository.PromocaoRepository;
import com.marlonmarqs.promobv.service.exceptions.ObjectNotFoundException;

@Service
public class PromocaoService {

	@Autowired
	private PromocaoRepository repo;
	
	@Autowired
	private UsuarioService userService;

	@Autowired
	private CategoriaService categoriaService;
	
	public Optional<Promocao> find(Integer id) {
		Optional<Promocao> obj = repo.findById(id);
		
		obj.orElseThrow(() -> new ObjectNotFoundException("Objeto não encontrado! Id: " + id
				+ ", Tipo: " + Promocao.class.getName()));
		
		return obj;
	}

	public List<Promocao> findAll() {
		List<Promocao> objs = repo.findAll();
		return objs;
	}
	
	public List<Promocao> findAllUser(Integer idUser) {
		List<Promocao> objs = repo.findByUsuario(userService.find(idUser));
		return objs;
	}
	
	public Page<Promocao> findPage(Integer idCat, Integer page, Integer linesPerPage, String orderBy, String direction){
		
		PageRequest pageRequest = PageRequest.of(page, linesPerPage, Direction.valueOf(direction), orderBy);
		Optional<Categoria> cat = categoriaService.find(idCat);
		return repo.findByCategoria(cat, pageRequest);
	}
	
	public Page<Promocao> findPage(Integer page, Integer linesPerPage, String orderBy, String direction){
		
		PageRequest pageRequest = PageRequest.of(page, linesPerPage, Direction.valueOf(direction), orderBy);
		return repo.findAll(pageRequest);
	}
}
