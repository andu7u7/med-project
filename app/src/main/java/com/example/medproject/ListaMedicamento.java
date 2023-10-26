package com.example.medproject;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class ListaMedicamento extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista_medicamento);
    }

    public void IrARegistrarReceta(View view) {
        Intent registraReceta = new Intent(this, MainActivity.class);
        startActivity(registraReceta);
    }
}