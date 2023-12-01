package com.example.medproject.services;

import com.example.medproject.entidad.Medicamento;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface MedicamentoService {

    @GET("medicamentos")
    Call<Medicamento> getAll(@Query("name") String name);
}
