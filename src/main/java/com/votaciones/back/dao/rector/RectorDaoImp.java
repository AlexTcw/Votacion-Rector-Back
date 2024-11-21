package com.votaciones.back.dao.rector;

import com.votaciones.back.model.entity.Tblrector;
import com.votaciones.back.repository.RectorRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class RectorDaoImp implements RectorDao {

    private final RectorRepository rectorRepository;

    @Override
    public Tblrector saveOrUpdateRector(Tblrector rector){
        return rectorRepository.save(rector);
    }

    @Override
    public Tblrector findTblrectorByFechainicio(LocalDateTime fechainicio) {
        return rectorRepository.findTblrectorByFechainicio(fechainicio);
    }

    @Override
    public boolean existsTblrectorByFechainicio(LocalDateTime fechainicio) {
        return rectorRepository.existsTblrectorByFechainicio(fechainicio);
    }

    @Override
    public List<Object[]> findRectorDataByCverector(long cverector) {
        return rectorRepository.findRectorDataByCverector(cverector);
    }
}
