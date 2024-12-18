package com.votaciones.back.service.candidate;

import com.votaciones.back.dao.candidato.CandidatoDao;
import com.votaciones.back.dao.institucion.InstitucionDao;
import com.votaciones.back.dao.invalidCan.InvalidCanDao;
import com.votaciones.back.dao.usuario.UsuarioDao;
import com.votaciones.back.model.entity.TblInvlidCandidato;
import com.votaciones.back.model.entity.Tblcandidato;
import com.votaciones.back.model.entity.Tblinst;
import com.votaciones.back.model.entity.Tbluser;
import com.votaciones.back.model.exception.ResourceNotFoundException;
import com.votaciones.back.model.pojos.consume.ConsumeJsonCandidato;
import com.votaciones.back.model.pojos.consume.ConsumeJsonLong;
import com.votaciones.back.model.pojos.consume.ConsumeJsonString;
import com.votaciones.back.model.pojos.response.ResponseJsonCandidato;
import com.votaciones.back.model.pojos.response.ResponseJsonGeneric;
import com.votaciones.back.model.pojos.response.ResponseJsonLongString;
import com.votaciones.back.service.util.ValidUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.*;

@Slf4j
@Service
public class CandidateServiceImp implements CandidateService {

    private final UsuarioDao usuarioDao;
    private final InstitucionDao institucionDao;
    private final CandidatoDao candidatoDao;
    private final InvalidCanDao invalidCanDao;


    public CandidateServiceImp(UsuarioDao usuarioDao, InstitucionDao institucionDao, CandidatoDao candidatoDao, InvalidCanDao invalidCanDao) {
        this.usuarioDao = usuarioDao;
        this.institucionDao = institucionDao;
        this.candidatoDao = candidatoDao;
        this.invalidCanDao = invalidCanDao;
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
    public ResponseJsonLongString CreateOrUpdateInvalidCan(ConsumeJsonString consume){
        ValidUtils.validateConsume(consume);
        ResponseJsonLongString response = new ResponseJsonLongString();
        Long cvecan = 0L;

        Tblcandidato candidato = new Tblcandidato();
        candidato.setAniocan(LocalDateTime.now().getYear());
        candidato.setFechacan(LocalDateTime.now());
        candidato.setVotos(1L);
        candidato.setPlantilla("INVALID");
        candidato.setResumen("INVALID");


        candidato = candidatoDao.createOrUpdateCandidato(candidato);

        if (candidato != null){
            cvecan = candidato.getCvecan();
            createInvalidUer(consume.getKey(),candidato);
        }

        response.setId(cvecan);
        response.setKey("Usuario invalido creado");
        return response;
    }

    @Override
    public ResponseJsonLongString CreateOrUpdateInvalidCan(String key, long cveinst, long cverol){
        ResponseJsonLongString response = new ResponseJsonLongString();
        Long cvecan = 0L;

        Tblcandidato candidato = new Tblcandidato();
        candidato.setAniocan(LocalDateTime.now().getYear());
        candidato.setFechacan(LocalDateTime.now());
        candidato.setVotos(1L);
        candidato.setPlantilla("INVALID");
        candidato.setResumen("INVALID");
        switch ((int) cveinst) {
            case 1:
                candidato.setInst1(1);
                break;
            case 2:
                candidato.setInst2(1);
                break;
            case 3:
                candidato.setInst3(1);
                break;
            default:
                throw new ResourceNotFoundException("No existe la institución con clave: " + cveinst);
        }

        switch ((int) cverol) {
            case 4:
                candidato.setAlumnoCount(1);
                break;
            case 6:
                candidato.setAdminCount(1);
                break;
            case 5:
                candidato.setMaestroCount(1);
                break;
            default:
                throw new ResourceNotFoundException("No existe la institución con clave: " + cveinst);
        }


        Tblcandidato newCan = candidatoDao.createOrUpdateCandidato(candidato);

        if (newCan != null){
            cvecan = newCan.getCvecan();
            createInvalidUer(key,newCan);
        }

        response.setId(cvecan);
        response.setKey("Usuario invalido creado");
        return response;
    }


    private void createInvalidUer(String name, Tblcandidato tblcandidato){
        Set<Tblcandidato> candidatos = new HashSet<>();
        candidatos.add(tblcandidato);

        TblInvlidCandidato candidato = new TblInvlidCandidato();
        candidato.setNameinvalid(name);
        candidato.setCandidaturas(candidatos);

        invalidCanDao.CreateOrUpdateInvalidCan(candidato);
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
    public ResponseJsonGeneric findAllCandidatos() {
        List<Long> candidatoes = candidatoDao.findAllCandidatoesByCvecan();

        List<ResponseJsonCandidato> candidatosResponse = new ArrayList<>();
        for (Long candidato : candidatoes) {
            if (candidatoDao.existTblcandidatoByCveCan(candidato)){
                Tblcandidato tblcandidato = candidatoDao.findTblcandidatoByCvecan(candidato);
                ResponseJsonCandidato candidatoResponse = fillCandidatoResponse(tblcandidato);
                candidatosResponse.add(candidatoResponse);
            }
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
        Long cveuser = candidatoDao.findCveuserByCvecan(candidato.getCvecan());
        if (cveuser != null){
            Tbluser usuario = usuarioDao.findTblUserByCveuser(cveuser);
            if (usuario == null) {
                throw new ResourceNotFoundException("usuario no encontrado");
            }

            response.setName(usuario.getNameusr());
            response.setLastName(usuario.getApeuser());
            response.setCvecan(candidato.getCvecan());
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
        }
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
        if (candidato == null) {
            throw new ResourceNotFoundException("no eciste el candidato");
        }
        response.setPlantilla(candidato.getPlantilla());
        response.setEmail(usuario.getEmailuser());
        response.setInstList(institucionDao.findInstNamesByCveuser(usuario.getCveuser()));
        response.setResumen(candidato.getResumen());
        response.setName(usuario.getNameusr());
        response.setLastName(usuario.getApeuser());

        return response;
    }

}
