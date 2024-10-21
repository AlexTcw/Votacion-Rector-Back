package com.example.sistemavotacionback.repository;

import com.example.sistemavotacionback.model.entity.Tbluser;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface UsuarioRepository extends JpaRepository<Tbluser, Integer> {

    Tbluser findTblUserByCveuser(long cveuser);

    boolean existsTbluserByCveuser(long cveuser);

    boolean existsTbluserByEmailuser(String emailuser);

    boolean existsTbluserByNumcunetauser(String numcunetauser);

    @Query(value = """
        SELECT DISTINCT u.cveuser, u.numcunetauser, u.nameusr, u.apeuser, u.emailuser
        FROM tbluser u
        INNER JOIN tblusuinst ui ON u.cveuser = ui.cveuser
        INNER JOIN tblusurol ur ON u.cveuser = ur.cveuser
        WHERE (:key IS NULL
               OR (UPPER(REPLACE(u.nameusr, '-', '')) LIKE CONCAT('%', UPPER(REPLACE(:key, '-', '')), '%')
                   OR UPPER(REPLACE(u.apeuser, '-', '')) LIKE CONCAT('%', UPPER(REPLACE(:key, '-', '')), '%')
                   OR UPPER(REPLACE(u.emailuser, '-', '')) LIKE CONCAT('%', UPPER(REPLACE(:key, '-', '')), '%')
                   OR REPLACE(u.numcunetauser, '-', '') LIKE CONCAT('%', REPLACE(:key, '-', ''), '%')
                   OR REPLACE(u.cveuser, '-', '') LIKE CONCAT('%', REPLACE(:key, '-', ''), '%')))
        ORDER BY
            IF(REPLACE(u.cveuser, '-', '') = REPLACE(:key, '-', ''), 0, 1),
            u.nameusr, u.apeuser, u.emailuser
        """, nativeQuery = true)
    Page<Object> findUserByKeyPage(@Param("key") String key, Pageable pageable);


    @Modifying
    void deleteByCveuser(long cveuser);
}
