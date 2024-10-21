package com.example.sistemavotacionback.dao.usuario;

import com.example.sistemavotacionback.model.entity.Tbluser;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface UsuarioDao {

    boolean existsTbluserByCveuser(long cveuser);

    Tbluser createOrUpdateUsuario(Tbluser tbluser);

    Tbluser findTblUserByCveuser(long cveuser);

    Page<Object> findUserByKeyPage(String key, Pageable pageable);

    boolean existsTbluserByEmailuser(String emailuser);

    boolean existsTbluserByNumcunetauser(String numcunetauser);

    void deleteTbluserByCveuser(long cveuser);


}
