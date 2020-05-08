package com.marlonmarqs.promobv.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.marlonmarqs.promobv.domain.VerificaToken;

@Repository
public interface VerificaTokenRepository extends JpaRepository<VerificaToken, Integer> {

	VerificaToken findByToken(String token);
}
