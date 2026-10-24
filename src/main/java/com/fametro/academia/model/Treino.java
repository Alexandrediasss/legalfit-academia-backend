package com.fametro.academia.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Treino {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "O nome do treino é obrigatório")
    private String nome;

    @NotBlank(message = "O objetivo é obrigatório")
    private String objetivo;

    @NotNull(message = "Selecione um aluno")
    @ManyToOne
    @JoinColumn(name = "aluno_id", nullable = false)
    private Aluno aluno;

    @NotNull(message = "Selecione um professor")
    @ManyToOne
    @JoinColumn(name = "professor_id", nullable = false)
    private Professor professor;

    // REMOVE: excluir o treino exclui os exercícios dele (veja o comentário em Professor)
    @JsonIgnore
    @OneToMany(mappedBy = "treino", cascade = CascadeType.REMOVE)
    private List<Exercicio> exercicios = new ArrayList<>();

    public Treino() {}

    public Treino(String nome, String objetivo, Aluno aluno, Professor professor) {
        this.nome = nome;
        this.objetivo = objetivo;
        this.aluno = aluno;
        this.professor = professor;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }

    public String getObjetivo() { return objetivo; }
    public void setObjetivo(String objetivo) { this.objetivo = objetivo; }

    public Aluno getAluno() { return aluno; }
    public void setAluno(Aluno aluno) { this.aluno = aluno; }

    public Professor getProfessor() { return professor; }
    public void setProfessor(Professor professor) { this.professor = professor; }

    public List<Exercicio> getExercicios() { return exercicios; }
    public void setExercicios(List<Exercicio> exercicios) { this.exercicios = exercicios; }
}
