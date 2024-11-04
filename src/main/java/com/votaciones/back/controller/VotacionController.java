package com.votaciones.back.controller;

import com.votaciones.back.model.exception.ErrorResponse;
import com.votaciones.back.model.pojos.consume.ConsumeJsonLongLong;
import com.votaciones.back.model.pojos.response.ResponseJsonString;
import com.votaciones.back.service.votacionService.VotacionService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@PreAuthorize("hasRole('ADMIN')")
@CrossOrigin(origins = {"*"})
@RequestMapping(value = "/votacion")
public class VotacionController {

    private final VotacionService votacionService;

    public VotacionController(VotacionService votacionService) {
        this.votacionService = votacionService;
    }

    @PreAuthorize("hasRole('USER')")
    @PostMapping(value = {"/setVoto"}, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "Registra un voto de un usuario")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Voto registrado exitosamente"),
            @ApiResponse(responseCode = "404",
                    description = "Usuario o candidato no encontrado",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(responseCode = "403",
                    description = "El usuario ya ha votado o no tiene permiso para votar",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    })
    public ResponseEntity<ResponseJsonString> setVoto(
            @Parameter(description = "Datos del voto con claves de usuario y candidato", required = true)
            @RequestBody ConsumeJsonLongLong consume) {
        return ResponseEntity.ok(votacionService.validateAndSetVoto(consume));
    }
}
