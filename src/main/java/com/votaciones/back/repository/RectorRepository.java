package com.votaciones.back.repository;

import com.votaciones.back.model.entity.Tblrector;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RectorRepository extends JpaRepository<Tblrector, Integer> {
}
