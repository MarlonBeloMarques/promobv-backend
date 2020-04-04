package com.marlonmarqs.promobv.resources.exceptions;

import javax.servlet.http.HttpServletRequest;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.amazonaws.AmazonClientException;
import com.amazonaws.AmazonServiceException;
import com.amazonaws.services.s3.model.AmazonS3Exception;
import com.marlonmarqs.promobv.service.exceptions.AuthorizationException;
import com.marlonmarqs.promobv.service.exceptions.BusinessRuleException;
import com.marlonmarqs.promobv.service.exceptions.DataIntegrityException;
import com.marlonmarqs.promobv.service.exceptions.FileException;
import com.marlonmarqs.promobv.service.exceptions.ObjectNotFoundException;

@ControllerAdvice //metodo padrão do controlleradvice
public class ResourceExceptionHandler {

	@ExceptionHandler(ObjectNotFoundException.class) // tratador de excecao do tipo passado
	public ResponseEntity<StandardError> objectNotFound (ObjectNotFoundException e, HttpServletRequest request){

		StandardError err = new StandardError(HttpStatus.NOT_FOUND.value(), e.getMessage(), System.currentTimeMillis());
		return ResponseEntity.status(HttpStatus.NOT_FOUND).body(err);
	}
	
	@ExceptionHandler(DataIntegrityException.class) // tratador de excecao do tipo passado
	public ResponseEntity<StandardError> dataIntegrity (DataIntegrityException e, HttpServletRequest request){

		StandardError err = new StandardError(HttpStatus.BAD_REQUEST.value(), e.getMessage(), System.currentTimeMillis());
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(err);
	}
	
	@ExceptionHandler(BusinessRuleException.class) // tratador de excecao do tipo passado
	public ResponseEntity<StandardError> businessRule (BusinessRuleException e, HttpServletRequest request){

		StandardError err = new StandardError(HttpStatus.ACCEPTED.value(), e.getMessage(), System.currentTimeMillis());
		return ResponseEntity.status(HttpStatus.ACCEPTED).body(err);
	}
	
	@ExceptionHandler(AuthorizationException.class) // tratador de exceçao do tipo passado
	public ResponseEntity<StandardError> authorization (AuthorizationException e, HttpServletRequest request){

		StandardError err = new StandardError(HttpStatus.FORBIDDEN.value(), e.getMessage(), System.currentTimeMillis()); // acesso negado
		return ResponseEntity.status(HttpStatus.FORBIDDEN).body(err);
	}
	
	@ExceptionHandler(MethodArgumentNotValidException.class) // tratador de exceçao do tipo passado
	public ResponseEntity<StandardError> datIntegrity (MethodArgumentNotValidException e, HttpServletRequest request){

		ValidationError err = new ValidationError(HttpStatus.BAD_REQUEST.value(), "Erro de validação", System.currentTimeMillis());

		for(FieldError x : e.getBindingResult().getFieldErrors()) {
			err.addError(x.getField(), x.getDefaultMessage());
		}

		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(err);
	}
	
	@ExceptionHandler(FileException.class) // tratador de exceçao do tipo passado
	public ResponseEntity<StandardError> file (FileException e, HttpServletRequest request){

		StandardError err = new StandardError(HttpStatus.BAD_REQUEST.value(), e.getMessage(), System.currentTimeMillis()); // acesso negado
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(err);
	}

	@ExceptionHandler(AmazonServiceException.class) // tratador de exceçao do tipo passado
	public ResponseEntity<StandardError> amazonService (AmazonServiceException e, HttpServletRequest request){

		HttpStatus code = HttpStatus.valueOf(e.getErrorCode()); //pega o codigo http da excecao e transforma em httpstatus
		StandardError err = new StandardError(code.value(), e.getMessage(), System.currentTimeMillis()); // acesso negado
		return ResponseEntity.status(code).body(err);
	}

	@ExceptionHandler(AmazonClientException.class) // tratador de exceçao do tipo passado
	public ResponseEntity<StandardError> amazonClient (AmazonClientException e, HttpServletRequest request){

		StandardError err = new StandardError(HttpStatus.FORBIDDEN.value(), e.getMessage(), System.currentTimeMillis()); // acesso negado
		return ResponseEntity.status(HttpStatus.FORBIDDEN).body(err);
	}

	@ExceptionHandler(AmazonS3Exception.class) // tratador de exceçao do tipo passado
	public ResponseEntity<StandardError> amazonS3 (AmazonS3Exception e, HttpServletRequest request){

		StandardError err = new StandardError(HttpStatus.FORBIDDEN.value(), e.getMessage(), System.currentTimeMillis()); // acesso negado
		return ResponseEntity.status(HttpStatus.FORBIDDEN).body(err);
	}
}
