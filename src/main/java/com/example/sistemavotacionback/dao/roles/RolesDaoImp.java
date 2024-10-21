package com.example.sistemavotacionback.dao.roles;

import com.example.sistemavotacionback.model.entity.Tblrol;
import com.example.sistemavotacionback.model.entity.Tblusurol;
import com.example.sistemavotacionback.repository.RolesPorUsuarioRepository;
import com.example.sistemavotacionback.repository.RolesRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class RolesDaoImp implements RolesDao {

    private final RolesRepository rolesRepository;

    private final RolesPorUsuarioRepository rolesPorUsuarioRepository;

    public RolesDaoImp(RolesRepository rolesRepository, RolesPorUsuarioRepository rolesPorUsuarioRepository) {
        this.rolesRepository = rolesRepository;
        this.rolesPorUsuarioRepository = rolesPorUsuarioRepository;
    }


    @Override
    public boolean existRolByCveRol(long cverol) {
        return rolesRepository.existsTblrolByCverol(cverol);
    }

    @Override
    public Tblrol findRolByCverol(long cverol) {
        return rolesRepository.findTblrolByCverol(cverol);
    }

    @Override
    public Tblusurol CreateOrUpdateUsuRol(Tblusurol tblusurol) {
        return rolesPorUsuarioRepository.save(tblusurol);
    }

    @Override
    public boolean userHaveRolesByCveuser(long cveuser) {
        return rolesPorUsuarioRepository.existsByTbluser_Cveuser(cveuser);
    }

    @Override
    @Modifying
    public void deleteRolByCveUser(long cveuser) {
        rolesPorUsuarioRepository.deleteByCveusurol(cveuser);
    }

    @Override
    public List<String> findRolesNamesByCveuser(long cveuser) {
        return rolesRepository.getRolesNamesByCveuser(cveuser);
    }
}
