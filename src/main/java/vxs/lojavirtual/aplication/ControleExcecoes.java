package vxs.lojavirtual.aplication;

import java.sql.SQLException;
import java.util.List;

import org.hibernate.exception.ConstraintViolationException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindException;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import vxs.lojavirtual.model.dto.ObjetoErroDTO;

@RestControllerAdvice
@ControllerAdvice
public class ControleExcecoes extends ResponseEntityExceptionHandler {

	
	
	 /*
      Tratamento de exceções genéricas.
     */
    @ExceptionHandler({ Exception.class, RuntimeException.class, Throwable.class })
    protected ResponseEntity<Object> handleGenericException(Exception ex, WebRequest request) {
        ObjetoErroDTO errorResponse = new ObjetoErroDTO();
        errorResponse.setError("Ocorreu um erro interno: " + ex.getMessage());
        errorResponse.setCode(HttpStatus.INTERNAL_SERVER_ERROR.value() + " - " + HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase());

        

        return new ResponseEntity<>(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
    }
    
    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(
            MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatusCode status, WebRequest request) {

        ObjetoErroDTO objetoErroDTO = new ObjetoErroDTO();
        StringBuilder msg = new StringBuilder();

        List<ObjectError> errors = ex.getBindingResult().getAllErrors();
        for (ObjectError error : errors) {
            msg.append(error.getDefaultMessage()).append("\n");
        }

        objetoErroDTO.setError(msg.toString());
        objetoErroDTO.setCode("Status code: " + status.value());
        return new ResponseEntity<>(objetoErroDTO, headers, status);
    }
    
    @ExceptionHandler({ DataIntegrityViolationException.class, ConstraintViolationException.class, SQLException.class })
    public ResponseEntity<Object> handleDatabaseExceptions(Exception ex) {
        ObjetoErroDTO objetoErroDTO = new ObjetoErroDTO();
        objetoErroDTO.setError("Erro relacionado ao banco de dados: " + ex.getMessage());
        objetoErroDTO.setCode(HttpStatus.INTERNAL_SERVER_ERROR.value() + " ==> " + HttpStatus.INTERNAL_SERVER_ERROR);
        return new ResponseEntity<>(objetoErroDTO, HttpStatus.INTERNAL_SERVER_ERROR);
    }
   
   @Override
    protected ResponseEntity<Object> handleBindException(
            BindException ex, HttpHeaders headers, HttpStatusCode status, WebRequest request) {
        ObjetoErroDTO objetoErroDTO = new ObjetoErroDTO();
        StringBuilder msg = new StringBuilder();

        List<ObjectError> errors = ex.getBindingResult().getAllErrors();
        for (ObjectError error : errors) {
            msg.append(error.getDefaultMessage()).append("\n");
        }

        objetoErroDTO.setError(msg.toString());
        objetoErroDTO.setCode("Status code: " + status.value());
        return new ResponseEntity<>(objetoErroDTO, headers, status);
    }
}