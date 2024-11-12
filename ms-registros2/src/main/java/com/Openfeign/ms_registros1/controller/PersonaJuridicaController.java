package com.Openfeign.ms_registros1.controller;

import com.Openfeign.ms_registros1.entity.PersonaJuridicaEntity;
import com.Openfeign.ms_registros1.service.PersonaJuridicaService;
import org.antlr.v4.runtime.misc.NotNull;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@RestController
@RequestMapping("/v1/personaJuridica")
public class PersonaJuridicaController {
    private final PersonaJuridicaService personaJuridicaService;

    public PersonaJuridicaController(PersonaJuridicaService personaJuridicaService) {
        this.personaJuridicaService = personaJuridicaService;
    }

    @PostMapping
    public ResponseEntity<PersonaJuridicaEntity> guardarPersona(
            @RequestParam("ruc")String ruc) throws IOException {
        PersonaJuridicaEntity personaJuridica= personaJuridicaService.guardar(ruc);
        return new ResponseEntity<>(personaJuridica, HttpStatus.CREATED);
    }
}
