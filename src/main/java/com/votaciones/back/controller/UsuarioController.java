package com.votaciones.back.controller;

import com.votaciones.back.model.pojos.consume.ConsumeJsonGeneric;
import com.votaciones.back.model.pojos.consume.ConsumeJsonLong;
import com.votaciones.back.model.pojos.consume.ConsumeJsonString;
import com.votaciones.back.model.pojos.consume.ConsumeJsonUsuario;
import com.votaciones.back.model.pojos.response.ResponseJsonLongString;
import com.votaciones.back.model.pojos.response.ResponseJsonPage;
import com.votaciones.back.model.pojos.response.ResponseJsonString;
import com.votaciones.back.model.pojos.response.ResponseJsonUsuario;
import com.votaciones.back.service.users.UsuarioService;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@PreAuthorize("denyAll()")
@CrossOrigin(origins = {"*"})
@RequestMapping(value = "/usuarios")
public class UsuarioController {

    private final UsuarioService usuarioService;

    public UsuarioController(UsuarioService usuarioService) {
        this.usuarioService = usuarioService;
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping(value = {"/bcrypt"},consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ResponseJsonString> bcrypt(@RequestBody ConsumeJsonString consume){
        return ResponseEntity.ok(usuarioService.bcrypt(consume));
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping(value = {"/createOrUpdateUsuario"}, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ResponseJsonUsuario> createOrUpdateUsuario(@RequestBody ConsumeJsonUsuario consume,
                                                                     @RequestParam(required = false) Long cveuser) {
        cveuser = (cveuser == null) ? 0L : cveuser;
        return ResponseEntity.ok(usuarioService.createOrUpdateUsuario(consume, cveuser));
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping(value = {"/findAllUserByKey"}, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ResponseJsonPage> findAllUserByKey(@RequestBody ConsumeJsonGeneric consume) {

        return ResponseEntity.ok(usuarioService.findAllUsersByKey(consume));
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping(value = {"/deleteUserByCveuser"}, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ResponseJsonLongString> deleteUserByCveuser(@RequestBody ConsumeJsonLong consume) {

        return ResponseEntity.ok(usuarioService.deleteUserByCveuser(consume));
    }
}
