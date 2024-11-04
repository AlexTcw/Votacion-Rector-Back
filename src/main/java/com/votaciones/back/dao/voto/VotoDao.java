package com.votaciones.back.dao.voto;

import com.votaciones.back.model.entity.Tblvoto;

public interface VotoDao {

    Tblvoto createORUpdateVoto(Tblvoto voto);
}
