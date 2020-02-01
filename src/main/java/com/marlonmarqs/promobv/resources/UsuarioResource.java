package com.marlonmarqs.promobv.resources;

import java.util.ArrayList;
import java.util.List;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.marlonmarqs.promobv.domain.Usuario;

@RestController
@RequestMapping(value = "/usuarios")
public class UsuarioResource {

	@RequestMapping(method=RequestMethod.GET)
	public List<Usuario> listar() {

		Usuario user1 = new Usuario(1, "Marlon", "marlon.belohd@gmail.com");
		Usuario user2 = new Usuario(2, "Matheus", "matheus.belo@gmail.com");

		List<Usuario> lista = new ArrayList<>();
		lista.add(user1);
		lista.add(user2);

		return lista;
	}
}
