package com.votaciones.back.service.candidate;

import com.votaciones.back.model.pojos.consume.ConsumeJsonCandidato;
import com.votaciones.back.model.pojos.consume.ConsumeJsonLong;
import com.votaciones.back.model.pojos.consume.ConsumeJsonString;
import com.votaciones.back.model.pojos.response.ResponseJsonCandidato;
import com.votaciones.back.model.pojos.response.ResponseJsonGeneric;

public interface cantidateService {

    ResponseJsonCandidato createOrUpdateCandidato(ConsumeJsonCandidato consume);

    void deleteCandidatoByCvecan(ConsumeJsonLong consume);

    ResponseJsonGeneric findAllCandidatos();

    ResponseJsonCandidato findCandidatoByCvecan(ConsumeJsonLong consume);
}
