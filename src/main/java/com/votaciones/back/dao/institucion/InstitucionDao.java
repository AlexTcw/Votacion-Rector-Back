package com.votaciones.back.dao.institucion;

import com.votaciones.back.model.entity.Tblinst;

import java.util.List;


public interface InstitucionDao {

    boolean existsinstitucionByCveinst(long cveinst);

    Tblinst findInstitutionByCveinst(long id);

    List<String> findInstNamesByCveuser(long cveuser);
}
