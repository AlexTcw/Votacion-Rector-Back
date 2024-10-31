package com.votaciones.back.service.candidate;

import com.votaciones.back.dao.candidato.CandidatoDao;
import com.votaciones.back.dao.institucion.InstitucionDao;
import com.votaciones.back.dao.usuario.UsuarioDao;
import com.votaciones.back.model.entity.Tblcandidato;
import com.votaciones.back.model.entity.Tbluser;
import com.votaciones.back.model.exception.ResourceNotFoundException;
import com.votaciones.back.model.pojos.consume.ConsumeJsonCandidato;
import com.votaciones.back.model.pojos.response.ResponseJsonCandidato;
import com.votaciones.back.service.util.ValidUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Set;

@Slf4j
@Service
public class CandidateServiceImp implements CantidateService{

    private final UsuarioDao usuarioDao;

    private final InstitucionDao institucionDao;
    private final CandidatoDao candidatoDao;

    public CandidateServiceImp(UsuarioDao usuarioDao, InstitucionDao institucionDao, CandidatoDao candidatoDao) {
        this.usuarioDao = usuarioDao;
        this.institucionDao = institucionDao;
        this.candidatoDao = candidatoDao;
    }

    /*create update cantidates*/
    @Override
    public ResponseJsonCandidato createOrUpdateCandidato(ConsumeJsonCandidato consume) {
        ValidUtils.validateConsume(consume);
        ResponseJsonCandidato response = new ResponseJsonCandidato();

        if (!usuarioDao.existsTbluserByCveuser(consume.getCveuser())) {
            throw new ResourceNotFoundException("No existe el usuario con clave " + consume.getCveuser());
        }
        Tbluser usuario = usuarioDao.findTblUserByCveuser(consume.getCveuser());

        Set<Tblcandidato> candidatoSet = usuario.getCandidaturas();
        candidatoSet.add(fillCandidato(consume));

        usuario.setCandidaturas(candidatoSet);

        Tbluser newUser = usuarioDao.createOrUpdateUsuario(usuario);
        log.info("guardando usuario");
        if (newUser != null){
            Tblcandidato candidato = candidatoDao.findTblcandidatoByCveuser(newUser.getCveuser());
            return createResponse(newUser);
        }
        throw new RuntimeException("Hubo un problema al guardar al candidato");
    }

    private Tblcandidato fillCandidato(ConsumeJsonCandidato consume) {
        Tblcandidato candidato = new Tblcandidato();
        candidato.setPlantilla(consume.getPlantilla());
        candidato.setResumen(consume.getResumen());
        candidato.setVotos(0L);
        candidato.setFechacan(LocalDateTime.now());
        candidato.setAniocan(LocalDateTime.now().getYear());

        candidatoDao.createOrUpdateCandidato(candidato);

        return candidato;
    }

    private ResponseJsonCandidato createResponse(Tbluser usuario) {
        ResponseJsonCandidato response = new ResponseJsonCandidato();
        Tblcandidato candidato = usuario.getCandidaturas().stream().findFirst().orElse(null);

        response.setCveuser(usuario.getCveuser());
        //response.setCvecan(candidato.getCvecan());
        assert candidato != null;
        response.setPlantilla(candidato.getPlantilla());
        response.setEmail(usuario.getEmailuser());
        response.setInstList(institucionDao.findInstNamesByCveuser(usuario.getCveuser()));
        response.setResumen(candidato.getResumen());
        response.setName(usuario.getNameusr());
        response.setLastName(usuario.getApeuser());

        return response;
    }

}
