package com.Openfeign.ms_registros1.service.impl;

import com.Openfeign.ms_registros1.aggregates.constans.Constants;
import com.Openfeign.ms_registros1.aggregates.response.ResponseSunat;
import com.Openfeign.ms_registros1.client.ClientSunat;
import com.Openfeign.ms_registros1.entity.PersonaJuridicaEntity;
import com.Openfeign.ms_registros1.redis.RedisService;
import com.Openfeign.ms_registros1.repository.PersonaJuridicaRepository;
import com.Openfeign.ms_registros1.retrofit.ClientSunatService;
import com.Openfeign.ms_registros1.retrofit.impl.ClientSunatServiceImpl;
import com.Openfeign.ms_registros1.service.PersonaJuridicaService;
import com.Openfeign.ms_registros1.util.Util;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import retrofit2.Call;
import retrofit2.Response;

import java.io.IOException;
import java.sql.Timestamp;
import java.util.Objects;

@Service
public class PersonaJuridicaServiceImpl implements PersonaJuridicaService {
    private final PersonaJuridicaRepository personaJuridicaRepository;
    private final ClientSunat clientSunat;

    ClientSunatService sunatServiceRetrofit= ClientSunatServiceImpl
            .getRetrofit()
            .create(ClientSunatService.class);
    private final RedisService redisService;


    @Value("${token.api}")
    private String token;

    public PersonaJuridicaServiceImpl(PersonaJuridicaRepository personaJuridicaRepository, ClientSunat clientSunat, RedisService redisService) {
        this.personaJuridicaRepository = personaJuridicaRepository;
        this.clientSunat = clientSunat;
        this.redisService = redisService;
    }


    @Override
    public PersonaJuridicaEntity guardar(String ruc) throws IOException{
        PersonaJuridicaEntity personaJuridica=getEntity(ruc);
        if(Objects.nonNull(personaJuridica)){
            return personaJuridicaRepository.save(personaJuridica);
        }else{
            return null;
        }

    }
    //ejecuto con este metodo de apoyo a mi entidad
    private PersonaJuridicaEntity getEntity(String ruc) throws IOException {
        //instancias
        PersonaJuridicaEntity personaJuridicaEntity=new PersonaJuridicaEntity();
        //ejecutando mis datos con onpefeign
        //ResponseSunat datosSunat=new ResponseSunat();
         //-----
        //preparo a sunat usando retrofit
        Call<ResponseSunat>clientRetrofit= prepareSunatRetrofit(ruc);
        //Ejecuto a sunat usando a retrofit
        Response<ResponseSunat>executeSunat=clientRetrofit.execute();
        //validar el resultado
        ResponseSunat datosSunat=null;
        if(executeSunat.isSuccessful()&& Objects.nonNull(executeSunat.body())){
            datosSunat=executeSunat.body();
        }

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
        //ejecuto sunat ejecutando OpenFeign
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
    //ejecutando la consulta externa por medio de openfeign de sunat
    private ResponseSunat executionSunat(String ruc){
        //String tokenOk="Bearer "+token;
        String tokenOk=Constants.BEARER+token;
        return clientSunat.getPersonaSunat(ruc,tokenOk);
    }
    //Metodo que ejecuta el client Retrofit de sunat
    private Call<ResponseSunat> prepareSunatRetrofit(String ruc){
        String tokenComplete="Bearer "+token;
        return sunatServiceRetrofit.getInfoSunat(tokenComplete,ruc);
    }

}
