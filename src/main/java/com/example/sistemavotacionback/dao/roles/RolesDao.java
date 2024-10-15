package com.example.sistemavotacionback.dao.roles;

import com.example.sistemavotacionback.model.entity.Tblrol;
import com.example.sistemavotacionback.model.entity.Tblusurol;

import java.util.List;

public interface RolesDao {

    boolean existRolByCveRol(long cverol);

    Tblrol findRolByCverol(long cverol);

    Tblusurol CreateOrUpdateUsuRol(Tblusurol tblusurol);

    boolean userHaveRolesByCveuser(long cveuser);

    void deleteRolByCveUser(long cveuser);

    List<String> findRolesNamesByCveuser(long cveuser);
}
