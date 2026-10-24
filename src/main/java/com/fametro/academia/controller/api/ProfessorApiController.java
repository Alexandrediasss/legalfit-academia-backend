package com.fametro.academia.controller.api;

import com.fametro.academia.model.Professor;
import com.fametro.academia.repository.ProfessorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/professores")
public class ProfessorApiController {

    @Autowired
    private ProfessorRepository professorRepository;

    @GetMapping
    public Iterable<Professor> listar() {
        return professorRepository.findAll();
    }

    @GetMapping("/{id}")
    public Professor buscar(@PathVariable("id") long id) {
        return buscarOu404(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Professor criar(@Valid @RequestBody Professor professor) {
        professor.setId(null); // o id é sempre gerado pelo banco
        return professorRepository.save(professor);
    }

    @PutMapping("/{id}")
    public Professor atualizar(@PathVariable("id") long id, @Valid @RequestBody Professor dados) {
        Professor professor = buscarOu404(id);
        professor.setNome(dados.getNome());
        professor.setCref(dados.getCref());
        professor.setEspecialidade(dados.getEspecialidade());
        return professorRepository.save(professor);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void excluir(@PathVariable("id") long id) {
        professorRepository.delete(buscarOu404(id));
    }

    private Professor buscarOu404(long id) {
        return professorRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Professor não encontrado: " + id));
    }
}
