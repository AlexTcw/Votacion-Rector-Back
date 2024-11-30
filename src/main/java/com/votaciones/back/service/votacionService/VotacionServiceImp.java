package com.votaciones.back.service.votacionService;

import com.votaciones.back.dao.candidato.CandidatoDao;
import com.votaciones.back.dao.rector.RectorDao;
import com.votaciones.back.dao.usuario.UsuarioDao;
import com.votaciones.back.dao.voto.VotoDao;
import com.votaciones.back.model.entity.*;
import com.votaciones.back.model.exception.ResourceNotFoundException;
import com.votaciones.back.model.pojos.consume.ConsumeJsonLongLong;
import com.votaciones.back.model.pojos.consume.ConsumeJsonLongString;
import com.votaciones.back.model.pojos.consume.ConsumeJsonString;
import com.votaciones.back.model.pojos.response.ResponseJsonCandidato;
import com.votaciones.back.model.pojos.response.ResponseJsonString;
import com.votaciones.back.service.candidate.CandidateService;
import com.votaciones.back.service.util.ValidUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.Year;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Slf4j
@Service
@RequiredArgsConstructor
public class VotacionServiceImp implements VotacionService {

    private final UsuarioDao usuarioDao;
    private final CandidatoDao candidatoDao;
    private final VotoDao votoDao;
    private final CandidateService candidateService;
    private final RectorDao rectorDao;

    @Override
    public ResponseJsonString validateAndSetInvalidWithKey(ConsumeJsonLongString consume){
        ValidUtils.validateConsume(consume);
        ResponseJsonString response = new ResponseJsonString();
        Long cveuser = consume.getId();
        Tblcandidato candidato = null;

        if (!usuarioDao.existsTbluserByCveuser(cveuser)) {
            throw new ResourceNotFoundException("Usuario no encontrado: " + cveuser);
        }

        Tbluser votante = usuarioDao.findTblUserByCveuser(cveuser);
        if (validVotacionAnual(votante)){
            throw new AccessDeniedException("El usuario "+cveuser + " ya voto");
        }

        var usuinstList = votante.getInstituciones();
        long cveentint = 0;
        for(var usuinst: usuinstList){
            cveentint = usuinst.getCveinst();
        }

        var usurolList = votante.getRoles();
        long cverol = 0;
        for (var usurol: usurolList){
            if (usurol.getCverol() != 8 && usurol.getCverol() != 7){
                cverol = usurol.getCverol();
            }
        }

        log.info("usuario votante : {}", votante.getNameusr());

        Tbluser usuario = validateByKeyUser(consume.getKey());
        if (usuario != null) {
            log.warn("votando por primer filtro {}",usuario.getCveuser());
            candidato = candidatoDao.findTblcandidatoByCveuser(usuario.getCveuser());
            response.setKey("voto correctamente por el candidato: " + candidato.getCvecan());
        } else if (candidatoDao.existCandidatoByPlantilla(consume.getKey())) {
            candidato = candidatoDao.findTblcandidatoByPlantilla(consume.getKey());
            response.setKey("voto correctamente por el candidato: " + candidato.getCvecan());
        } else {
            long cveinstvot = 0;
            long cverolvot = 0;
            var instSet = votante.getInstituciones();
            if (!instSet.isEmpty()) {
                var inst = instSet.iterator().next(); // Obtiene el primer elemento
                cveinstvot = inst.getCveinst(); // Asigna su valor a cveinstvot
            }
            var rolSet = votante.getRoles();
            for (var rol: rolSet){
                if (rol.getCverol() != 8 && rol.getCverol() != 7){
                    cverolvot = rol.getCverol();
                }
            }

            candidateService.CreateOrUpdateInvalidCan(consume.getKey(),cveinstvot,cverolvot);
            response.setKey("usuario invalido creado");
        }

        if (candidato != null) {findAndSetVoto(candidato.getCvecan(), cveentint,cverol);}

        votante.setVotos(addVotoUsuario());
        usuarioDao.createOrUpdateUsuario(votante);
        return response;
    }

