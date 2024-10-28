package com.votaciones.back.dao.usuario;

import com.votaciones.back.model.entity.Tbluser;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface UsuarioDao {

    Tbluser findTbluserByEmailuser(String emailuser);

    Tbluser findTbluserByNumcunetauser(String numcunetauser);

    boolean existsTbluserByEmailuser(String emailuser);

    boolean existsTbluserByNumcunetauser(String numcunetauser);

    boolean existsTbluserByCveuser(long cveuser);

    Tbluser createOrUpdateUsuario(Tbluser tbluser);

    Tbluser findTblUserByCveuser(long cveuser);

    Page<Object> findUserByKeyPage(String key, Pageable pageable);

    void deleteTbluserByCveuser(long cveuser);


}
