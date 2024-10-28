package com.votaciones.back.repository;

import com.votaciones.back.model.entity.Tblcandidato;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CandidatoRepository extends JpaRepository<Tblcandidato, Integer> {
}
