package com.votaciones.back.service.candidate;

import com.votaciones.back.model.pojos.consume.ConsumeJsonCandidato;
import com.votaciones.back.model.pojos.consume.ConsumeJsonLong;
import com.votaciones.back.model.pojos.consume.ConsumeJsonString;
import com.votaciones.back.model.pojos.response.ResponseJsonCandidato;
import com.votaciones.back.model.pojos.response.ResponseJsonGeneric;
import com.votaciones.back.model.pojos.response.ResponseJsonLongString;

public interface CandidateService {

    ResponseJsonCandidato createOrUpdateCandidato(ConsumeJsonCandidato consume);

    ResponseJsonLongString CreateOrUpdateInvalidCan(ConsumeJsonString consume);

    void deleteCandidatoByCvecan(ConsumeJsonLong consume);

    ResponseJsonGeneric findAllCandidatos();

    ResponseJsonCandidato findCandidatoByCvecan(ConsumeJsonLong consume);
}
