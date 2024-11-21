package com.votaciones.back.repository;

import com.votaciones.back.model.entity.Tblrector;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;

public interface RectorRepository extends JpaRepository<Tblrector, Integer> {

    Tblrector findTblrectorByFechainicio(LocalDateTime fechainicio);

    boolean existsTblrectorByFechainicio(LocalDateTime fechainicio);

    @Query(value = """
            SELECT u.cveuser, c.cvecan, u.nameusr, u.apeuser, u.emailuser, i.nameinst, c.plantilla, c.resumen\s
            FROM tblrector r
            INNER JOIN tblcandidato c ON c.cvecan = r.cvecan
            INNER JOIN usu_can uc ON uc.cvecan = c.cvecan
            INNER JOIN tbluser u ON u.cveuser = uc.cveuser
            INNER JOIN usu_inst ui ON ui.cveuser = u.cveuser
            INNER JOIN tblinst i ON i.cveinst = (
                SELECT MAX(sub_ui.cveinst)
                FROM usu_inst sub_ui
                WHERE sub_ui.cveuser = u.cveuser
            )
            WHERE r.cverector = :cverector""",nativeQuery = true)
    List<Object[]> findRectorDataByCverector(@Param("cverector") long cverector);
}
