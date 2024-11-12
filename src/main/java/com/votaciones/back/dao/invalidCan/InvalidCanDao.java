package com.votaciones.back.dao.invalidCan;

import com.votaciones.back.model.entity.TblInvlidCandidato;

public interface InvalidCanDao {
    TblInvlidCandidato CreateOrUpdateInvalidCan(TblInvlidCandidato tblInvlidCandidato);
}
