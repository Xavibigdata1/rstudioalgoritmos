package com.Openfeign.ms_registros1.service.impl;

import com.Openfeign.ms_registros1.aggregates.constans.Constants;
import com.Openfeign.ms_registros1.aggregates.response.ResponseSunat;
import com.Openfeign.ms_registros1.client.ClientSunat;
import com.Openfeign.ms_registros1.entity.PersonaJuridicaEntity;
import com.Openfeign.ms_registros1.redis.RedisService;
import com.Openfeign.ms_registros1.repository.PersonaJuridicaRepository;
import com.Openfeign.ms_registros1.service.PersonaJuridicaService;
import com.Openfeign.ms_registros1.util.Util;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.sql.Timestamp;
import java.util.Objects;

@Service
public class PersonaJuridicaServiceImpl implements PersonaJuridicaService {
    private final PersonaJuridicaRepository personaJuridicaRepository;
    private final ClientSunat clientSunat;
    private final RestTemplate restTemplate;
    private final RedisService redisService;


    @Value("${token.api}")
    private String token;

    public PersonaJuridicaServiceImpl(PersonaJuridicaRepository personaJuridicaRepository, ClientSunat clientSunat, RedisService redisService, RestTemplate restTemplate) {
        this.personaJuridicaRepository = personaJuridicaRepository;
        this.clientSunat = clientSunat;
        this.redisService = redisService;
        this.restTemplate = restTemplate;
    }


    @Override
    public PersonaJuridicaEntity guardar(String ruc) {
        PersonaJuridicaEntity personaJuridica=getEntityForRestTemplate(ruc);
        if(Objects.nonNull(personaJuridica)){
            return personaJuridicaRepository.save(personaJuridica);
        }else{
            return null;
        }

    }
    //ejecuto con este metodo de apoyo a mi entidad
    private PersonaJuridicaEntity getEntity(String ruc){
        //instancias
        PersonaJuridicaEntity personaJuridicaEntity=new PersonaJuridicaEntity();
        ResponseSunat datosSunat=new ResponseSunat();

        //recupero la información de redis
        String redisInfo=redisService.getDataFromRedis(ruc);
        //valido que exista la información
        if(Objects.nonNull(redisInfo)){
            datosSunat= Util.ConvertirDesdeString(redisInfo, ResponseSunat.class);
        }else{
            //sino existe la data en redis me voy a sunat api
            datosSunat=executionSunat(ruc);
            //convertido a String para poder guardarlo
            String dataForRedis=Util.convertirAString(datosSunat);
            //voy a guardar en redis
            redisService.saveInRedis(ruc,dataForRedis,Constants.REDIS_TTL);
        }
        //ejecuto sunat
        //ResponseSunat datosSunat=executionSunat(ruc);
        if(Objects.nonNull(datosSunat)){
            personaJuridicaEntity.setRazonSocial(datosSunat.getRazonSocial());
            personaJuridicaEntity.setTipoDocumento(datosSunat.getTipoDocumento());
            personaJuridicaEntity.setNumeroDocumento(datosSunat.getNumeroDocumento());
            personaJuridicaEntity.setEstado(datosSunat.getEstado());
            personaJuridicaEntity.setCondicion(datosSunat.getCondicion());
            personaJuridicaEntity.setDireccion(datosSunat.getDireccion());
            personaJuridicaEntity.setUbigeo(datosSunat.getUbigeo());
            personaJuridicaEntity.setViaTipo(datosSunat.getViaTipo());
            personaJuridicaEntity.setViaNombre(datosSunat.getViaNombre());
            personaJuridicaEntity.setZonaCodigo(datosSunat.getZonaCodigo());
            personaJuridicaEntity.setZonaTipo(datosSunat.getZonaTipo());
            personaJuridicaEntity.setNumero(datosSunat.getNumero());
            personaJuridicaEntity.setInterior(datosSunat.getInterior());
            personaJuridicaEntity.setLote(datosSunat.getLote());
            personaJuridicaEntity.setDpto(datosSunat.getDpto());
            personaJuridicaEntity.setManzana(datosSunat.getManzana());
            personaJuridicaEntity.setKilometro(datosSunat.getKilometro());
            personaJuridicaEntity.setDistrito(datosSunat.getDistrito());
            personaJuridicaEntity.setProvincia(datosSunat.getProvincia());
            personaJuridicaEntity.setDepartamento(datosSunat.getDepartamento());
            personaJuridicaEntity.setEsAgenteRetencion(datosSunat.isEsAgenteRetencion());
            personaJuridicaEntity.setTipo(datosSunat.getTipo());
            personaJuridicaEntity.setActividadEconomica(datosSunat.getActividadEconomica());
            personaJuridicaEntity.setNumeroTrabajadores(datosSunat.getNumeroTrabajadores());
            personaJuridicaEntity.setTipoFacturacion(datosSunat.getTipoFacturacion());
            personaJuridicaEntity.setTipoContabilidad(datosSunat.getTipoContabilidad());
            personaJuridicaEntity.setComercioExterior(datosSunat.getComercioExterior());
            personaJuridicaEntity.setEstado1(Constants.ESTADO_ACTIVO);
            personaJuridicaEntity.setUserCreated(Constants.USER_CREATED);
            personaJuridicaEntity.setDateCreated(new Timestamp(System.currentTimeMillis()));

        }
        return personaJuridicaEntity;
    }

