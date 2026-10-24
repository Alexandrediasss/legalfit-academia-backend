package com.fametro.academia.controller;

import com.fametro.academia.model.Aluno;
import com.fametro.academia.repository.AlunoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;

@Controller
@RequestMapping("/alunos")
public class AlunoController {

    @Autowired
    private AlunoRepository alunoRepository;

    @GetMapping
    public String listarAlunos(Model model) {
        model.addAttribute("alunos", alunoRepository.findAll());
        return "alunos/listar";
    }

    @GetMapping("/novo")
    public String mostrarFormularioNovoAluno(Model model) {
        model.addAttribute("aluno", new Aluno());
        return "alunos/formulario";
    }

    @PostMapping("/salvar")
    public String salvarAluno(@Valid @ModelAttribute("aluno") Aluno aluno,
                              BindingResult result,
                              Model model) {
        if (result.hasErrors()) {
            return "alunos/formulario";
        }
        alunoRepository.save(aluno);
        return "redirect:/alunos";
    }

    @GetMapping("/editar/{id}")
    public String mostrarFormularioEditarAluno(@PathVariable("id") long id,
                                               Model model) {
        Aluno aluno = alunoRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("ID inválido: " + id));
        model.addAttribute("aluno", aluno);
        return "alunos/formulario";
    }

    @GetMapping("/excluir/{id}")
    public String excluirAluno(@PathVariable("id") long id) {
        Aluno aluno = alunoRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("ID inválido: " + id));
        alunoRepository.delete(aluno);
        return "redirect:/alunos";
    }
}
