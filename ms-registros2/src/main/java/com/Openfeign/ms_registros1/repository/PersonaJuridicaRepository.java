package com.Openfeign.ms_registros1.repository;

import com.Openfeign.ms_registros1.entity.PersonaJuridicaEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PersonaJuridicaRepository extends JpaRepository<PersonaJuridicaEntity,Long> {
}
