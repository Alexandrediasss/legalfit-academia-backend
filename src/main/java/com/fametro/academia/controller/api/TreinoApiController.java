package com.fametro.academia.controller.api;

import com.fametro.academia.model.Aluno;
import com.fametro.academia.model.Professor;
import com.fametro.academia.model.Treino;
import com.fametro.academia.repository.AlunoRepository;
import com.fametro.academia.repository.ProfessorRepository;
import com.fametro.academia.repository.TreinoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/treinos")
public class TreinoApiController {

    @Autowired
    private TreinoRepository treinoRepository;

    @Autowired
    private AlunoRepository alunoRepository;

    @Autowired
    private ProfessorRepository professorRepository;

    @GetMapping
    public Iterable<Treino> listar() {
        return treinoRepository.findAll();
    }

    @GetMapping("/{id}")
    public Treino buscar(@PathVariable("id") long id) {
        return buscarOu404(id);
    }

    // O front manda só o id do aluno e do professor: { ..., "aluno": {"id": 1}, "professor": {"id": 2} }
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Treino criar(@Valid @RequestBody Treino treino) {
        treino.setId(null); // o id é sempre gerado pelo banco
        treino.setAluno(buscarAluno(treino.getAluno()));
        treino.setProfessor(buscarProfessor(treino.getProfessor()));
        return treinoRepository.save(treino);
    }

    @PutMapping("/{id}")
    public Treino atualizar(@PathVariable("id") long id, @Valid @RequestBody Treino dados) {
        Treino treino = buscarOu404(id);
        treino.setNome(dados.getNome());
        treino.setObjetivo(dados.getObjetivo());
        treino.setAluno(buscarAluno(dados.getAluno()));
        treino.setProfessor(buscarProfessor(dados.getProfessor()));
        return treinoRepository.save(treino);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void excluir(@PathVariable("id") long id) {
        treinoRepository.delete(buscarOu404(id));
    }

    private Treino buscarOu404(long id) {
        return treinoRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Treino não encontrado: " + id));
    }

    private Aluno buscarAluno(Aluno aluno) {
        if (aluno.getId() == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Informe o id do aluno");
        }
        return alunoRepository.findById(aluno.getId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "Aluno não encontrado: " + aluno.getId()));
    }

    private Professor buscarProfessor(Professor professor) {
        if (professor.getId() == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Informe o id do professor");
        }
        return professorRepository.findById(professor.getId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "Professor não encontrado: " + professor.getId()));
    }
}
