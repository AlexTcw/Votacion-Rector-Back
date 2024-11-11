package com.votaciones.back.dao.candidato;

import com.votaciones.back.model.entity.Tblcandidato;
import com.votaciones.back.repository.CandidatoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

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

    @Override
    public Tblcandidato findTblcandidatoByCvecan(long cvecan) {
        return candidatoRepository.findTblcandidatoByCvecan(cvecan);
    }

    @Override
    public boolean existTblcandidatoByCveCan(long cveCandidato) {
        return candidatoRepository.existsTblcandidatoByCvecan(cveCandidato);
    }

    @Override
    public List<Tblcandidato> findAllByAniocan(int aniocan) {
        return candidatoRepository.findAllByAniocan(aniocan);
    }

    @Override
    public void deleteCandidatoByCvecan(long cvecan) {
        candidatoRepository.deleteTblcandidatoByCvecan(cvecan);
    }

    @Override
    public List<Long> findAllCandidatoesByCvecan() {
        return candidatoRepository.findAllCandidatoesByCvecan();
    }
}
