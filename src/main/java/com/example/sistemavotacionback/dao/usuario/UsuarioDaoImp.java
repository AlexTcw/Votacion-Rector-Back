package com.example.sistemavotacionback.dao.usuario;

import com.example.sistemavotacionback.model.entity.Tbluser;
import com.example.sistemavotacionback.repository.UsuarioRepository;
import org.springframework.stereotype.Repository;

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
    public boolean existsTbluserByCveuser(long cveuser) {
        return usuarioRepository.existsTbluserByCveuser(cveuser);
    }
}
