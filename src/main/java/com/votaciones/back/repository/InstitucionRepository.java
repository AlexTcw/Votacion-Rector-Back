package com.votaciones.back.repository;

import com.votaciones.back.model.entity.Tblinst;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface InstitucionRepository extends JpaRepository<Tblinst, Integer> {

    boolean existsTblinstByCveinst(long cveinst);

    Tblinst findTblinstByCveinst(long cveinst);

    @Query(value = """
            SELECT i.nameinst
            	FROM tblinst i
            	inner join usu_inst ui ON ui.cveinst = i.cveinst\s
            	INNER join tbluser u on u.cveuser = ui.cveuser\s
            	where u.cveuser = :cveuser""",nativeQuery = true)
    List<String>getInstNamesByCveuser(@Param("cveuser") long cveuser);
}
