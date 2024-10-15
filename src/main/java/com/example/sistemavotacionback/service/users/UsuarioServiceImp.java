package com.example.sistemavotacionback.service.users;

import com.example.sistemavotacionback.dao.institucion.InstitucionDao;
import com.example.sistemavotacionback.dao.roles.RolesDao;
import com.example.sistemavotacionback.dao.usuario.UsuarioDao;
import com.example.sistemavotacionback.model.entity.*;
import com.example.sistemavotacionback.model.exception.AccessDeniedException;
import com.example.sistemavotacionback.model.exception.ResourceNotFoundException;
import com.example.sistemavotacionback.model.pojos.consume.ConsumeJsonGeneric;
import com.example.sistemavotacionback.model.pojos.consume.ConsumeJsonUsuario;
import com.example.sistemavotacionback.model.pojos.response.ResponseJsonGeneric;
import com.example.sistemavotacionback.model.pojos.response.ResponseJsonPage;
import com.example.sistemavotacionback.model.pojos.response.ResponseJsonUsuario;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.example.sistemavotacionback.service.util.ValidUtils.validateConsume;

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


    /*CREATE AND UPDATE*/
    @Override
    @Transactional
    public ResponseJsonUsuario createOrUpdateUsuario(ConsumeJsonUsuario consume, long cveuser) {
        /*validamos los datos del usuario*/

        validateConsume(consume);

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
        String password = "Password"; // en esta parte deberiamos llamar al servicio con bycrypt
        usuario.setNameusr(consume.getName());
        usuario.setApeuser(consume.getLastName());
        usuario.setEmailuser(consume.getEmail());
        usuario.setPassworduser(password);
        usuario.setNumcunetauser(consume.getNumCuenta());

        /*primera peristencia de datos*/
        usuario = usuarioDao.createOrUpdateUsuario(usuario);
        updateRoles(consume.getRolList(),usuario);
        updateInstituciones(consume.getInstList(),usuario);

        return fillResponseUser(usuario);
    }

    private void updateRoles(List<Integer> cverolList, Tbluser usuario){
        /*Borramos lo anterior para evitar traslapes*/
        if (rolesDao.userHaveRolesByCveuser(usuario.getCveuser())){
            rolesDao.deleteRolByCveUser(usuario.getCveuser());
        }

        if (cverolList.contains(1)) {
            throw new AccessDeniedException("No tienes permisos para hacer eso");
        }

        /*añadimos el rol de usuario por defecto*/
        /*el rol del sistema solo puede ser modificado por el admin*/
        /*este metodo es para añadir entre alumno, profesor o administrativo*/
        Tblusurol usuariorol = new Tblusurol();
        usuariorol.setTblrol(rolesDao.findRolByCverol(2L));
        usuariorol.setTbluser(usuario);
        rolesDao.CreateOrUpdateUsuRol(usuariorol);

        for(long cverol : cverolList) {
            if (rolesDao.existRolByCveRol(cverol)){
                Tblusurol rol = new Tblusurol();
                rol.setTbluser(usuario);
                rol.setTblrol(rolesDao.findRolByCverol(cverol));
                rolesDao.CreateOrUpdateUsuRol(rol);
            }
        }
    }

    private void updateInstituciones(List<Integer> cveInstList, Tbluser usuario){
        if (institucionDao.userhaveInstitucionByCveuser(usuario.getCveuser())){
            institucionDao.deleteInstitutionByCveUser(usuario.getCveuser());
        }

        for(long cveinst: cveInstList) {
            if (institucionDao.existsinstitucionByCveinst(cveinst)) {
                Tblusuinst institucion = new Tblusuinst();
                institucion.setTbluser(usuario);
                institucion.setTblinst(institucionDao.findInstitutionByCveinst(cveinst));
                institucionDao.createOrUpdateTblusuinst(institucion);
            }
        }
    }

    private ResponseJsonUsuario fillResponseUser(Tbluser usuario) {
        ResponseJsonUsuario response = new ResponseJsonUsuario();

        // Llenar la información básica del usuario
        response.setCveuser(usuario.getCveuser());
        response.setName(usuario.getNameusr());
        response.setLastName(usuario.getApeuser());
        response.setEmail(usuario.getEmailuser());
        response.setPassword(usuario.getPassworduser());
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
        Map<String, Object> responseMap = new HashMap<>(), data = consume.getDatos();
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
}
