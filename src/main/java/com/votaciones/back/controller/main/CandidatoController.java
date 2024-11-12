package com.votaciones.back.controller.main;

import com.votaciones.back.model.pojos.consume.ConsumeJsonCandidato;
import com.votaciones.back.model.pojos.consume.ConsumeJsonLong;
import com.votaciones.back.model.pojos.consume.ConsumeJsonString;
import com.votaciones.back.model.pojos.response.ResponseJsonCandidato;
import com.votaciones.back.model.pojos.response.ResponseJsonGeneric;
import com.votaciones.back.model.pojos.response.ResponseJsonLongString;
import com.votaciones.back.service.candidate.CandidateService;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@PreAuthorize("hasRole('ADMIN')")
@CrossOrigin(origins = {"*"})
@RequestMapping(value = "/candidatos")
public class CandidatoController {

    private final CandidateService candidateService;

    public CandidatoController(CandidateService cantidateService) {
        this.candidateService = cantidateService;
    }

    @PostMapping(value = {"/createCandidato"}, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ResponseJsonCandidato> createOrUpdateCandidate(@RequestBody ConsumeJsonCandidato consume) {
        return ResponseEntity.ok(candidateService.createOrUpdateCandidato(consume));
    }

    @PostMapping(value = {"/createCandidatoInvalid"}, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ResponseJsonLongString> createCandidatoInvalid(@RequestBody ConsumeJsonString consume) {
        return ResponseEntity.ok(candidateService.CreateOrUpdateInvalidCan(consume));
    }


    @PreAuthorize("hasRole('USER')")
    @GetMapping(value = {"/findAllCandidatos"}, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ResponseJsonGeneric> findAllCandidatos() {
        return ResponseEntity.ok(candidateService.findAllCandidatos());
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
