package com.fametro.academia.repository;

import com.fametro.academia.model.Treino;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TreinoRepository extends CrudRepository<Treino, Long> {
}
