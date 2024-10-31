package com.votaciones.back.dao.candidato;

import com.votaciones.back.model.entity.Tblcandidato;
import com.votaciones.back.repository.CandidatoRepository;
import org.springframework.stereotype.Repository;

@Repository
public class CandidatoDaoImp implements CandidatoDao {
    private final CandidatoRepository candidatoRepository;

    public CandidatoDaoImp(CandidatoRepository candidatoRepository) {
        this.candidatoRepository = candidatoRepository;
    }

    @Override
    public Tblcandidato createOrUpdateCandidato(Tblcandidato candidato) {
        return candidatoRepository.save(candidato);
    }

    @Override
    public Tblcandidato findTblcandidatoByCveuser(long cveuser) {
        return candidatoRepository.findTblcandidatoByCveuser(cveuser);
    }
}
