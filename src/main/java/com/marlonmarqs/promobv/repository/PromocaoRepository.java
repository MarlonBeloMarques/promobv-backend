package com.marlonmarqs.promobv.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.marlonmarqs.promobv.domain.Promocao;

@Repository
public interface PromocaoRepository extends JpaRepository<Promocao, Integer> {

}