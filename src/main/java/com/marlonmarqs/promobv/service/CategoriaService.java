package com.marlonmarqs.promobv.service;

import java.util.Optional;

import org.springframework.stereotype.Service;

import com.marlonmarqs.promobv.domain.Categoria;
import com.marlonmarqs.promobv.repository.CategoriaRepository;

@Service
public class CategoriaService {

	private CategoriaRepository repo;
	
	public Optional<Categoria> find(Integer id) {
		Optional<Categoria> obj = repo.findById(id);
		
		return obj;
	}
}
