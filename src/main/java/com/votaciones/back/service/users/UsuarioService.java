package com.votaciones.back.service.users;


import com.votaciones.back.model.entity.Tbluser;
import com.votaciones.back.model.pojos.consume.*;
import com.votaciones.back.model.pojos.response.*;

public interface UsuarioService {

    ResponseJsonString bcrypt(ConsumeJsonString consume);

    ResponseJsonLogin login(ConsumeJsonLogin consume);

    ResponseJsonUsuario createOrUpdateUsuario(ConsumeJsonUsuario consumeJsonUsuario, long cveuser);

    ResponseJsonUsuario findUserByEmailOrNumcuentaAndPassword(ConsumeJsonString consume);

    ResponseJsonPage findAllUsersByKey(ConsumeJsonGeneric consume);

    ResponseJsonLongString deleteUserByCveuser(long id);

    ResponseJsonString generateFakeUsersByRange(ConsumeJsonGeneric consume);

    ResponseJsonString encriptadorContrasenias();
}
