package com.votaciones.back.service.votacionService;

import com.votaciones.back.model.pojos.consume.ConsumeJsonLongLong;
import com.votaciones.back.model.pojos.consume.ConsumeJsonLongString;
import com.votaciones.back.model.pojos.consume.ConsumeJsonString;
import com.votaciones.back.model.pojos.response.ResponseJsonString;

public interface VotacionService {

    ResponseJsonString validateAndSetInvalidWithKey(ConsumeJsonLongString consume);

    ResponseJsonString validateAndSetVoto(ConsumeJsonLongLong consume);
}
