package com.votaciones.back.controller;

import com.votaciones.back.model.pojos.consume.ConsumeJsonCandidato;
import com.votaciones.back.model.pojos.consume.ConsumeJsonLong;
import com.votaciones.back.model.pojos.consume.ConsumeJsonString;
import com.votaciones.back.model.pojos.response.ResponseJsonCandidato;
import com.votaciones.back.model.pojos.response.ResponseJsonGeneric;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@PreAuthorize("hasRole('ADMIN')")
@CrossOrigin(origins = {"*"})
@RequestMapping(value = "/candidatos")
public class CandidatoController {

    private final com.votaciones.back.service.candidate.cantidateService candidateService;

    public CandidatoController(com.votaciones.back.service.candidate.cantidateService cantidateService) {
        this.candidateService = cantidateService;
    }

    @PostMapping(value = {"/createCandidato"}, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ResponseJsonCandidato> createOrUpdateCandidate(@RequestBody ConsumeJsonCandidato consume) {
        return ResponseEntity.ok(candidateService.createOrUpdateCandidato(consume));
    }


    @PreAuthorize("hasRole('USER')")
    @PostMapping(value = {"/findAllCandidatos"}, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ResponseJsonGeneric> findAllCandidatos(@RequestBody ConsumeJsonString consume) {
        return ResponseEntity.ok(candidateService.findAllCandidatos(consume));
    }

    @DeleteMapping(value = {"/deleteCandidatoByCvecan"}, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> deleteCandidatoByCvecan(@RequestBody ConsumeJsonLong consume) {
        candidateService.deleteCandidatoByCvecan(consume);
        return ResponseEntity.ok("Usuario " + consume.getId() + " eliminado correctamente");
    }

    @PreAuthorize("hasRole('USER')")
    @GetMapping(value = {"/findCandidatoByCvecan"}, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ResponseJsonCandidato>findCandidatoByCvecan(@RequestBody ConsumeJsonLong consume){
        return ResponseEntity.ok(candidateService.findCandidatoByCvecan(consume));
    }


}
