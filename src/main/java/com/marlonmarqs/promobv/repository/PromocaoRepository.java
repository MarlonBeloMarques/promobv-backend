package com.marlonmarqs.promobv.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.marlonmarqs.promobv.domain.Categoria;
import com.marlonmarqs.promobv.domain.Promocao;
import com.marlonmarqs.promobv.domain.Usuario;

@Repository
public interface PromocaoRepository extends JpaRepository<Promocao, Integer> {

	@Transactional(readOnly = true)
	List<Promocao> findByUsuario(Optional<Usuario> usuario);
	
	@Transactional(readOnly = true)
	@Query("SELECT obj FROM Promocao obj WHERE obj.publicada = :status AND obj.categoria.id = :categoriaId")
	Page<Promocao> findByCategoria(@Param("status") Boolean status, @Param("categoriaId") Integer id, Pageable pageRequest);
	
	@Transactional(readOnly = true)
	@Query("SELECT obj FROM Promocao obj WHERE obj.publicada = :status")
	Page<Promocao> findAll(@Param("status") Boolean status, Pageable pageRequest);
}