    private PersonaJuridicaEntity getEntityForRestTemplate(String ruc){
        //instancias
        PersonaJuridicaEntity personaJuridicaEntity=new PersonaJuridicaEntity();
        //ResponseSunat datosSunat=new ResponseSunat();
        ResponseSunat datosSunat=executeRestTemplate(ruc);


        //recupero la información de redis
        String redisInfo=redisService.getDataFromRedis(ruc);
        //valido que exista la información
        if(Objects.nonNull(redisInfo)){
            datosSunat= Util.ConvertirDesdeString(redisInfo, ResponseSunat.class);
        }else{
            //sino existe la data en redis me voy a sunat api
            datosSunat=executionSunat(ruc);
            //convertido a String para poder guardarlo
            String dataForRedis=Util.convertirAString(datosSunat);
            //voy a guardar en redis
            redisService.saveInRedis(ruc,dataForRedis,Constants.REDIS_TTL);
        }
        //ejecuto sunat
        //ResponseSunat datosSunat=executionSunat(ruc);
        if(Objects.nonNull(datosSunat)){
            personaJuridicaEntity.setRazonSocial(datosSunat.getRazonSocial());
            personaJuridicaEntity.setTipoDocumento(datosSunat.getTipoDocumento());
            personaJuridicaEntity.setNumeroDocumento(datosSunat.getNumeroDocumento());
            personaJuridicaEntity.setEstado(datosSunat.getEstado());
            personaJuridicaEntity.setCondicion(datosSunat.getCondicion());
            personaJuridicaEntity.setDireccion(datosSunat.getDireccion());
            personaJuridicaEntity.setUbigeo(datosSunat.getUbigeo());
            personaJuridicaEntity.setViaTipo(datosSunat.getViaTipo());
            personaJuridicaEntity.setViaNombre(datosSunat.getViaNombre());
            personaJuridicaEntity.setZonaCodigo(datosSunat.getZonaCodigo());
            personaJuridicaEntity.setZonaTipo(datosSunat.getZonaTipo());
            personaJuridicaEntity.setNumero(datosSunat.getNumero());
            personaJuridicaEntity.setInterior(datosSunat.getInterior());
            personaJuridicaEntity.setLote(datosSunat.getLote());
            personaJuridicaEntity.setDpto(datosSunat.getDpto());
            personaJuridicaEntity.setManzana(datosSunat.getManzana());
            personaJuridicaEntity.setKilometro(datosSunat.getKilometro());
            personaJuridicaEntity.setDistrito(datosSunat.getDistrito());
            personaJuridicaEntity.setProvincia(datosSunat.getProvincia());
            personaJuridicaEntity.setDepartamento(datosSunat.getDepartamento());
            personaJuridicaEntity.setEsAgenteRetencion(datosSunat.isEsAgenteRetencion());
            personaJuridicaEntity.setTipo(datosSunat.getTipo());
            personaJuridicaEntity.setActividadEconomica(datosSunat.getActividadEconomica());
            personaJuridicaEntity.setNumeroTrabajadores(datosSunat.getNumeroTrabajadores());
            personaJuridicaEntity.setTipoFacturacion(datosSunat.getTipoFacturacion());
            personaJuridicaEntity.setTipoContabilidad(datosSunat.getTipoContabilidad());
            personaJuridicaEntity.setComercioExterior(datosSunat.getComercioExterior());
            personaJuridicaEntity.setEstado1(Constants.ESTADO_ACTIVO);
            personaJuridicaEntity.setUserCreated(Constants.USER_CREATED);
            personaJuridicaEntity.setDateCreated(new Timestamp(System.currentTimeMillis()));

        }
        return personaJuridicaEntity;
    }
    //ejecutando la consulta externa
    private ResponseSunat executionSunat(String ruc){
        //String tokenOk="Bearer "+token;
        String tokenOk=Constants.BEARER+token;
        return clientSunat.getPersonaSunat(ruc,tokenOk);
    }
    private ResponseSunat executeRestTemplate(String ruc){
        //configuro una url completa como un String
        String url="https://api.apis.net.pe/v2/sunat/ruc/full?numero="+ruc;
        // genero mi client restTemplate y ejecuto
        ResponseEntity<ResponseSunat>executeRestTemplate=restTemplate.exchange(
                url,
                HttpMethod.GET,//ejecutar el  tipo de solicitud al que pertenece la url
                new HttpEntity<>(createHeaders()),//cabeceras
                ResponseSunat.class//response a castaer
        );
        if(executeRestTemplate.getStatusCode().equals(HttpStatus.OK)){
            return executeRestTemplate.getBody();
        }else{
            return null;
        }
    }
    private HttpHeaders createHeaders(){
        HttpHeaders headers=new HttpHeaders();
        headers.set("Authorization","Bearer "+token);
        return headers;
    }

}
