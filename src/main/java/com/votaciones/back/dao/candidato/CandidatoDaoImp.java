package com.votaciones.back.dao.candidato;

import com.votaciones.back.model.entity.Tblcandidato;
import com.votaciones.back.repository.CandidatoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class CandidatoDaoImp implements CandidatoDao {
    private final CandidatoRepository candidatoRepository;

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
    public void deleteCandidatoByCvecan(long cvecan) {
        candidatoRepository.deleteTblcandidatoByCvecan(cvecan);
    }

    @Override
    public List<Long> findAllCandidatoesByCvecan() {
        return candidatoRepository.findAllCandidatoesByCvecan();
    }

    @Override
    public Long findCveuserByCvecan(Long cvecan) {
        return candidatoRepository.findCveuserByCvecan(cvecan);
    }

    @Override
    public boolean existCandidatoByPlantilla(String plantilla){
        return candidatoRepository.existsTblcandidatoByPlantilla(plantilla);
    }

    @Override
    public Tblcandidato findTblcandidatoByPlantilla(String plantilla){
        return candidatoRepository.findTblcandidatoByPlantilla(plantilla);
    }

    @Override
    public Tblcandidato findTblcandidatoWithMaxVotos() {
        return candidatoRepository.findTblcandidatoWithMaxVotos();
    }

}