    private Tbluser validateByKeyUser(String key){

        log.info("key {}",key);

        key = key.trim().replaceAll("\\s+", "");  // Eliminar espacios extras
        String[] parts = key.split(" ");

        if (usuarioDao.existsTbluserByEmailuser(key)){
            return usuarioDao.findTbluserByEmailuser(key);
        } else if (usuarioDao.existsTbluserByNumcunetauser(key)) {
            return usuarioDao.findTbluserByNumcunetauser(key);
        } else if (usuarioDao.existTbluserByNameUser(key)) {
            log.warn("votando por: nombre de usuario candidato");
            return usuarioDao.findTbluserByNameUser(key);
        } else if (usuarioDao.existTbluserByApeUser(key)) {
            return usuarioDao.findTbluserByApeUser(key);
        } else if (parts.length == 2) {
            String nombre = parts[0];
            String apellido = parts[1];
            if (usuarioDao.existTbluserByNameAndApeUser(nombre, apellido)) {
                return usuarioDao.findTbluserByNameAndApeUser(nombre,apellido);
            }
        } else if (parts.length == 3) {
            //nombre apellido apellido
            String nombre = parts[0];
            String apellido = parts[1] + " " + parts[2];
            if (usuarioDao.existTbluserByNameAndApeUser(nombre, apellido)) {
                return usuarioDao.findTbluserByNameAndApeUser(nombre,apellido);
            }
            //nombre nombre apellido
            String nombrelargo = parts[0] + " " + parts[1];
            String apellidocorto = parts[2];
            if (usuarioDao.existTbluserByNameAndApeUser(nombrelargo, apellidocorto)){
                return usuarioDao.findTbluserByNameAndApeUser(nombrelargo,apellidocorto);
            }
        } else if (parts.length == 4) {
            String nombre = parts[0]+ " " + parts[1];
            String apellido = parts[2] + " " + parts[3];
            if (usuarioDao.existTbluserByNameAndApeUser(nombre, apellido)) {
                return usuarioDao.findTbluserByNameAndApeUser(nombre,apellido);
            }
        }
        return null;
    }

    @Transactional
    @Override
    public ResponseJsonCandidato setWinner(){
        Tblcandidato winner = candidatoDao.findTblcandidatoWithMaxVotos();
        Tblrector rector;

        if (rectorDao.existsTblrectorByFechainicio(LocalDateTime.now())){
            rector = rectorDao.findTblrectorByFechainicio(LocalDateTime.now());
        }

        else rector = new Tblrector();
        rector.setCandidato(winner);
        rector.setDescripcion(winner.getResumen());
        rector.setFechainicio(LocalDateTime.now());

        Tblrector newRector = rectorDao.saveOrUpdateRector(rector);
        List<Object[]> dataRector = rectorDao.findRectorDataByCverector(newRector.getCverector());

        ResponseJsonCandidato response = new ResponseJsonCandidato();

        Object[] row = dataRector.getFirst(); // Obtienes la primera fila (si existe)

        response.setCveuser(((Number) row[0]).longValue());
        response.setCvecan(((Number) row[1]).longValue());
        response.setName((String) row[2]);
        response.setLastName((String) row[3]);
        response.setEmail((String) row[4]);
        response.setInstList(Collections.singletonList((String) row[5])); // Asumiendo que hay solo una institución
        response.setPlantilla((String) row[6]);
        response.setResumen((String) row[7]);

        return response;
    }

    @Override
    @Transactional
    public ResponseJsonString validateAndSetVoto(ConsumeJsonLongLong consume){
        ValidUtils.validateConsume(consume);
        ResponseJsonString response = new ResponseJsonString();
        /*Primero validamos al usuario*/
        long cveuser = consume.getValue1();
        long cvecandidato = consume.getValue2();

        if (!usuarioDao.existsTbluserByCveuser(cveuser)){
            throw new ResourceNotFoundException("Usuario no encontrado con clave" + cveuser);
        }

        /*Recuperamos al usuario*/
        Tbluser usuario = usuarioDao.findTblUserByCveuser(cveuser);

        /*Validamos si ya voto*/
        if (validVotacionAnual(usuario)){
            throw new AccessDeniedException("El usuario "+cveuser + " ya voto");
        }

        /*Registra un voto pero no porquien voto*/
        usuario.setVotos(addVotoUsuario());

        var usuinstList = usuario.getInstituciones();
        var usurolList = usuario.getRoles();
        long cverol = 0;
        for (var usurol: usurolList){
            if (usurol.getCverol() != 8 && usurol.getCverol() != 7){
                cverol = usurol.getCverol();
            }
        }

        long cveentint = 0;
        for(var usuinst: usuinstList){
            cveentint = usuinst.getCveinst();
        }

        /*Busca al candidato para añadirle un voto sin relacion directa con el usuario*/
        findAndSetVoto(cvecandidato, cveentint, cverol);

        /*Perisistimos para que el usuario no pueda volver a votar este año*/
        usuarioDao.createOrUpdateUsuario(usuario);
        response.setKey("VotoRegistrado");
        return  response;
    }

