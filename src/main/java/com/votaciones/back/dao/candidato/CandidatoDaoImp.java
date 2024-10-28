package com.votaciones.back.dao.candidato;

import com.votaciones.back.repository.CandidatoRepository;

public class CandidatoDaoImp implements CandidatoDao {
    private final CandidatoRepository candidatoRepository;

    public CandidatoDaoImp(CandidatoRepository candidatoRepository) {
        this.candidatoRepository = candidatoRepository;
    }
}
