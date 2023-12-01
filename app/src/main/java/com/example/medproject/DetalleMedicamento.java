package com.example.medproject;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.medproject.entidad.Recordatorio;

public class DetalleMedicamento extends AppCompatActivity {
    public TextView nombre, dosis, descripcion;
    public ImageView imagen;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalle_medicamento);
        Recordatorio recordatorio = (Recordatorio) getIntent().getSerializableExtra("medicamentoSeleccionado");

        nombre = findViewById(R.id.detalleNombreMedicamento);
        dosis = findViewById(R.id.detalleDosis);
        descripcion = findViewById(R.id.detalleDescripcion);
        imagen = findViewById(R.id.detalleImagen);

        nombre.setText(recordatorio.getNombre());
        dosis.setText("Dosis: "+recordatorio.getDosis());

        if(recordatorio.getImagen() != null && !recordatorio.getImagen().isEmpty()){
            Glide.with(this).load(recordatorio.getImagen()).into(imagen);
        }

        if(recordatorio.getDescripcion() != null && !recordatorio.getDescripcion().isEmpty()){
            descripcion.setText(recordatorio.getDescripcion());
        }

    }
}