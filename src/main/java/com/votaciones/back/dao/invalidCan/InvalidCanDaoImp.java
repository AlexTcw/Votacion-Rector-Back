package com.votaciones.back.dao.invalidCan;

import com.votaciones.back.model.entity.TblInvlidCandidato;
import com.votaciones.back.repository.InvalidCanRepository;
import org.springframework.stereotype.Repository;

@Repository
public class InvalidCanDaoImp implements InvalidCanDao{

    private final InvalidCanRepository invalidCanRepository;

    public InvalidCanDaoImp(InvalidCanRepository invalidCanRepository) {
        this.invalidCanRepository = invalidCanRepository;
    }

    @Override
    public TblInvlidCandidato CreateOrUpdateInvalidCan(TblInvlidCandidato tblInvlidCandidato) {
        return invalidCanRepository.save(tblInvlidCandidato);
    }
}
