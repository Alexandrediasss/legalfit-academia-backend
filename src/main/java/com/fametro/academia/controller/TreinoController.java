package com.fametro.academia.controller;

import com.fametro.academia.model.Treino;
import com.fametro.academia.repository.AlunoRepository;
import com.fametro.academia.repository.ProfessorRepository;
import com.fametro.academia.repository.TreinoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;

@Controller
@RequestMapping("/treinos")
public class TreinoController {

    @Autowired
    private TreinoRepository treinoRepository;

    @Autowired
    private AlunoRepository alunoRepository;

    @Autowired
    private ProfessorRepository professorRepository;

    @GetMapping
    public String listarTreinos(Model model) {
        model.addAttribute("treinos", treinoRepository.findAll());
        return "treinos/listar";
    }

    @GetMapping("/novo")
    public String mostrarFormularioNovoTreino(Model model) {
        model.addAttribute("treino", new Treino());
        model.addAttribute("alunos", alunoRepository.findAll());
        model.addAttribute("professores", professorRepository.findAll());
        return "treinos/formulario";
    }

    @PostMapping("/salvar")
    public String salvarTreino(@Valid @ModelAttribute("treino") Treino treino,
                               BindingResult result,
                               Model model) {
        if (result.hasErrors()) {
            model.addAttribute("alunos", alunoRepository.findAll());
            model.addAttribute("professores", professorRepository.findAll());
            return "treinos/formulario";
        }
        treinoRepository.save(treino);
        return "redirect:/treinos";
    }

    @GetMapping("/editar/{id}")
    public String mostrarFormularioEditarTreino(@PathVariable("id") long id,
                                                Model model) {
        Treino treino = treinoRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("ID inválido: " + id));
        model.addAttribute("treino", treino);
        model.addAttribute("alunos", alunoRepository.findAll());
        model.addAttribute("professores", professorRepository.findAll());
        return "treinos/formulario";
    }

    @GetMapping("/excluir/{id}")
    public String excluirTreino(@PathVariable("id") long id) {
        Treino treino = treinoRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("ID inválido: " + id));
        treinoRepository.delete(treino);
        return "redirect:/treinos";
    }
}
