package com.example.sistemavotacionback.controller;

import com.example.sistemavotacionback.model.pojos.consume.ConsumeJsonGeneric;
import com.example.sistemavotacionback.model.pojos.consume.ConsumeJsonLong;
import com.example.sistemavotacionback.model.pojos.consume.ConsumeJsonUsuario;
import com.example.sistemavotacionback.model.pojos.response.ResponseJsonLongString;
import com.example.sistemavotacionback.model.pojos.response.ResponseJsonPage;
import com.example.sistemavotacionback.model.pojos.response.ResponseJsonUsuario;
import com.example.sistemavotacionback.service.users.UsuarioService;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping({"/users"})
@CrossOrigin(origins = {"*"})
public class UsuarioController {

    private final UsuarioService usuarioService;

    public UsuarioController(UsuarioService usuarioService) {
        this.usuarioService = usuarioService;
    }


    @PostMapping(value = {"/createOrUpdateUsuario"}, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ResponseJsonUsuario> createOrUpdateUsuario(@RequestBody ConsumeJsonUsuario consume,
                                                                     @RequestParam(required = false) Long cveuser) {
        cveuser = (cveuser == null) ? 0L : cveuser;
        return ResponseEntity.ok(usuarioService.createOrUpdateUsuario(consume, cveuser));
    }

    @PostMapping(value = {"/findAllUserByKey"}, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ResponseJsonPage> findAllUserByKey(@RequestBody ConsumeJsonGeneric consume) {

        return ResponseEntity.ok(usuarioService.findAllUsersByKey(consume));
    }

    @DeleteMapping(value = {"/deleteUserByCveuser"}, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ResponseJsonLongString> deleteUserByCveuser(@RequestBody ConsumeJsonLong consume) {

        return ResponseEntity.ok(usuarioService.deleteUserByCveuser(consume));
    }
}
