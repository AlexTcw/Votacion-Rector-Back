package com.votaciones.back.repository;

import com.votaciones.back.model.entity.Tblrol;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface RolesRepository extends JpaRepository<Tblrol,Integer> {

    boolean existsTblrolByCverol(long cverol);

    boolean existsTblrolByNamerol(String namerol);

    Tblrol findTblrolByCverol(long cverol);

    @Query(value = """
            SELECT r.namerol
            	from tblrol r
            	inner join usu_rol ur ON ur.cverol = r.cverol
            	inner join tbluser u on ur.cveuser = u.cveuser
            	WHERE u.cveuser = :cveuser""", nativeQuery = true)
    List<String> getRolesNamesByCveuser(@Param("cveuser") long cveuser);

    Tblrol findTblrolByNamerol(String name);
}
