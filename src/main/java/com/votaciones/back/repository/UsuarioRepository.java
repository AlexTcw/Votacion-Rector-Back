package com.votaciones.back.repository;

import com.votaciones.back.model.entity.Tbluser;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface UsuarioRepository extends JpaRepository<Tbluser, Integer> {

    Tbluser findTblUserByCveuser(long cveuser);

    Tbluser findTbluserByEmailuser(String emailuser);

    Tbluser findTbluserByNumcunetauser(String numcunetauser);

    boolean existsTbluserByCveuser(long cveuser);

    boolean existsTbluserByEmailuser(String emailuser);

    boolean existsTbluserByNumcunetauser(String numcunetauser);

    @Query(value = """
        SELECT DISTINCT
            u.cveuser, u.numcunetauser, u.nameusr, u.apeuser, u.emailuser, i.nameinst
        FROM tbluser u
        INNER JOIN usu_inst ui ON ui.cveuser = u.cveuser
        INNER JOIN tblinst i ON i.cveinst = ui.cveinst\s
        INNER JOIN usu_rol ur ON ur.cveuser = u.cveuser
        INNER JOIN tblrol r ON r.cverol = ur.cverol\s
        WHERE\s
            (:key IS NULL
             OR UPPER(REPLACE(u.nameusr, '-', '')) LIKE CONCAT('%', UPPER(REPLACE(:key, '-', '')), '%')
             OR UPPER(REPLACE(u.apeuser, '-', '')) LIKE CONCAT('%', UPPER(REPLACE(:key, '-', '')), '%')
             OR UPPER(REPLACE(u.emailuser, '-', '')) LIKE CONCAT('%', UPPER(REPLACE(:key, '-', '')), '%')
             OR REPLACE(u.numcunetauser, '-', '') LIKE CONCAT('%', REPLACE(:key, '-', ''), '%')
             OR REPLACE(u.cveuser, '-', '') LIKE CONCAT('%', REPLACE(:key, '-', ''))
             OR REPLACE(i.nameinst, '-', '') LIKE CONCAT('%', REPLACE(:key, '-', ''), '%')
            )
        ORDER BY
            IF(REPLACE(u.cveuser, '-', '') = REPLACE(:key, '-', ''), 0, 1),
            u.nameusr, u.apeuser, u.emailuser, u.numcunetauser, u.cveuser, i.nameinst
        """, nativeQuery = true)
    Page<Object> findUserByKeyPage(@Param("key") String key, Pageable pageable);


    @Modifying
    void deleteTbluserByCveuser(long cveuser);

    @Query(value = "SELECT u.cveuser FROM tbluser u", nativeQuery = true)
    List<Long> findAllCveuser();

    boolean existsTbluserByNameusr(String nameUser);

    Tbluser findTbluserByNameusr(String nameUser);

    boolean existsTbluserByApeuser(String apeUser);

    Tbluser findTbluserByApeuser(String apeUser);

    boolean existsTbluserByNameusrAndApeuser(String nameUser, String apeUser);

    Tbluser findTbluserByNameusrAndApeuser(String nameUser, String apeUser);

    @Query(value = "SELECT MAX(u.numcunetauser) from tbluser u", nativeQuery = true)
    String findLastNumcuetauser();


}
