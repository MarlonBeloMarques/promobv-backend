package com.marlonmarqs.promobv.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.marlonmarqs.promobv.domain.Usuario;
import com.marlonmarqs.promobv.repository.UsuarioRepository;

@Service
public class UsuarioService {

	@Autowired
	private UsuarioRepository repo;
	
	public Optional<Usuario> find(Integer id) {
		Optional<Usuario> obj = repo.findById(id);
		return obj;
	}
}
