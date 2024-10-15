package com.example.sistemavotacionback.dao.institucion;

import com.example.sistemavotacionback.model.entity.Tblinst;
import com.example.sistemavotacionback.model.entity.Tblusuinst;
import com.example.sistemavotacionback.repository.InstPorUsuarioRepository;
import com.example.sistemavotacionback.repository.InstitucionRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class InstitucionDaoImp implements InstitucionDao{
    private final InstitucionRepository institucionRepository;
    private final InstPorUsuarioRepository instPorUsuarioRepository;

    public InstitucionDaoImp(InstitucionRepository institucionRepository, InstPorUsuarioRepository instPorUsuarioRepository) {
        this.institucionRepository = institucionRepository;
        this.instPorUsuarioRepository = instPorUsuarioRepository;
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

    @Override
    public boolean userhaveInstitucionByCveuser(long cveuser) {
        return instPorUsuarioRepository.existsByTbluser_Cveuser(cveuser);
    }


    @Override
    public void deleteInstitutionByCveUser(long cveuser) {
        instPorUsuarioRepository.deleteByCveusuinst(cveuser);
    }

    @Override
    public Tblusuinst createOrUpdateTblusuinst(Tblusuinst tblusuinst) {
        return instPorUsuarioRepository.save(tblusuinst);
    }
}
