package com.marlonmarqs.promobv.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.marlonmarqs.promobv.domain.Promocao;
import com.marlonmarqs.promobv.domain.Usuario;

@Repository
public interface PromocaoRepository extends JpaRepository<Promocao, Integer> {

	@Transactional(readOnly = true)
	List<Promocao> findByUsuario(Optional<Usuario> usuario);
}