package com.example.sistemavotacionback.repository;

import com.example.sistemavotacionback.model.entity.Tblinst;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface InstitucionRepository extends JpaRepository<Tblinst, Integer> {

    boolean existsTblinstByCveinst(long cveinst);

    Tblinst findTblinstByCveinst(long cveinst);

    @Query(value = """
            SELECT i.nameinst
            FROM tblinst i inner join tblusuinst u on i.cveinst = u.cveinst
            where u.cveuser = :cveuser""",nativeQuery = true)
    List<String>getInstNamesByCveuser(@Param("cveuser") long cveuser);
}
