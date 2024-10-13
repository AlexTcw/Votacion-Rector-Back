package com.example.sistemavotacionback.dao.usuario;

import com.example.sistemavotacionback.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;

public class UsuarioDaoImp implements UsuarioDao {

    private final UsuarioRepository usuarioRepository;

    public UsuarioDaoImp(UsuarioRepository usuarioRepository) {
        this.usuarioRepository = usuarioRepository;
    }
}
