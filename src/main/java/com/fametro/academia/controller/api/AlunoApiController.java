package com.fametro.academia.controller.api;

import com.fametro.academia.model.Aluno;
import com.fametro.academia.repository.AlunoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/alunos")
public class AlunoApiController {

    @Autowired
    private AlunoRepository alunoRepository;

    @GetMapping
    public Iterable<Aluno> listar() {
        return alunoRepository.findAll();
    }

    @GetMapping("/{id}")
    public Aluno buscar(@PathVariable("id") long id) {
        return buscarOu404(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Aluno criar(@Valid @RequestBody Aluno aluno) {
        aluno.setId(null); // o id é sempre gerado pelo banco
        return alunoRepository.save(aluno);
    }

    @PutMapping("/{id}")
    public Aluno atualizar(@PathVariable("id") long id, @Valid @RequestBody Aluno dados) {
        Aluno aluno = buscarOu404(id);
        aluno.setNome(dados.getNome());
        aluno.setEmail(dados.getEmail());
        aluno.setIdade(dados.getIdade());
        return alunoRepository.save(aluno);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void excluir(@PathVariable("id") long id) {
        alunoRepository.delete(buscarOu404(id));
    }

    private Aluno buscarOu404(long id) {
        return alunoRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Aluno não encontrado: " + id));
    }
}
