package com.votaciones.back.dao.rector;

import com.votaciones.back.model.entity.Tblrector;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;

public interface RectorDao {
    Tblrector saveOrUpdateRector(Tblrector rector);

    Tblrector findTblrectorByFechainicio(LocalDateTime fechainicio);

    boolean existsTblrectorByFechainicio(LocalDateTime fechainicio);

    List<Object[]> findRectorDataByCverector(long cverector);
}
