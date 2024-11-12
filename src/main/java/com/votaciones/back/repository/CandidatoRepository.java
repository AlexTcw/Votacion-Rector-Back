package com.votaciones.back.repository;

import com.votaciones.back.model.entity.Tblcandidato;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface CandidatoRepository extends JpaRepository<Tblcandidato, Integer> {


    @Query(value = "SELECT c.* from tblcandidato c \n" +
            "inner join usu_can uc on c.cvecan = uc.cvecan\n" +
            "inner join tbluser u on u.cveuser = uc.cveuser \n" +
            "WHERE u.cveuser = :cveuser", nativeQuery = true)
    Tblcandidato findTblcandidatoByCveuser(@Param("cveuser") long cveuser);

    boolean existsTblcandidatoByCvecan(long cveCandidato);

    Tblcandidato findTblcandidatoByCvecan(long cvecan);

    List<Tblcandidato> findAllByAniocan(int aniocan);

    void deleteTblcandidatoByCvecan(long cvecan);

    @Query(value = "SELECT c.cvecan FROM tblcandidato c",nativeQuery = true)
    List<Long>findAllCandidatoesByCvecan();

    @Query(value = "SELECT u.cveuser\n" +
            "FROM tbluser u\n" +
            "INNER JOIN usu_can uc ON uc.cveuser = u.cveuser\n" +
            "INNER JOIN tblcandidato c ON c.cvecan = uc.cvecan\n" +
            "WHERE c.cvecan = :cvecan", nativeQuery = true)
    Long findCveuserByCvecan(@Param("cvecan") Long cvecan);

    boolean existsTblcandidatoByPlantilla(String plantilla);

    Tblcandidato findTblcandidatoByPlantilla(String plantilla);
    
}
