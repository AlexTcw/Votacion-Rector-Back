package com.votaciones.back.dao.voto;

import com.votaciones.back.model.entity.Tblvoto;
import com.votaciones.back.repository.VotoRepository;
import org.springframework.stereotype.Repository;

@Repository
public class VotoDaoImp implements VotoDao{

    private final VotoRepository votoRepository;

    public VotoDaoImp(VotoRepository votoRepository) {
        this.votoRepository = votoRepository;
    }

    @Override
    public Tblvoto createORUpdateVoto(Tblvoto voto) {
        return votoRepository.save(voto);
    }
}