    private void findAndSetVoto(long cvecandidato, long cveinst, long cverol) {
        // Verificar si el candidato existe
        if (!candidatoDao.existTblcandidatoByCveCan(cvecandidato)) {
            throw new ResourceNotFoundException("No existe el candidato con clave: " + cvecandidato);
        }

        // Obtener el candidato
        Tblcandidato candidato = candidatoDao.findTblcandidatoByCvecan(cvecandidato);

        // Validar el año del candidato
        if (candidato.getAniocan() != Year.now().getValue()) {
            throw new AccessDeniedException("El candidato " + cvecandidato + " no está postulado para este año");
        }

        // Incrementar votos totales
        candidato.setVotos((candidato.getVotos() != null ? candidato.getVotos() : 0) + 1);

        // Incrementar votos por institución según cveinst
        switch ((int) cveinst) {
            case 1:
                candidato.setInst1((candidato.getInst1() != null ? candidato.getInst1() : 0) + 1);
                break;
            case 2:
                candidato.setInst2((candidato.getInst2() != null ? candidato.getInst2() : 0) + 1);
                break;
            case 3:
                candidato.setInst3((candidato.getInst3() != null ? candidato.getInst3() : 0) + 1);
                break;
            default:
                throw new ResourceNotFoundException("No existe la institución con clave: " + cveinst);
        }

        switch ((int) cverol) {
            case 4:
                candidato.setAlumnoCount((candidato.getAlumnoCount() != null ? candidato.getAlumnoCount() : 0) + 1);
                break;
            case 6:
                candidato.setAdminCount((candidato.getAdminCount() != null ? candidato.getAdminCount() : 0) + 1);
                break;
            case 5:
                candidato.setMaestroCount((candidato.getMaestroCount() != null ? candidato.getMaestroCount() : 0) + 1);
                break;
            default:
                throw new ResourceNotFoundException("No existe la institución con clave: " + cveinst);
        }

        // Log de información
        log.info("Votos actualizados: {}", candidato.getVotos());
        log.warn("Guardando candidato con clave: {}", candidato.getCvecan());

        // Persistir cambios
        candidatoDao.createOrUpdateCandidato(candidato);
    }


    private Set<Tblvoto> addVotoUsuario(){
        Tblvoto voto = new Tblvoto();
        voto.setFechavota(LocalDateTime.now());

        votoDao.createORUpdateVoto(voto);

        Set<Tblvoto> votos = new HashSet<>();
        votos.add(voto);

        return votos;
    }

    private boolean validVotacionAnual(Tbluser usuario){

        //valida si el usuario es un candidato de este año
        if (!usuario.getCandidaturas().isEmpty()){
            Set<Tblcandidato> candidaturasSet = usuario.getCandidaturas();
            for (Tblcandidato candidatura: candidaturasSet){
                if (candidatura.getAniocan() == Year.now().getValue()){
                    //si lo es no puede votar
                    return true;
                }
            }
        }
        //valida si el usuario ya voto
        if (!usuario.getVotos().isEmpty()){
            Set<Tblvoto> votosSet = usuario.getVotos();
            for (Tblvoto voto: votosSet){
                if (voto.getFechavota().getYear() == Year.now().getValue()){
                    //si ya voto este año no puede volver a votar
                    return true;
                }
            }
        }
        return false;
    }
}
