package com.votaciones.back.controller.main;


import com.votaciones.back.model.pojos.consume.ConsumeJsonLogin;
import com.votaciones.back.model.pojos.response.ResponseJsonLogin;
import com.votaciones.back.service.users.UsuarioService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin(origins = {"*"})
@RequestMapping(value = "/auth")
@RequiredArgsConstructor
public class AuthController {

    private final UsuarioService usuarioService;

    @PostMapping(value = "login",consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ResponseJsonLogin> login(@RequestBody ConsumeJsonLogin consume) {
        return ResponseEntity.ok(usuarioService.login(consume));
    }
}
