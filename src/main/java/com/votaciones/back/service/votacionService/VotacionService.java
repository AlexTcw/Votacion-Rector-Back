package com.votaciones.back.service.votacionService;

import com.votaciones.back.model.pojos.consume.ConsumeJsonLongLong;
import com.votaciones.back.model.pojos.response.ResponseJsonString;

public interface VotacionService {

    ResponseJsonString validateAndSetVoto(ConsumeJsonLongLong consume);
}
