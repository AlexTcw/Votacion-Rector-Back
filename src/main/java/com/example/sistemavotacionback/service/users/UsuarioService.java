package com.example.sistemavotacionback.service.users;


import com.example.sistemavotacionback.model.pojos.consume.ConsumeJsonUsuario;
import com.example.sistemavotacionback.model.pojos.response.ResponseJsonUsuario;

public interface UsuarioService {
    ResponseJsonUsuario createOrUpdateUsuario(ConsumeJsonUsuario consumeJsonUsuario, long cveuser);
}
