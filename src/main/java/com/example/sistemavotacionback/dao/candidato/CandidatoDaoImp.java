package com.example.sistemavotacionback.dao.candidato;

import com.example.sistemavotacionback.repository.CandidatoRepository;

public class CandidatoDaoImp implements CandidatoDao {
    private final CandidatoRepository candidatoRepository;

    public CandidatoDaoImp(CandidatoRepository candidatoRepository) {
        this.candidatoRepository = candidatoRepository;
    }
}
