package com.votaciones.back.dao.roles.Permission;

import com.votaciones.back.model.entity.Tblpermission;

import java.util.List;

public interface PermissionDao {

    void saveAll(List<Tblpermission> permissions);

    boolean existsTblpermissionByNamePermission(String namePermission);

    Tblpermission createOrUpdateTblpermission(Tblpermission tblpermission);

    Tblpermission findTblpermissionByName(String name);
}
