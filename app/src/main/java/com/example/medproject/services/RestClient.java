package com.example.medproject.services;

import com.example.medproject.entidad.Medicamento;
import com.example.medproject.entidad.MedicamentoData;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RestClient {

    static final String BASE_URL ="https://api-med-project.onrender.com/api/";
    private static RestClient instance;
    private Retrofit retrofit;
    private MedicamentoService medicamentoApi;

    private RestClient() {
        retrofit = new Retrofit.Builder().baseUrl(BASE_URL).addConverterFactory(GsonConverterFactory.create()).build();
        medicamentoApi = retrofit.create(MedicamentoService.class);
    }

    public static RestClient getInstance(){
        if(instance == null){
            instance = new RestClient();
        }
        return instance;
    }

    public void getMedicine(String name, Callback<Medicamento> callback){
        Call<Medicamento> call = medicamentoApi.getAll(name);
        call.enqueue(callback);
    }
}
