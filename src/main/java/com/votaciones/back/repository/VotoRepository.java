package com.votaciones.back.repository;

import com.votaciones.back.model.entity.Tblvoto;
import org.springframework.data.jpa.repository.JpaRepository;

public interface VotoRepository extends JpaRepository<Tblvoto, Integer> {
}
