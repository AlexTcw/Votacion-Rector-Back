package com.votaciones.back;

import com.votaciones.back.dao.roles.Permission.PermissionDao;
import com.votaciones.back.dao.roles.RolesDao;
import com.votaciones.back.dao.usuario.UsuarioDao;
import com.votaciones.back.model.entity.Tblpermission;
import com.votaciones.back.model.entity.Tblrol;
import com.votaciones.back.model.entity.Tbluser;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.Set;

@SpringBootApplication
public class SistemaVotacionBackApplication {

    public static void main(String[] args) {
        SpringApplication.run(SistemaVotacionBackApplication.class, args);
    }

    @Bean
    CommandLineRunner init(UsuarioDao usuarioDao, RolesDao rolesDao, PermissionDao permissionDao) {
        return args -> {
            /*CREATE PERMISSIONS*/
            Tblpermission createPermission = createPermissionIfNotExists("CREATE", permissionDao);
            Tblpermission readPermission = createPermissionIfNotExists("READ", permissionDao);
            Tblpermission updatePermission = createPermissionIfNotExists("UPDATE", permissionDao);
            Tblpermission deletePermission = createPermissionIfNotExists("DELETE", permissionDao);

            /*CREATE ROLES*/
            Tblrol roleAdmin = createRoleIfNotExists("ADMIN", Set.of(createPermission, readPermission, updatePermission, deletePermission), rolesDao);
            Tblrol roleUser = createRoleIfNotExists("USER", Set.of(readPermission), rolesDao);

            /*CREATE DEFAULT USER*/
            if (!usuarioDao.existsTbluserByEmailuser("admonsvr@gmail.com")) {
                Tbluser userAdmin = Tbluser.builder()
                        .nameusr("ADMIN")
                        .apeuser("DEFAULT")
                        .numcunetauser("1111111")
                        .emailuser("admonsvr@gmail.com")
                        .passworduser("$2a$10$K2h4Dl.OvV1JmBe0RI8msuvajjW6yn3oSw8sfQyUqVRV3MN/CBKAe")
                        .generouser("M")
                        .roles(Set.of(roleAdmin, roleUser))
                        .build();

                usuarioDao.createOrUpdateUsuario(userAdmin);
            }
        };
    }
    private Tblpermission createPermissionIfNotExists(String name, PermissionDao permissionDao) {
        // Verifica si el permiso ya existe
        if (!permissionDao.existsTblpermissionByNamePermission(name)) {
            // Si no existe, crea uno nuevo
            Tblpermission permission = Tblpermission.builder()
                    .namePermission(name)
                    .build();
            permissionDao.createOrUpdateTblpermission(permission);
            return permission; // Devuelve el nuevo permiso creado
        }
        // Si ya existe, recupera la instancia existente
        return permissionDao.findTblpermissionByName(name); // Asegúrate de tener un método para recuperar permisos por nombre
    }

    private Tblrol createRoleIfNotExists(String name, Set<Tblpermission> permissions, RolesDao rolesDao) {
        // Verifica si el rol ya existe
        if (!rolesDao.existsTblrolByNamerol(name)) {
            // Si no existe, crea uno nuevo
            Tblrol role = Tblrol.builder()
                    .namerol(name)
                    .permissions(permissions)
                    .build();
            rolesDao.createOrUpdateRol(role);
            return role; // Devuelve el nuevo rol creado
        }
        // Si ya existe, recupera la instancia existente
        return rolesDao.findTblrolByNamerol(name);
    }
}
