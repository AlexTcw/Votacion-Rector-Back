package com.votaciones.back.service.users;


import com.votaciones.back.model.pojos.consume.ConsumeJsonGeneric;
import com.votaciones.back.model.pojos.consume.ConsumeJsonLong;
import com.votaciones.back.model.pojos.consume.ConsumeJsonString;
import com.votaciones.back.model.pojos.consume.ConsumeJsonUsuario;
import com.votaciones.back.model.pojos.response.ResponseJsonLongString;
import com.votaciones.back.model.pojos.response.ResponseJsonPage;
import com.votaciones.back.model.pojos.response.ResponseJsonString;
import com.votaciones.back.model.pojos.response.ResponseJsonUsuario;

public interface UsuarioService {

    ResponseJsonString bcrypt(ConsumeJsonString consume);

    ResponseJsonUsuario createOrUpdateUsuario(ConsumeJsonUsuario consumeJsonUsuario, long cveuser);

    ResponseJsonPage findAllUsersByKey(ConsumeJsonGeneric consume);

    ResponseJsonLongString deleteUserByCveuser(ConsumeJsonLong consume);
}
