package com.votaciones.back.service.users;


import com.votaciones.back.model.pojos.consume.*;
import com.votaciones.back.model.pojos.response.ResponseJsonLongString;
import com.votaciones.back.model.pojos.response.ResponseJsonPage;
import com.votaciones.back.model.pojos.response.ResponseJsonString;
import com.votaciones.back.model.pojos.response.ResponseJsonUsuario;

public interface UsuarioService {

    ResponseJsonString bcrypt(ConsumeJsonString consume);

    ResponseJsonUsuario createOrUpdateUsuario(ConsumeJsonUsuario consumeJsonUsuario, long cveuser);

    ResponseJsonUsuario findUserByEmailOrNumcuentaAndPassword(ConsumeJsonString consume);

    ResponseJsonPage findAllUsersByKey(ConsumeJsonGeneric consume);

    ResponseJsonLongString deleteUserByCveuser(long id);

    ResponseJsonString generateFakeUsersByRange(ConsumeJsonGeneric consume);

    ResponseJsonString encriptadorContrasenias();
}
