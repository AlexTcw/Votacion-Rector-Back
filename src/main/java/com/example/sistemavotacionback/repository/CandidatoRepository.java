package com.example.sistemavotacionback.repository;

import com.example.sistemavotacionback.model.entity.Tblcandidato;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CandidatoRepository extends JpaRepository<Tblcandidato, Integer> {
}
