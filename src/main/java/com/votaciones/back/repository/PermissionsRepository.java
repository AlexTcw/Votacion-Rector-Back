package com.votaciones.back.repository;

import com.votaciones.back.model.entity.Tblpermission;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PermissionsRepository extends JpaRepository<Tblpermission, Integer> {

    boolean existsTblpermissionByNamePermission(String namePermission);

    Tblpermission findTblpermissionByNamePermission(String name);
}
