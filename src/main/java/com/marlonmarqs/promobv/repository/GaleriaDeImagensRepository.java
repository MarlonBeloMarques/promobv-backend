package com.marlonmarqs.promobv.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.marlonmarqs.promobv.domain.Categoria;
import com.marlonmarqs.promobv.domain.GaleriaDeImagens;

@Repository
public interface GaleriaDeImagensRepository extends JpaRepository<GaleriaDeImagens, Integer> {

}