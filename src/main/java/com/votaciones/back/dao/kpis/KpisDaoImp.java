package com.votaciones.back.dao.kpis;

import com.votaciones.back.repository.CandidatoRepository;
import com.votaciones.back.repository.UsuarioRepository;
import com.votaciones.back.repository.VotoRepository;
import org.springframework.stereotype.Repository;

@Repository
public class KpisDaoImp implements KpisDao{

    private final CandidatoRepository candidatoRepository;
    private final UsuarioRepository usuarioRepository;
    private final VotoRepository votoRepository;


    public KpisDaoImp(CandidatoRepository candidatoRepository, UsuarioRepository usuarioRepository, VotoRepository votoRepository) {
        this.candidatoRepository = candidatoRepository;
        this.usuarioRepository = usuarioRepository;
        this.votoRepository = votoRepository;
    }
}
