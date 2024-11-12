package com.votaciones.back.service.votacionService;

import com.votaciones.back.dao.candidato.CandidatoDao;
import com.votaciones.back.dao.usuario.UsuarioDao;
import com.votaciones.back.dao.voto.VotoDao;
import com.votaciones.back.model.entity.Tblcandidato;
import com.votaciones.back.model.entity.Tbluser;
import com.votaciones.back.model.entity.Tblvoto;
import com.votaciones.back.model.exception.ResourceNotFoundException;
import com.votaciones.back.model.pojos.consume.ConsumeJsonLongLong;
import com.votaciones.back.model.pojos.consume.ConsumeJsonLongString;
import com.votaciones.back.model.pojos.consume.ConsumeJsonString;
import com.votaciones.back.model.pojos.response.ResponseJsonString;
import com.votaciones.back.service.candidate.CandidateService;
import com.votaciones.back.service.util.ValidUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.Year;
import java.util.HashSet;
import java.util.Set;

@Slf4j
@Service
public class VotacionServiceImp implements VotacionService {

    private final UsuarioDao usuarioDao;
    private final CandidatoDao candidatoDao;
    private final VotoDao votoDao;
    private final CandidateService candidateService;

    public VotacionServiceImp(UsuarioDao usuarioDao, CandidatoDao candidatoDao, VotoDao votoDao, CandidateService candidateService) {
        this.usuarioDao = usuarioDao;
        this.candidatoDao = candidatoDao;
        this.votoDao = votoDao;
        this.candidateService = candidateService;
    }

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
            ConsumeJsonString consumeInvalid = new ConsumeJsonString();
            consumeInvalid.setKey(consume.getKey());
            candidateService.CreateOrUpdateInvalidCan(consumeInvalid);
            response.setKey("usuario invalido creado");
        }

        if (candidato != null) {findAndsetVoto(candidato.getCvecan());}

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
                return usuarioDao.findTbluserByNameAndApeUser(parts[0], parts[1]);
            }
        }
        return null;
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

        /*Busca al candidato para añadirle un voto sin relacion directa con el usuario*/
        findAndsetVoto(cvecandidato);

        /*Perisistimos para que el usuario no pueda volver a votar este año*/
        usuarioDao.createOrUpdateUsuario(usuario);
        response.setKey("VotoRegistrado");
        return  response;
    }

    private void findAndsetVoto(long cvecandidato){
        if (!candidatoDao.existTblcandidatoByCveCan(cvecandidato)){
            throw new ResourceNotFoundException("No existe el candidato con clave: "+cvecandidato);
        }
        Tblcandidato candidato = candidatoDao.findTblcandidatoByCvecan(cvecandidato);

        if (candidato.getAniocan() != Year.now().getValue()){
            throw new AccessDeniedException("El candidato "+cvecandidato+" no esta postulado para este año");
        }

        var votos = (candidato.getVotos() != null ? candidato.getVotos() : 0) + 1;
        log.info("votos {}",votos);

        log.warn("guardando {}",candidato.getCvecan());
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
