package com.marlonmarqs.promobv.service;

import java.util.List;
import java.util.Optional;

import javax.validation.ConstraintViolationException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.marlonmarqs.promobv.domain.Categoria;
import com.marlonmarqs.promobv.dto.CategoriaDTO;
import com.marlonmarqs.promobv.repository.CategoriaRepository;
import com.marlonmarqs.promobv.service.exceptions.DataIntegrityException;
import com.marlonmarqs.promobv.service.exceptions.ObjectNotFoundException;

@Service
public class CategoriaService {

	@Autowired
	private CategoriaRepository repo;

	public Optional<Categoria> find(Integer id) {
		Optional<Categoria> obj = repo.findById(id);

		obj.orElseThrow(() -> new ObjectNotFoundException("Objeto não encontrado! Id: " + id
				+ ", Tipo: " + Categoria.class.getName()));

		return obj;
	}
	
	public List<Categoria> findAll() {
		List<Categoria> objs = repo.findAll();
		return objs;
	}
	
	public Categoria fromDTO(CategoriaDTO objDto) {
		return new Categoria(objDto.getId(), objDto.getNome());
	}
	
	public Categoria insert(Categoria obj) {
		obj.setId(null);
		return repo.save(obj);
	}
	
	public Categoria update(Categoria obj) {
		Optional<Categoria> newObj = find(obj.getId());
		updateData(newObj, obj);
		return repo.save(obj);
	}
	
	public void updateData(Optional<Categoria> newObj, Categoria obj) {
		newObj.get().setNome(obj.getNome());
	}
	
	public void delete(Integer id) throws DataIntegrityException {
		Optional<Categoria> obj = find(id);

		if(obj.get().getPromocoes().isEmpty()) {
			repo.deleteById(id);
		} else {
			throw new DataIntegrityException("Não é possível excluir uma categoria que possui promoções");	
		}
	}
}
