package com.votaciones.back.service.users;

import com.votaciones.back.dao.institucion.InstitucionDao;
import com.votaciones.back.dao.roles.RolesDao;
import com.votaciones.back.dao.usuario.UsuarioDao;
import com.votaciones.back.model.entity.Tblinst;
import com.votaciones.back.model.entity.Tblrol;
import com.votaciones.back.model.entity.Tbluser;
import com.votaciones.back.model.exception.DuplicateDataException;
import com.votaciones.back.model.exception.ResourceNotFoundException;
import com.votaciones.back.model.pojos.consume.ConsumeJsonGeneric;
import com.votaciones.back.model.pojos.consume.ConsumeJsonLong;
import com.votaciones.back.model.pojos.consume.ConsumeJsonString;
import com.votaciones.back.model.pojos.consume.ConsumeJsonUsuario;
import com.votaciones.back.model.pojos.response.ResponseJsonLongString;
import com.votaciones.back.model.pojos.response.ResponseJsonPage;
import com.votaciones.back.model.pojos.response.ResponseJsonString;
import com.votaciones.back.model.pojos.response.ResponseJsonUsuario;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

import static com.votaciones.back.service.util.ValidUtils.validateConsume;

@Service
public class UsuarioServiceImp implements UsuarioService {

    private final UsuarioDao usuarioDao;
    private final RolesDao rolesDao;
    private final InstitucionDao institucionDao;

    public UsuarioServiceImp(UsuarioDao usuarioDao, RolesDao rolesDao, InstitucionDao institucionDao) {
        this.usuarioDao = usuarioDao;
        this.rolesDao = rolesDao;
        this.institucionDao = institucionDao;
    }


    @Override
    public ResponseJsonString bcrypt(ConsumeJsonString consume) {
        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        String encryptedPassword = passwordEncoder.encode(consume.getKey());
        ResponseJsonString response = new ResponseJsonString();
        response.setKey(encryptedPassword);
        return response;
    }

    /*CREATE AND UPDATE*/
    @Override
    @Transactional
    public ResponseJsonUsuario createOrUpdateUsuario(ConsumeJsonUsuario consume, long cveuser) {
        /*validamos los datos del usuario*/

        validateConsume(consume);

        //validamos un email repetido
        if (cveuser == 0) {
            if (usuarioDao.existsTbluserByEmailuser(consume.getEmail())) {
                throw new DuplicateDataException("El correo ya existe en base");
            } else if (usuarioDao.existsTbluserByNumcunetauser(consume.getNumCuenta())) {
                throw new DuplicateDataException("El numero de cuenta ya existe en base");
            }
        }

        /*inicializamos un usuario nuevo*/
        Tbluser usuario = new Tbluser();
        /*Si queremos actualizar al usuario*/
        if (cveuser > 0){
             if (usuarioDao.existsTbluserByCveuser(cveuser)){
                 usuario = usuarioDao.findTblUserByCveuser(cveuser);
             } else {
                 throw new ResourceNotFoundException("Usuario no encontrado con clave" + cveuser);
             }
        }
        /*predeterminado para crear usuarios*/
        ConsumeJsonString consumePassword = new ConsumeJsonString();
        consumePassword.setKey(consume.getPassword());
        usuario.setPassworduser(bcrypt(consumePassword).getKey());
        usuario.setNameusr(consume.getName());
        usuario.setApeuser(consume.getLastName());
        usuario.setEmailuser(consume.getEmail());
        usuario.setNumcunetauser(consume.getNumCuenta());
        usuario.setGenerouser(consume.getGenero());
        usuario.setRoles(updateRoles(consume.getRolList()));
        usuario.setInstituciones(updateInstituciones(consume.getInstList()));

        /*primera peristencia de datos*/
        usuario = usuarioDao.createOrUpdateUsuario(usuario);

        return fillResponseUser(usuario);
    }

    private Set<Tblrol> updateRoles(List<Integer> cverolList){
        /*añadimos el rol de usuario por defecto*/
        /*el rol del sistema solo puede ser modificado por el admin*/
        /*este metodo es para añadir entre alumno, profesor o administrativo*/
        Set<Tblrol> tblrolSet = new HashSet<>();
        Tblrol rol = rolesDao.findRolByCverol(8L);
        tblrolSet.add(rol);
        for (long cverol: cverolList){
            if (rolesDao.existRolByCveRol(cverol)){
                rol = rolesDao.findRolByCverol(cverol);
                tblrolSet.add(rol);
            }else throw new ResourceNotFoundException("No existe un rol con clave: "+cverol);
        }
        return tblrolSet;
    }

    private Set<Tblinst> updateInstituciones(List<Integer> cveInstList){
        Set<Tblinst> tblinstSet = new HashSet<>();
        for(long cveinst: cveInstList) {
            if (institucionDao.existsinstitucionByCveinst(cveinst)) {
                Tblinst institucion;
                institucion = institucionDao.findInstitutionByCveinst(cveinst);
                tblinstSet.add(institucion);
            } else throw new ResourceNotFoundException("No existe una institucion con clave: "+cveinst);
        }

        return tblinstSet;
    }

    private ResponseJsonUsuario fillResponseUser(Tbluser usuario) {
        ResponseJsonUsuario response = new ResponseJsonUsuario();

        // Llenar la información básica del usuario
        response.setCveuser(usuario.getCveuser());
        response.setName(usuario.getNameusr());
        response.setLastName(usuario.getApeuser());
        response.setEmail(usuario.getEmailuser());
        response.setNumCuenta(usuario.getNumcunetauser());

        // Obtener nombres de roles usando streams
        response.setRolList(rolesDao.findRolesNamesByCveuser(usuario.getCveuser()));

        // Obtener nombres de instituciones usando streams
        response.setInstList(institucionDao.findInstNamesByCveuser(usuario.getCveuser()));

        return response;
    }

    @Override
    public ResponseJsonPage findAllUsersByKey(ConsumeJsonGeneric consume) {
        validateConsume(consume);
        ResponseJsonPage response = new ResponseJsonPage();
        int page,size;
        String key;
        Pageable pageable;
        Map<String, Object> data = consume.getDatos();
        key = data.containsKey("key") ? (String) data.get("key") : null;
        page = data.containsKey("page") ? (int) data.get("page") : 0;
        size = data.containsKey("size") ? (int) data.get("size") : 10;
        pageable = PageRequest.of(page, size);

        var usrResults = usuarioDao.findUserByKeyPage(key, pageable);
        response.setContent(usrResults.getContent());
        response.setPage(usrResults.getNumber());
        response.setSize(usrResults.getSize());
        response.setTotalElements(usrResults.getTotalElements());
        response.setTotalPages(usrResults.getTotalPages());

        return response;
    }

    @Override
    @Transactional
    public ResponseJsonLongString deleteUserByCveuser(ConsumeJsonLong consume) {

        validateConsume(consume);
        ResponseJsonLongString response = new ResponseJsonLongString();
        long cveuser = consume.getId();
        response.setId(cveuser);
        response.setKey("usuario eliminado exitosamente");
        if (usuarioDao.existsTbluserByCveuser(cveuser)){
            usuarioDao.deleteTbluserByCveuser(cveuser);
            return response;
        }

        throw new ResourceNotFoundException("Usuario no encontrado");
    }


}
