package com.votaciones.back.dao.candidato;

import com.votaciones.back.model.entity.Tblcandidato;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface CandidatoDao {

    Tblcandidato createOrUpdateCandidato(Tblcandidato candidato);

    Tblcandidato findTblcandidatoByCveuser(long cveuser);
}
