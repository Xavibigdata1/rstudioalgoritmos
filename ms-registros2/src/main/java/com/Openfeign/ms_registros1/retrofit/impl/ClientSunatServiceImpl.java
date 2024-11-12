package com.Openfeign.ms_registros1.retrofit.impl;

import com.Openfeign.ms_registros1.aggregates.constans.Constants;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ClientSunatServiceImpl {

    private static String BASE_URL= Constants.BASE_URL;
    private static Retrofit retrofit=null;
    public static Retrofit getRetrofit(){
        if(retrofit==null){
            retrofit=new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }

        return retrofit;
    }
}
