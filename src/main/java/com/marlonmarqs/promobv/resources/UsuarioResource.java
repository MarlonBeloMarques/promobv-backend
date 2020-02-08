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
			List<Usuario> lista = new ArrayList<>();
		return lista;
	}
}
