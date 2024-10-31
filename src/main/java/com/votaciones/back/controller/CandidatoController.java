package com.votaciones.back.controller;

import com.votaciones.back.model.pojos.consume.ConsumeJsonCandidato;
import com.votaciones.back.model.pojos.consume.ConsumeJsonUsuario;
import com.votaciones.back.model.pojos.response.ResponseJsonCandidato;
import com.votaciones.back.model.pojos.response.ResponseJsonUsuario;
import com.votaciones.back.service.candidate.CantidateService;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@PreAuthorize("hasRole('ADMIN')")
@CrossOrigin(origins = {"*"})
@RequestMapping(value = "/candidatos")
public class CandidatoController {

    private final CantidateService cantidateService;

    public CandidatoController(CantidateService cantidateService) {
        this.cantidateService = cantidateService;
    }

    @PostMapping(value = {"/createCandidato"}, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ResponseJsonCandidato> createOrUpdateUsuario(@RequestBody ConsumeJsonCandidato consume) {
        return ResponseEntity.ok(cantidateService.createOrUpdateCandidato(consume));
    }
}
