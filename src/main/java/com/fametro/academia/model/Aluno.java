package com.fametro.academia.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Aluno {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "O nome do aluno é obrigatório")
    private String nome;

    @NotBlank(message = "O e-mail é obrigatório")
    @Email(message = "Digite um e-mail válido")
    @Column(unique = true)
    private String email;

    @NotNull(message = "A idade é obrigatória")
    @Positive(message = "A idade deve ser positiva")
    private Integer idade;

    // REMOVE: excluir o aluno exclui os treinos dele (veja o comentário em Professor)
    @JsonIgnore
    @OneToMany(mappedBy = "aluno", cascade = CascadeType.REMOVE)
    private List<Treino> treinos = new ArrayList<>();

    public Aluno() {}

    public Aluno(String nome, String email, Integer idade) {
        this.nome = nome;
        this.email = email;
        this.idade = idade;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public Integer getIdade() { return idade; }
    public void setIdade(Integer idade) { this.idade = idade; }

    public List<Treino> getTreinos() { return treinos; }
    public void setTreinos(List<Treino> treinos) { this.treinos = treinos; }
}
