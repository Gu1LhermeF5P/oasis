package br.com.fiap.oasis.oasis.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.net.URI;
import java.time.Instant;
import java.util.List;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ProblemDetail handleValidationErrors(MethodArgumentNotValidException ex) {
        ProblemDetail problemDetail = ProblemDetail.forStatus(HttpStatus.BAD_REQUEST);
        problemDetail.setTitle("Dados Inválidos");
        problemDetail.setDetail("Um ou mais campos não preenchem os requisitos de validação.");
        problemDetail.setType(URI.create("https://oasis.fiap.com.br/erros/bad-request"));
        problemDetail.setProperty("timestamp", Instant.now());
        
        List<String> errors = ex.getBindingResult().getFieldErrors().stream()
                .map(f -> String.format("Campo '%s': %s", f.getField(), f.getDefaultMessage()))
                .toList();
        
        problemDetail.setProperty("errors", errors);
        return problemDetail;
    }
    
    // Adicione aqui outros handlers (ex: EntityNotFound, IllegalArgumentException)
    @ExceptionHandler(Exception.class)
    public ProblemDetail handleGeneralException(Exception ex) {
        ProblemDetail problemDetail = ProblemDetail.forStatus(HttpStatus.INTERNAL_SERVER_ERROR);
        problemDetail.setTitle("Erro Interno");
        problemDetail.setDetail(ex.getMessage());
        problemDetail.setProperty("timestamp", Instant.now());
        return problemDetail;
    }
}