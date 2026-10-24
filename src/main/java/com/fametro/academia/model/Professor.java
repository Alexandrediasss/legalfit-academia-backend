package com.fametro.academia.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Professor {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "O nome do professor é obrigatório")
    private String nome;

    @NotBlank(message = "O CREF é obrigatório")
    @Column(unique = true)
    private String cref;

    @NotBlank(message = "A especialidade é obrigatória")
    private String especialidade;

    // REMOVE: excluir o professor exclui os treinos dele.
    // Não usamos ALL + orphanRemoval (como no PDF) porque, ao editar pelo formulário,
    // a lista chega vazia e o Hibernate apagaria todos os treinos do professor.
    // @JsonIgnore: a API não manda essa lista no JSON (senão: professor -> treinos -> professor -> ... infinito)
    @JsonIgnore
    @OneToMany(mappedBy = "professor", cascade = CascadeType.REMOVE)
    private List<Treino> treinos = new ArrayList<>();

    public Professor() {}

    public Professor(String nome, String cref, String especialidade) {
        this.nome = nome;
        this.cref = cref;
        this.especialidade = especialidade;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }

    public String getCref() { return cref; }
    public void setCref(String cref) { this.cref = cref; }

    public String getEspecialidade() { return especialidade; }
    public void setEspecialidade(String especialidade) { this.especialidade = especialidade; }

    public List<Treino> getTreinos() { return treinos; }
    public void setTreinos(List<Treino> treinos) { this.treinos = treinos; }
}
