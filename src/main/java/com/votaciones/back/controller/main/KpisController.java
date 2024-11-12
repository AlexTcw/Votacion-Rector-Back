package com.votaciones.back.controller.main;

import com.votaciones.back.service.kpis.KpisService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@PreAuthorize("hasRole('ADMIN')")
@CrossOrigin(origins = {"*"})
@RequestMapping(value = "/kpis")
public class KpisController {

    private final KpisService kpisService;

    public KpisController(KpisService kpisService) {
        this.kpisService = kpisService;
    }
}
