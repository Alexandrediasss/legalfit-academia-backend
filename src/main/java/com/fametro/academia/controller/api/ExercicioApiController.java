package com.fametro.academia.controller.api;

import com.fametro.academia.model.Exercicio;
import com.fametro.academia.model.Treino;
import com.fametro.academia.repository.ExercicioRepository;
import com.fametro.academia.repository.TreinoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/exercicios")
public class ExercicioApiController {

    @Autowired
    private ExercicioRepository exercicioRepository;

    @Autowired
    private TreinoRepository treinoRepository;

    @GetMapping
    public Iterable<Exercicio> listar() {
        return exercicioRepository.findAll();
    }

    @GetMapping("/{id}")
    public Exercicio buscar(@PathVariable("id") long id) {
        return buscarOu404(id);
    }

    // O front manda só o id do treino: { ..., "treino": {"id": 1} }
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Exercicio criar(@Valid @RequestBody Exercicio exercicio) {
        exercicio.setId(null); // o id é sempre gerado pelo banco
        exercicio.setTreino(buscarTreino(exercicio.getTreino()));
        return exercicioRepository.save(exercicio);
    }

    @PutMapping("/{id}")
    public Exercicio atualizar(@PathVariable("id") long id, @Valid @RequestBody Exercicio dados) {
        Exercicio exercicio = buscarOu404(id);
        exercicio.setNome(dados.getNome());
        exercicio.setSeries(dados.getSeries());
        exercicio.setRepeticoes(dados.getRepeticoes());
        exercicio.setCarga(dados.getCarga());
        exercicio.setTreino(buscarTreino(dados.getTreino()));
        return exercicioRepository.save(exercicio);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void excluir(@PathVariable("id") long id) {
        exercicioRepository.delete(buscarOu404(id));
    }

    private Exercicio buscarOu404(long id) {
        return exercicioRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Exercício não encontrado: " + id));
    }

    private Treino buscarTreino(Treino treino) {
        if (treino.getId() == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Informe o id do treino");
        }
        return treinoRepository.findById(treino.getId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "Treino não encontrado: " + treino.getId()));
    }
}
