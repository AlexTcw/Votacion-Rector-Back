package com.votaciones.back.repository;

import com.votaciones.back.model.entity.TblInvlidCandidato;
import org.springframework.data.jpa.repository.JpaRepository;

public interface InvalidCanRepository extends JpaRepository<TblInvlidCandidato, Integer> {
}
