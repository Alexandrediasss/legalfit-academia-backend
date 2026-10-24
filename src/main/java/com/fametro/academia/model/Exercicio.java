package com.fametro.academia.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;

@Entity
public class Exercicio {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "O nome do exercício é obrigatório")
    private String nome;

    @NotNull(message = "O número de séries é obrigatório")
    @Positive(message = "As séries devem ser positivas")
    private Integer series;

    @NotNull(message = "O número de repetições é obrigatório")
    @Positive(message = "As repetições devem ser positivas")
    private Integer repeticoes;

    // Zero é permitido para exercícios com o peso do corpo (ex.: flexão)
    @NotNull(message = "A carga é obrigatória")
    @PositiveOrZero(message = "A carga não pode ser negativa")
    private Double carga;

    @NotNull(message = "Selecione um treino")
    @ManyToOne
    @JoinColumn(name = "treino_id", nullable = false)
    private Treino treino;

    public Exercicio() {}

    public Exercicio(String nome, Integer series, Integer repeticoes, Double carga, Treino treino) {
        this.nome = nome;
        this.series = series;
        this.repeticoes = repeticoes;
        this.carga = carga;
        this.treino = treino;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }

    public Integer getSeries() { return series; }
    public void setSeries(Integer series) { this.series = series; }

    public Integer getRepeticoes() { return repeticoes; }
    public void setRepeticoes(Integer repeticoes) { this.repeticoes = repeticoes; }

    public Double getCarga() { return carga; }
    public void setCarga(Double carga) { this.carga = carga; }

    public Treino getTreino() { return treino; }
    public void setTreino(Treino treino) { this.treino = treino; }
}
