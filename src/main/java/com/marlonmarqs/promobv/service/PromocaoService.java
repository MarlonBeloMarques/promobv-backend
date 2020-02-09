package com.marlonmarqs.promobv.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.marlonmarqs.promobv.domain.Promocao;
import com.marlonmarqs.promobv.repository.PromocaoRepository;
import com.marlonmarqs.promobv.resources.exceptions.ObjectNotFoundException;

@Service
public class PromocaoService {

	@Autowired
	private PromocaoRepository repo;

	public Optional<Promocao> find(Integer id) {
		Optional<Promocao> obj = repo.findById(id);
		
		if (obj == null) {
			throw new ObjectNotFoundException(
					"Objeto não encontrado! Id: " + id + ", Tipo: " + Promocao.class.getName());
		}
		return obj;
	}

	public List<Promocao> findAll() {
		List<Promocao> objs = repo.findAll();
		return objs;
	}
}
