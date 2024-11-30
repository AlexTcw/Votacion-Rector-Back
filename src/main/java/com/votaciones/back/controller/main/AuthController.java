package com.votaciones.back.controller.main;


import com.votaciones.back.model.pojos.consume.ConsumeJsonLogin;
import com.votaciones.back.model.pojos.response.ResponseJsonLogin;
import com.votaciones.back.service.users.UsuarioService;
import lombok.RequiredArgsConstructor;
import org.apache.coyote.BadRequestException;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.annotation.*;

import java.time.LocalTime;

@RestController
@CrossOrigin(origins = {"*"})
@RequestMapping(value = "/auth")
@RequiredArgsConstructor
public class AuthController {

    private final UsuarioService usuarioService;

//    private final LocalTime startTime = LocalTime.of(7, 0);
//    private final LocalTime endTime = LocalTime.of(8, 30);

    @PostMapping(value = "login", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ResponseJsonLogin> login(@RequestBody ConsumeJsonLogin consume) {
        LocalTime currentTime = LocalTime.now();

        // Validar si la hora actual está dentro del rango permitido
//        if (currentTime.isBefore(startTime) || currentTime.isAfter(endTime)) {
//            throw new BadCredentialsException("Acceso no permitido fuera del horario de 7:00 AM a 9:00 AM.");
//        }

        // Procesar el login normalmente si está dentro del horario permitido
        return ResponseEntity.ok(usuarioService.login(consume));
    }
}
