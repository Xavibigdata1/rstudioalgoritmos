package com.Openfeign.ms_registros1.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class Util {
    private static final ObjectMapper OBJECT_MAPPER=new ObjectMapper();

    public static <T> String convertirAString(T objeto){
        try{
            return OBJECT_MAPPER.writeValueAsString(objeto);
        }catch (JsonProcessingException e){
            throw new RuntimeException("Error al convertir la clases a cadena (String)"+e);
        }
    }
    public static <T>T ConvertirDesdeString(String  json, Class<T> tipoClase){
        try{
            return OBJECT_MAPPER.readValue(json, tipoClase);
        }catch (JsonProcessingException e){
            throw new RuntimeException("error al convertir desde String a la clases: "+e);
        }
    }

}
