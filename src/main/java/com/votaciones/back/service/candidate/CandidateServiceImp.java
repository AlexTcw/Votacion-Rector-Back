package com.votaciones.back.service.candidate;

import com.votaciones.back.dao.candidato.CandidatoDao;
import com.votaciones.back.dao.institucion.InstitucionDao;
import com.votaciones.back.dao.usuario.UsuarioDao;
import com.votaciones.back.model.entity.Tblcandidato;
import com.votaciones.back.model.entity.Tblinst;
import com.votaciones.back.model.entity.Tbluser;
import com.votaciones.back.model.exception.ResourceNotFoundException;
import com.votaciones.back.model.pojos.consume.ConsumeJsonCandidato;
import com.votaciones.back.model.pojos.consume.ConsumeJsonLong;
import com.votaciones.back.model.pojos.consume.ConsumeJsonString;
import com.votaciones.back.model.pojos.response.ResponseJsonCandidato;
import com.votaciones.back.model.pojos.response.ResponseJsonGeneric;
import com.votaciones.back.service.util.ValidUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.Year;
import java.util.*;

@Slf4j
@Service
public class CandidateServiceImp implements cantidateService {

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
            candidatoDao.findTblcandidatoByCveuser(newUser.getCveuser());
            return createResponse(newUser);
        }
        throw new RuntimeException("Hubo un problema al guardar al candidato");
    }

    @Override
    public void deleteCandidatoByCvecan(ConsumeJsonLong consume){
        ValidUtils.validateConsume(consume);

        if (!candidatoDao.existTblcandidatoByCveCan(consume.getId())){
            throw new ResourceNotFoundException("No existe el usuario con clave " + consume.getId());
        }

        candidatoDao.deleteCandidatoByCvecan(consume.getId());
    }

    @Override
    public ResponseJsonGeneric findAllCandidatos(ConsumeJsonString consume) {
        String aniocandidato = consume.getKey();
        int anio = Year.now().getValue();
        if (aniocandidato != null) {
            anio = Integer.parseInt(aniocandidato);
        }

        List<Tblcandidato> candidatoes = candidatoDao.findAllByAniocan(anio);

        List<ResponseJsonCandidato> candidatosResponse = new ArrayList<>();
        for (Tblcandidato candidato : candidatoes) {
            ResponseJsonCandidato candidatoResponse = fillCandidatoResponse(candidato);
            candidatosResponse.add(candidatoResponse);
        }

        ResponseJsonGeneric response = new ResponseJsonGeneric();
        Map<String, Object> responseMap = new LinkedHashMap<>();
        responseMap.put("candidatos", candidatosResponse);
        response.setData(responseMap);

        return response;
    }

    @Override
    public ResponseJsonCandidato findCandidatoByCvecan(ConsumeJsonLong consume){
        ValidUtils.validateConsume(consume);

        Tblcandidato candidato = candidatoDao.findTblcandidatoByCvecan(consume.getId());

        return fillCandidatoResponse(candidato);
    }

    private ResponseJsonCandidato fillCandidatoResponse(Tblcandidato candidato) {
        ResponseJsonCandidato response = new ResponseJsonCandidato();
        Tbluser usuario = candidato.getUsuarios().stream().findFirst().orElse(null);
        response.setCvecan(candidato.getCvecan());
        assert usuario != null;
        response.setName(usuario.getNameusr());
        response.setLastName(usuario.getApeuser());
        response.setResumen(candidato.getResumen());
        response.setEmail(usuario.getEmailuser());
        response.setCveuser(usuario.getCveuser());
        response.setPlantilla(candidato.getPlantilla());

        var instUsuarioSet = usuario.getInstituciones();
        List<String> instUsuarioList = new ArrayList<>();
        for (Tblinst instituciones: instUsuarioSet) {
            instUsuarioList.add(instituciones.getNameinst());
        }

        response.setInstList(instUsuarioList);

        return response;
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
