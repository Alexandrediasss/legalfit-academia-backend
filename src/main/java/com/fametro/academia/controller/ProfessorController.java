package com.fametro.academia.controller;

import com.fametro.academia.model.Professor;
import com.fametro.academia.repository.ProfessorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;

@Controller
@RequestMapping("/professores")
public class ProfessorController {

    @Autowired
    private ProfessorRepository professorRepository;

    @GetMapping
    public String listarProfessores(Model model) {
        model.addAttribute("professores", professorRepository.findAll());
        return "professores/listar";
    }

    @GetMapping("/novo")
    public String mostrarFormularioNovoProfessor(Model model) {
        model.addAttribute("professor", new Professor());
        return "professores/formulario";
    }

    @PostMapping("/salvar")
    public String salvarProfessor(@Valid @ModelAttribute("professor") Professor professor,
                                  BindingResult result,
                                  Model model) {
        if (result.hasErrors()) {
            return "professores/formulario";
        }
        professorRepository.save(professor);
        return "redirect:/professores";
    }

    @GetMapping("/editar/{id}")
    public String mostrarFormularioEditarProfessor(@PathVariable("id") long id,
                                                   Model model) {
        Professor professor = professorRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("ID inválido: " + id));
        model.addAttribute("professor", professor);
        return "professores/formulario";
    }

    @GetMapping("/excluir/{id}")
    public String excluirProfessor(@PathVariable("id") long id) {
        Professor professor = professorRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("ID inválido: " + id));
        professorRepository.delete(professor);
        return "redirect:/professores";
    }
}
