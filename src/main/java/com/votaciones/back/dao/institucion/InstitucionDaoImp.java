package com.votaciones.back.dao.institucion;

import com.votaciones.back.model.entity.Tblinst;
import com.votaciones.back.repository.InstitucionRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class InstitucionDaoImp implements InstitucionDao{
    private final InstitucionRepository institucionRepository;

    public InstitucionDaoImp(InstitucionRepository institucionRepository) {
        this.institucionRepository = institucionRepository;
    }

    @Override
    public boolean existsinstitucionByCveinst(long cveinst) {
        return institucionRepository.existsTblinstByCveinst(cveinst);
    }

    @Override
    public Tblinst findInstitutionByCveinst(long cveinst) {
        return institucionRepository.findTblinstByCveinst(cveinst);
    }

    @Override
    public List<String> findInstNamesByCveuser(long cveuser) {
        return institucionRepository.getInstNamesByCveuser(cveuser);
    }
}
