package com.marlonmarqs.promobv.resources.exceptions;

import javax.servlet.http.HttpServletRequest;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.marlonmarqs.promobv.service.exceptions.ObjectNotFoundException;

@ControllerAdvice //metodo padrão do controlleradvice
public class ResourceExceptionHandler {

	@ExceptionHandler(ObjectNotFoundException.class) // tratador de excessao do tipo passado
	public ResponseEntity<StandardError> objectNotFound (ObjectNotFoundException e, HttpServletRequest request){

		StandardError err = new StandardError(HttpStatus.NOT_FOUND.value(), e.getMessage(), System.currentTimeMillis());
		return ResponseEntity.status(HttpStatus.NOT_FOUND).body(err);
	}
}
