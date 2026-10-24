package com.fametro.academia.controller.api;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.server.ResponseStatusException;

import java.util.LinkedHashMap;
import java.util.Map;

// Transforma os erros da API em JSON simples para o front mostrar.
// Só vale para os controllers deste pacote (as telas Thymeleaf não mudam).
@RestControllerAdvice(basePackages = "com.fametro.academia.controller.api")
public class ApiExceptionHandler {

    // 400: campos inválidos -> {"nome": "O nome do aluno é obrigatório", "idade": "..."}
    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public Map<String, String> camposInvalidos(MethodArgumentNotValidException e) {
        Map<String, String> erros = new LinkedHashMap<>();
        for (FieldError erro : e.getBindingResult().getFieldErrors()) {
            erros.put(erro.getField(), erro.getDefaultMessage());
        }
        return erros;
    }

    // 404 (não encontrado) e 400 (aluno/professor/treino inexistente) -> {"erro": "..."}
    @ExceptionHandler(ResponseStatusException.class)
    public ResponseEntity<Map<String, String>> statusComMensagem(ResponseStatusException e) {
        return ResponseEntity.status(e.getStatusCode()).body(Map.of("erro", e.getReason()));
    }

    // 409: e-mail ou CREF repetido (o banco recusa por causa do unique)
    @ExceptionHandler(DataIntegrityViolationException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    public Map<String, String> valorRepetido(DataIntegrityViolationException e) {
        return Map.of("erro", "Já existe um cadastro com esse valor (e-mail ou CREF repetido)");
    }
}
