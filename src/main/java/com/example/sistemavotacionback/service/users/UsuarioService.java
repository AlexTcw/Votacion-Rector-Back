package com.example.sistemavotacionback.service.users;


import com.example.sistemavotacionback.model.pojos.consume.ConsumeJsonGeneric;
import com.example.sistemavotacionback.model.pojos.consume.ConsumeJsonLong;
import com.example.sistemavotacionback.model.pojos.consume.ConsumeJsonUsuario;
import com.example.sistemavotacionback.model.pojos.response.ResponseJsonGeneric;
import com.example.sistemavotacionback.model.pojos.response.ResponseJsonLongString;
import com.example.sistemavotacionback.model.pojos.response.ResponseJsonPage;
import com.example.sistemavotacionback.model.pojos.response.ResponseJsonUsuario;

public interface UsuarioService {
    ResponseJsonUsuario createOrUpdateUsuario(ConsumeJsonUsuario consumeJsonUsuario, long cveuser);

    ResponseJsonPage findAllUsersByKey(ConsumeJsonGeneric consume);

    ResponseJsonLongString deleteUserByCveuser(ConsumeJsonLong consume);
}
