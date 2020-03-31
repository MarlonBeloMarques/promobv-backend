package com.marlonmarqs.promobv.service.validation;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.HandlerMapping;

import com.marlonmarqs.promobv.domain.Usuario;
import com.marlonmarqs.promobv.dto.UsuarioUpdateDTO;
import com.marlonmarqs.promobv.repository.UsuarioRepository;
import com.marlonmarqs.promobv.resources.exceptions.FieldMessage;

public class UsuarioUpdateValidator implements ConstraintValidator<UsuarioUpdate, UsuarioUpdateDTO> {

	@Autowired
	private HttpServletRequest request; // permite obter o parametro da URI

	@Autowired
	private UsuarioRepository repo;

	@Override
	public void initialize(UsuarioUpdate ann) {
	}

	@Override
	public boolean isValid(UsuarioUpdateDTO objDto, ConstraintValidatorContext context) {

		// converte um tipo URI para map
		@SuppressWarnings("unchecked") // tirar mensagem "chata"
		Map<String, String> map = (Map<String, String>) request.getAttribute(HandlerMapping.URI_TEMPLATE_VARIABLES_ATTRIBUTE); // MAP = mapa de atributos, comparado com um json
		Integer uriId =Integer.parseInt(map.get("id"));

		List<FieldMessage> list = new ArrayList<>();

		// inclua os testes aqui, inserindo erros na lista

		Usuario aux = repo.findByEmail(objDto.getEmail());
		if(aux != null && !aux.getId().equals(uriId)) {
			list.add(new FieldMessage("email", "Email já existente"));
		}

		for (FieldMessage e : list) {
			// lista de erros do framework, permite transportar a minha lista de erros para o framework
			context.disableDefaultConstraintViolation();
			context.buildConstraintViolationWithTemplate(e.getMessage()).addPropertyNode(e.getFieldName())
					.addConstraintViolation();
		}
		return list.isEmpty();
	}
} 