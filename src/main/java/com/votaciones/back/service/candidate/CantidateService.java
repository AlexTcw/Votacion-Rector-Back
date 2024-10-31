package com.votaciones.back.service.candidate;

import com.votaciones.back.model.pojos.consume.ConsumeJsonCandidato;
import com.votaciones.back.model.pojos.response.ResponseJsonCandidato;

public interface CantidateService {

    ResponseJsonCandidato createOrUpdateCandidato(ConsumeJsonCandidato consume);
}
