package com.votaciones.back.controller.main;

import com.votaciones.back.model.pojos.consume.*;
import com.votaciones.back.model.pojos.response.ResponseJsonLongString;
import com.votaciones.back.model.pojos.response.ResponseJsonPage;
import com.votaciones.back.model.pojos.response.ResponseJsonString;
import com.votaciones.back.model.pojos.response.ResponseJsonUsuario;
import com.votaciones.back.service.users.UsuarioService;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import static com.votaciones.back.service.util.ValidUtils.validateConsume;

@RestController
@PreAuthorize("hasRole('ADMIN')")
@CrossOrigin(origins = {"*"})
@RequestMapping(value = "/usuarios")
public class UsuarioController {

    private final UsuarioService usuarioService;

    public UsuarioController(UsuarioService usuarioService) {
        this.usuarioService = usuarioService;
    }

    @GetMapping(value = {"/auth"},produces = MediaType.APPLICATION_JSON_VALUE)
    private ResponseEntity<String> auth(){
        return ResponseEntity.ok("autorizado");
    }


    @PostMapping(value = {"/bcrypt"},consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation
    public ResponseEntity<ResponseJsonString> bcrypt(@RequestBody ConsumeJsonString consume){
        return ResponseEntity.ok(usuarioService.bcrypt(consume));
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
        validateConsume(consume);
        return ResponseEntity.ok(usuarioService.deleteUserByCveuser(consume.getId()));
    }

    @DeleteMapping(value = {"/deleteUsersByCveuser"}, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ResponseJsonString> deleteUserByCveuser(@RequestBody ConsumeJsonLongList consume) {
        validateConsume(consume);
        for (long id: consume.getIds()){
            usuarioService.deleteUserByCveuser(id);
        }

        ResponseJsonString response = new ResponseJsonString();
        response.setKey("usuarios eliminados exitosamente");

        return ResponseEntity.ok(response);
    }

    @PostMapping(value = {"/generateFakeUsers"}, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ResponseJsonString> generateFakeUsers(@RequestBody ConsumeJsonGeneric consume) {

        return ResponseEntity.ok(usuarioService.generateFakeUsersByRange(consume));
    }

    @PutMapping(value = {"/encryptAllUsersPassword"}, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ResponseJsonString> encryptAllUsersPassword() {
        return ResponseEntity.ok(usuarioService.encriptadorContrasenias());
    }
}
