package com.votaciones.back.dao.roles;

import com.votaciones.back.model.entity.Tblrol;

import java.util.List;

public interface RolesDao {

    boolean existRolByCveRol(long cverol);

    boolean existsTblrolByNamerol(String namerol);

    Tblrol findRolByCverol(long cverol);

    Tblrol createOrUpdateRol(Tblrol tblrol);

    void saveAllRol(List<Tblrol> roles);

    List<String> findRolesNamesByCveuser(long cveuser);

    Tblrol findTblrolByNamerol(String name);
}
