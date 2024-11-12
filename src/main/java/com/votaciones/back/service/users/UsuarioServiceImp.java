package com.votaciones.back.service.users;

import com.votaciones.back.dao.institucion.InstitucionDao;
import com.votaciones.back.dao.roles.RolesDao;
import com.votaciones.back.dao.usuario.UsuarioDao;
import com.votaciones.back.model.entity.Tblinst;
import com.votaciones.back.model.entity.Tblrol;
import com.votaciones.back.model.entity.Tbluser;
import com.votaciones.back.model.exception.DuplicateDataException;
import com.votaciones.back.model.exception.ResourceNotFoundException;
import com.votaciones.back.model.pojos.consume.*;
import com.votaciones.back.model.pojos.response.ResponseJsonLongString;
import com.votaciones.back.model.pojos.response.ResponseJsonPage;
import com.votaciones.back.model.pojos.response.ResponseJsonString;
import com.votaciones.back.model.pojos.response.ResponseJsonUsuario;
import com.votaciones.back.service.util.FakeDataGenerator;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

import static com.votaciones.back.service.util.ValidUtils.validateConsume;

@Slf4j
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

    public String bcrypt (String password){
        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        return passwordEncoder.encode(password);
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
    public ResponseJsonUsuario findUserByEmailOrNumcuentaAndPassword(ConsumeJsonString consume) {
        validateConsume(consume);
        Tbluser usuario = null; // Inicializar explícitamente a null
        String param = consume.getKey();
        System.out.println(param);

        if (usuarioDao.existsTbluserByEmailuser(param)) {
            usuario = usuarioDao.findTbluserByEmailuser(param);
        } else if (usuarioDao.existsTbluserByNumcunetauser(param)) {
            usuario = usuarioDao.findTbluserByNumcunetauser(param);
        }

        // Verificar si usuario es null y lanzar excepción en caso necesario
        if (usuario == null) {
            throw new ResourceNotFoundException("No existe un usuario con: " + param);
        }

        return fillResponseUser(usuario);
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
    public ResponseJsonLongString deleteUserByCveuser(long cveuser) {

        validateConsume(cveuser);
        ResponseJsonLongString response = new ResponseJsonLongString();
        response.setId(cveuser);
        response.setKey("usuario eliminado exitosamente");
        if (usuarioDao.existsTbluserByCveuser(cveuser)){
            usuarioDao.deleteTbluserByCveuser(cveuser);
            return response;
        }

        throw new ResourceNotFoundException("Usuario no encontrado");
    }

    @SuppressWarnings("unchecked")
    @Override
    public ResponseJsonString generateFakeUsersByRange(ConsumeJsonGeneric consume) {
        Map<String, Object> data = consume.getDatos();
        int initialnumcuent = 2000000;
        long range = (long) data.getOrDefault("range", 10L);
        List<Integer> cverolList = (List<Integer>) data.getOrDefault("rolList", new ArrayList<>());
        List<Integer> cveinstList = (List<Integer>) data.getOrDefault("instList", new ArrayList<>());
        Set<Tbluser> tbluserSet = new HashSet<>();

        // Verificar que listas de roles e instituciones no estén vacías
        if (cverolList.isEmpty() || cveinstList.isEmpty()) {
            throw new IllegalArgumentException("Roles e instituciones no pueden estar vacíos");
        }

        for (int i = 0; i < range; i++) {
            String nombre = FakeDataGenerator.generarNombre();
            String genero = nombre.endsWith("a") ? "F" : "M";  // Determinación simple del género

            Tbluser user = Tbluser.builder()
                    .nameusr(nombre)
                    .apeuser(FakeDataGenerator.generarApellido())
                    .emailuser(FakeDataGenerator.generarEmail())
                    .numcunetauser(String.valueOf(initialnumcuent))
                    .generouser(genero)
                    .passworduser(bcrypt(FakeDataGenerator.generarPasword()))
                    .roles(updateRoles(cverolList))
                    .instituciones(updateInstituciones(cveinstList))
                    .build();

            initialnumcuent++;
            tbluserSet.add(user);
        }

        usuarioDao.saveall(tbluserSet);

        // Crear la respuesta con la cantidad de usuarios generados
        ResponseJsonString response = new ResponseJsonString();
        response.setKey(range + " usuarios generados correctamente");

        return response;
    }

    @Override
    public ResponseJsonString encriptadorContrasenias() {
        ResponseJsonString response = new ResponseJsonString();

        List<Long> cvesuser = usuarioDao.findAllUsersCve();
        int cvesuserLength = cvesuser.size();

        for (long cveuser : cvesuser) {
            if (usuarioDao.existsTbluserByCveuser(cveuser)) {
                Tbluser usuario = usuarioDao.findTblUserByCveuser(cveuser);
                log.warn("Actualizando usuario {}", usuario.getNameusr());

                // Verificar si la contraseña ya está encriptada
                if (!usuario.getPassworduser().startsWith("$2a$") &&
                        !usuario.getPassworduser().startsWith("$2b$") &&
                        !usuario.getPassworduser().startsWith("$2y$")) {

                    usuario.setPassworduser(bcrypt(usuario.getPassworduser()));
                    usuarioDao.createOrUpdateUsuario(usuario);
                    log.info("Contraseña encriptada para el usuario: {}", usuario.getNameusr());
                } else {
                    log.info("La contraseña para el usuario {} ya está encriptada.", usuario.getNameusr());
                }

                log.info("Usuarios restantes por encriptar: {}", cvesuserLength - 1);
                cvesuserLength--;
            }
        }

        response.setKey("Proceso de encriptación finalizado. Total de usuarios procesados: " + cvesuser.size());
        return response;
    }

}
