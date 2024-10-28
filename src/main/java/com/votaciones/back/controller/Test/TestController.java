package com.votaciones.back.controller.Test;

import com.votaciones.back.model.exception.ResourceNotFoundException;
import com.votaciones.back.model.pojos.response.ResponseJsonGeneric;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping({"/test"})
@CrossOrigin(origins = {"*"})
public class TestController {

    @GetMapping(value = {"/Exception"}, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ResponseJsonGeneric> CreateOrUpdateEst() {
        throw new ResourceNotFoundException("No lo encuentro pa");
    }

}
