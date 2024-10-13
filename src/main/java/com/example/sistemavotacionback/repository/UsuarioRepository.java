package com.example.sistemavotacionback.repository;

import com.example.sistemavotacionback.model.entity.Tbluser;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UsuarioRepository extends JpaRepository<Tbluser, Integer> {
}
