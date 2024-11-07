package com.votaciones.back.repository;

import com.votaciones.back.model.entity.Tblcandidato;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface CandidatoRepository extends JpaRepository<Tblcandidato, Integer> {


    @Query(value = """
            SELECT tc.* from tblcandidato tc
               inner join usu_can uc on tc.cvecan = usu_can.cvecan\s
               inner join tbluser tu on tu.cveuser = usu_can.cveuser\s
               where tu.cveuser = :cveuser""", nativeQuery = true)
    Tblcandidato findTblcandidatoByCveuser(@Param("cveuser") long cveuser);

    boolean existsTblcandidatoByCvecan(long cveCandidato);

    Tblcandidato findTblcandidatoByCvecan(long cvecan);

    List<Tblcandidato> findAllByAniocan(int aniocan);

    void deleteTblcandidatoByCvecan(long cvecan);

    
}
