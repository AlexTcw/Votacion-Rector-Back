package com.example.sistemavotacionback.repository;

import com.example.sistemavotacionback.model.entity.Tblinst;
import org.springframework.data.jpa.repository.JpaRepository;

public interface InstitucionRepository extends JpaRepository<Tblinst, Integer> {
}
