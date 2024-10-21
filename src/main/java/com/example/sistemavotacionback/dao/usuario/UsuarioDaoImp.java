package com.example.sistemavotacionback.dao.usuario;

import com.example.sistemavotacionback.dao.institucion.InstitucionDao;
import com.example.sistemavotacionback.dao.roles.RolesDao;
import com.example.sistemavotacionback.model.entity.Tbluser;
import com.example.sistemavotacionback.repository.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

@Repository
public class UsuarioDaoImp implements UsuarioDao {

    private final UsuarioRepository usuarioRepository;
    private final RolesDao rolesDao;
    private final InstitucionDao institucionDao;

    public UsuarioDaoImp(UsuarioRepository usuarioRepository, RolesDao rolesDao, InstitucionDao institucionDao) {
        this.usuarioRepository = usuarioRepository;
        this.rolesDao = rolesDao;
        this.institucionDao = institucionDao;
    }


    @Override
    public Tbluser createOrUpdateUsuario(Tbluser tbluser) {
        return usuarioRepository.save(tbluser);
    }

    @Override
    public Tbluser findTblUserByCveuser(long cveuser) {
        return usuarioRepository.findTblUserByCveuser(cveuser);
    }

    @Override
    public Page<Object> findUserByKeyPage(String key, Pageable pageable) {
        return usuarioRepository.findUserByKeyPage(key, pageable);
    }

    @Override
    public boolean existsTbluserByEmailuser(String emailuser) {
        return usuarioRepository.existsTbluserByEmailuser(emailuser);
    }

    @Override
    public boolean existsTbluserByNumcunetauser(String numcunetauser) {
        return usuarioRepository.existsTbluserByNumcunetauser(numcunetauser);
    }

    @Override
    public void deleteTbluserByCveuser(long cveuser) {
        rolesDao.deleteRolByCveUser(cveuser);
        institucionDao.deleteInstitutionByCveUser(cveuser);
        usuarioRepository.deleteByCveuser(cveuser);
    }

    @Override
    public boolean existsTbluserByCveuser(long cveuser) {
        return usuarioRepository.existsTbluserByCveuser(cveuser);
    }
}
