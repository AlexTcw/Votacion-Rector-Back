package com.example.sistemavotacionback.repository;

import com.example.sistemavotacionback.model.entity.Tblrol;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface RolesRepository extends JpaRepository<Tblrol,Integer> {

    boolean existsTblrolByCverol(long cverol);

    Tblrol findTblrolByCverol(long cverol);

    @Query(value = """
            SELECT r.namerol\s
            from tblrol r
            inner join tblusurol u on r.cverol = u.cverol\s
            WHERE u.cveuser = :cveuser""", nativeQuery = true)
    List<String> getRolesNamesByCveuser(@Param("cveuser") long cveuser);
}
