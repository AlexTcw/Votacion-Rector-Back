package com.votaciones.back.dao.roles.Permission;

import com.votaciones.back.model.entity.Tblpermission;
import com.votaciones.back.repository.PermissionsRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class PermissionDaoImp implements PermissionDao {

    private final PermissionsRepository permissionsRepository;

    public PermissionDaoImp(PermissionsRepository permissionsRepository) {
        this.permissionsRepository = permissionsRepository;
    }


    @Override
    public void saveAll(List<Tblpermission> permissions) {
        permissionsRepository.saveAll(permissions);
    }

    @Override
    public boolean existsTblpermissionByNamePermission(String namePermission) {
        return permissionsRepository.existsTblpermissionByNamePermission(namePermission);
    }

    @Override
    public Tblpermission createOrUpdateTblpermission(Tblpermission tblpermission) {
        return permissionsRepository.save(tblpermission);
    }

    @Override
    public Tblpermission findTblpermissionByName(String name) {
        return permissionsRepository.findTblpermissionByNamePermission(name);
    }
}
