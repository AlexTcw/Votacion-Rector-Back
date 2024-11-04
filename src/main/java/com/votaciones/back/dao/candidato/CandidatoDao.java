package com.votaciones.back.dao.candidato;

import com.votaciones.back.model.entity.Tblcandidato;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CandidatoDao {

    Tblcandidato createOrUpdateCandidato(Tblcandidato candidato);

    Tblcandidato findTblcandidatoByCveuser(long cveuser);

    Tblcandidato findTblcandidatoByCvecan(long cvecan);

    boolean existTblcandidatoByCveCan(long cveCandidato);

    List<Tblcandidato> findAllByAniocan(int aniocan);

    void deleteCandidatoByCvecan(long cvecan);
}
