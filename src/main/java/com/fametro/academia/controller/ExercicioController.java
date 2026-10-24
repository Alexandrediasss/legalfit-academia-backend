package com.fametro.academia.controller;

import com.fametro.academia.model.Exercicio;
import com.fametro.academia.repository.ExercicioRepository;
import com.fametro.academia.repository.TreinoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;

@Controller
@RequestMapping("/exercicios")
public class ExercicioController {

    @Autowired
    private ExercicioRepository exercicioRepository;

    @Autowired
    private TreinoRepository treinoRepository;

    @GetMapping
    public String listarExercicios(Model model) {
        model.addAttribute("exercicios", exercicioRepository.findAll());
        return "exercicios/listar";
    }

    @GetMapping("/novo")
    public String mostrarFormularioNovoExercicio(Model model) {
        model.addAttribute("exercicio", new Exercicio());
        model.addAttribute("treinos", treinoRepository.findAll());
        return "exercicios/formulario";
    }

    @PostMapping("/salvar")
    public String salvarExercicio(@Valid @ModelAttribute("exercicio") Exercicio exercicio,
                                  BindingResult result,
                                  Model model) {
        if (result.hasErrors()) {
            model.addAttribute("treinos", treinoRepository.findAll());
            return "exercicios/formulario";
        }
        exercicioRepository.save(exercicio);
        return "redirect:/exercicios";
    }

    @GetMapping("/editar/{id}")
    public String mostrarFormularioEditarExercicio(@PathVariable("id") long id,
                                                   Model model) {
        Exercicio exercicio = exercicioRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("ID inválido: " + id));
        model.addAttribute("exercicio", exercicio);
        model.addAttribute("treinos", treinoRepository.findAll());
        return "exercicios/formulario";
    }

    @GetMapping("/excluir/{id}")
    public String excluirExercicio(@PathVariable("id") long id) {
        Exercicio exercicio = exercicioRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("ID inválido: " + id));
        exercicioRepository.delete(exercicio);
        return "redirect:/exercicios";
    }
}
