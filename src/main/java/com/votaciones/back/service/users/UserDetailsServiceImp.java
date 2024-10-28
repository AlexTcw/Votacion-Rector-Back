package com.votaciones.back.service.users;

import com.votaciones.back.dao.usuario.UsuarioDao;
import com.votaciones.back.model.entity.Tbluser;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserDetailsServiceImp implements UserDetailsService {

    private final UsuarioDao usuarioDao;

    public UserDetailsServiceImp(UsuarioDao usuarioDao) {
        this.usuarioDao = usuarioDao;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        List<SimpleGrantedAuthority> authorities = new ArrayList<>();
        Tbluser usuario = new Tbluser();

        if (usuarioDao.existsTbluserByNumcunetauser(username)) {
            usuario = usuarioDao.findTbluserByNumcunetauser(username);
        } else if (usuarioDao.existsTbluserByEmailuser(username)) {
            usuario = usuarioDao.findTbluserByEmailuser(username);
        } else {
            throw new UsernameNotFoundException("Usuario no encontrado: " + username);
        }

        usuario.getRoles()
                .forEach(role -> authorities.add(new SimpleGrantedAuthority("ROLE_".concat(role.getNamerol()))));

        usuario.getRoles().stream()
                .flatMap(role -> role.getPermissions().stream())
                .forEach(permission -> authorities.add(new SimpleGrantedAuthority(permission.getNamePermission())));

        return new User(usuario.getEmailuser(), usuario.getPassworduser(), authorities);

    }
}
