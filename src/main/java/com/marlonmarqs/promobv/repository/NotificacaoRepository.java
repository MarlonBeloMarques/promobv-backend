package com.marlonmarqs.promobv.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.marlonmarqs.promobv.domain.Notificacao;
import com.marlonmarqs.promobv.domain.Promocao;

@Repository
public interface NotificacaoRepository extends JpaRepository<Notificacao, Integer> {

	@Transactional(readOnly = true)
	List<Notificacao> findByPromocao(Promocao promocao);
}