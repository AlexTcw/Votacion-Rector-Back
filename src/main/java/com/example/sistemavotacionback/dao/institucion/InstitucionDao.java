package com.example.sistemavotacionback.dao.institucion;

import com.example.sistemavotacionback.model.entity.Tblinst;
import com.example.sistemavotacionback.model.entity.Tblusuinst;

import java.util.List;


public interface InstitucionDao {

    boolean existsinstitucionByCveinst(long cveinst);

    Tblinst findInstitutionByCveinst(long id);

    List<String> findInstNamesByCveuser(long cveuser);

    boolean userhaveInstitucionByCveuser(long cveuser);

    void deleteInstitutionByCveUser(long cveuser);

    Tblusuinst createOrUpdateTblusuinst(Tblusuinst tblusuinst);
}
