package com.Openfeign.ms_registros1.service;

import com.Openfeign.ms_registros1.entity.PersonaJuridicaEntity;

import java.io.IOException;

public interface PersonaJuridicaService {
    PersonaJuridicaEntity guardar(String ruc) throws IOException;
}
