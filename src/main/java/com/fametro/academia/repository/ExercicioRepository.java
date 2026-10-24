package com.fametro.academia.repository;

import com.fametro.academia.model.Exercicio;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ExercicioRepository extends CrudRepository<Exercicio, Long> {
}
