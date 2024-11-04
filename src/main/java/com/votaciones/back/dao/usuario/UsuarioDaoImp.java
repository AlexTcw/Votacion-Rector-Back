package com.votaciones.back.dao.usuario;
import com.votaciones.back.model.entity.Tbluser;
import com.votaciones.back.repository.UsuarioRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Set;

@Repository
public class UsuarioDaoImp implements UsuarioDao {

    private final UsuarioRepository usuarioRepository;

    public UsuarioDaoImp(UsuarioRepository usuarioRepository) {
        this.usuarioRepository = usuarioRepository;
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
        usuarioRepository.deleteTbluserByCveuser(cveuser);
    }

    @Override
    public boolean existsTbluserByCveuser(long cveuser) {
        return usuarioRepository.existsTbluserByCveuser(cveuser);
    }

    @Override
    public Tbluser findTbluserByEmailuser(String emailuser) {
        return usuarioRepository.findTbluserByEmailuser(emailuser);
    }

    @Override
    public Tbluser findTbluserByNumcunetauser(String numcunetauser) {
        return usuarioRepository.findTbluserByNumcunetauser(numcunetauser);
    }

    @Override
    public void saveall(Set<Tbluser> users) {
        usuarioRepository.saveAll(users);
    }

    @Override
    public List<Tbluser> findAllUsers(){
       return usuarioRepository.findAll();
    }

    @Override
    public List<Long>findAllUsersCve(){
        return usuarioRepository.findAllCveuser();
    }
}
