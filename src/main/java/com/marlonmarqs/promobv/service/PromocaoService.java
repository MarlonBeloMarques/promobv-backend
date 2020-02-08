package com.marlonmarqs.promobv.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.marlonmarqs.promobv.domain.Promocao;
import com.marlonmarqs.promobv.repository.PromocaoRepository;

@Service
public class PromocaoService {

	@Autowired
	private PromocaoRepository repo;
	
	public Optional<Promocao> find(Integer id) {
		Optional<Promocao> obj = repo.findById(id);
		return obj;
	}
}
