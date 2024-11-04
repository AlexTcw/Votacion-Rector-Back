package com.votaciones.back.service.votacionService;

import com.votaciones.back.dao.candidato.CandidatoDao;
import com.votaciones.back.dao.usuario.UsuarioDao;
import com.votaciones.back.dao.voto.VotoDao;
import com.votaciones.back.model.entity.Tblcandidato;
import com.votaciones.back.model.entity.Tbluser;
import com.votaciones.back.model.entity.Tblvoto;
import com.votaciones.back.model.exception.ResourceNotFoundException;
import com.votaciones.back.model.pojos.consume.ConsumeJsonLongLong;
import com.votaciones.back.model.pojos.response.ResponseJsonString;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.Year;
import java.util.HashSet;
import java.util.Set;

@Service
public class VotacionServiceImp implements VotacionService {

    private final UsuarioDao usuarioDao;
    private final CandidatoDao candidatoDao;
    private final VotoDao votoDao;

    public VotacionServiceImp(UsuarioDao usuarioDao, CandidatoDao candidatoDao, VotoDao votoDao) {
        this.usuarioDao = usuarioDao;
        this.candidatoDao = candidatoDao;
        this.votoDao = votoDao;
    }

    @Override
    @Transactional
    public ResponseJsonString validateAndSetVoto(ConsumeJsonLongLong consume){
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
        if (!validVotacionAnual(usuario)){
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

        var votos = candidato.getVotos() + 1;
        candidato.setVotos(votos);

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
                    return false;
                }
            }
        }
        //valida si el usuario ya voto
        if (!usuario.getVotos().isEmpty()){
            Set<Tblvoto> votosSet = usuario.getVotos();
            for (Tblvoto voto: votosSet){
                if (voto.getFechavota().getYear() == Year.now().getValue()){
                    //si ya voto este año no puede volver a votar
                    return false;
                }
            }
        }
        return true;
    }
}
