package com.marlonmarqs.promobv.service;

import org.springframework.security.core.context.SecurityContextHolder;

import com.marlonmarqs.promobv.security.UserSS;

public class UserService {

	// retorna o usuario logado no sistema
	public static UserSS authenticated() {
		try {
			return (UserSS) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		}
		catch (Exception e) {
			return null;
		}
	}
}
