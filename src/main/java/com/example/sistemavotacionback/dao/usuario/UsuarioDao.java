package com.example.sistemavotacionback.dao.usuario;

import com.example.sistemavotacionback.model.entity.Tbluser;

public interface UsuarioDao {

    boolean existsTbluserByCveuser(long cveuser);

    Tbluser createOrUpdateUsuario(Tbluser tbluser);

    Tbluser findTblUserByCveuser(long cveuser);


}
