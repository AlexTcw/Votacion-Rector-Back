package com.votaciones.back.dao.roles;

import com.votaciones.back.model.entity.Tblrol;
import com.votaciones.back.repository.RolesRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class RolesDaoImp implements RolesDao {

    private final RolesRepository rolesRepository;

    public RolesDaoImp(RolesRepository rolesRepository) {
        this.rolesRepository = rolesRepository;
    }

    @Override
    public boolean existRolByCveRol(long cverol) {
        return rolesRepository.existsTblrolByCverol(cverol);
    }

    @Override
    public boolean existsTblrolByNamerol(String namerol) {
        return rolesRepository.existsTblrolByNamerol(namerol);
    }

    @Override
    public Tblrol findRolByCverol(long cverol) {
        return rolesRepository.findTblrolByCverol(cverol);
    }

    @Override
    public Tblrol createOrUpdateRol(Tblrol tblrol) {
        return rolesRepository.save(tblrol);
    }

    @Override
    public void saveAllRol(List<Tblrol> roles) {
        rolesRepository.saveAll(roles);
    }

//    @Override
//    public boolean userHaveRolesByCveuser(long cveuser) {
//        return rolesPorUsuarioRepository.existsByTbluser_Cveuser(cveuser);
//    }
//
//    @Override
//    @Modifying
//    public void deleteRolByCveUser(long cveuser) {
//        rolesPorUsuarioRepository.deleteByCveusurol(cveuser);
//    }

    @Override
    public List<String> findRolesNamesByCveuser(long cveuser) {
        return rolesRepository.getRolesNamesByCveuser(cveuser);
    }

    @Override
    public Tblrol findTblrolByNamerol(String name) {
        return rolesRepository.findTblrolByNamerol(name);
    }
}
