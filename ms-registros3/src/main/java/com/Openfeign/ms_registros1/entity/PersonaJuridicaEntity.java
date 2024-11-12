package com.Openfeign.ms_registros1.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.sql.Timestamp;

@Entity
@Table(name="empresa")
@Getter
@Setter
public class PersonaJuridicaEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String razonSocial;
    private String tipoDocumento;
    private String numeroDocumento;
    private String estado;
    private String condicion;
    private String direccion;
    private String ubigeo;
    private String viaTipo;
    private String viaNombre;
    private String zonaCodigo;
    private String zonaTipo;
    private String numero;
    private String interior;
    private String lote;
    private String dpto;
    private String manzana;
    private String kilometro;
    private String distrito;
    private String provincia;
    private String departamento;
    private boolean esAgenteRetencion;
    private String tipo;
    private String actividadEconomica;
    private String numeroTrabajadores;
    private String tipoFacturacion;
    private String tipoContabilidad;
    private String comercioExterior;
    private int estado1;
    private String userCreated;
    private Timestamp dateCreated;

    @Override
    public String toString() {
        return "PersonaJuridicaEntity{" +
                "id=" + id +
                ", razonSocial='" + razonSocial + '\'' +
                ", tipoDocumento='" + tipoDocumento + '\'' +
                ", numeroDocumento='" + numeroDocumento + '\'' +
                ", estado='" + estado + '\'' +
                ", condicion='" + condicion + '\'' +
                ", direccion='" + direccion + '\'' +
                ", ubigeo='" + ubigeo + '\'' +
                ", viaTipo='" + viaTipo + '\'' +
                ", viaNombre='" + viaNombre + '\'' +
                ", zonaCodigo='" + zonaCodigo + '\'' +
                ", zonaTipo='" + zonaTipo + '\'' +
                ", numero='" + numero + '\'' +
                ", interior='" + interior + '\'' +
                ", lote='" + lote + '\'' +
                ", dpto='" + dpto + '\'' +
                ", manzana='" + manzana + '\'' +
                ", kilometro='" + kilometro + '\'' +
                ", distrito='" + distrito + '\'' +
                ", provincia='" + provincia + '\'' +
                ", departamento='" + departamento + '\'' +
                ", esAgenteRetencion=" + esAgenteRetencion +
                ", tipo='" + tipo + '\'' +
                ", actividadEconomica='" + actividadEconomica + '\'' +
                ", numeroTrabajadores='" + numeroTrabajadores + '\'' +
                ", tipoFacturacion='" + tipoFacturacion + '\'' +
                ", tipoContabilidad='" + tipoContabilidad + '\'' +
                ", comercioExterior='" + comercioExterior + '\'' +
                ", estado1=" + estado1 +
                ", userCreated='" + userCreated + '\'' +
                ", dateCreated=" + dateCreated +
                '}';
    }
}
