package com.votaciones.back.service.votacionService;

import com.votaciones.back.model.pojos.consume.ConsumeJsonLongLong;
import com.votaciones.back.model.pojos.consume.ConsumeJsonLongString;
import com.votaciones.back.model.pojos.consume.ConsumeJsonString;
import com.votaciones.back.model.pojos.response.ResponseJsonCandidato;
import com.votaciones.back.model.pojos.response.ResponseJsonString;
import org.springframework.transaction.annotation.Transactional;

public interface VotacionService {

    ResponseJsonString validateAndSetInvalidWithKey(ConsumeJsonLongString consume);

    @Transactional
    ResponseJsonCandidato setWinner();

    ResponseJsonString validateAndSetVoto(ConsumeJsonLongLong consume);
}
