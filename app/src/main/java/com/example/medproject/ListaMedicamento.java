package com.example.medproject;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.example.medproject.adaptador.ListaMedicamentosAdapter;
import com.example.medproject.entidad.Medicamento;

import java.util.ArrayList;
import java.util.List;

public class ListaMedicamento extends AppCompatActivity {
    List<Medicamento> medicamentos;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista_medicamento);
        init();
    }

    public void IrARegistrarReceta(View view) {
        Intent registraReceta = new Intent(this, MainActivity.class);
        startActivity(registraReceta);
    }

    public ArrayList<Medicamento> mostrarMedicamentos() {
        AdminSQLiteOpenHelper admin = new AdminSQLiteOpenHelper(this, "med_project.db", null, 1);
        SQLiteDatabase db = admin.getWritableDatabase();
        ArrayList<Medicamento> listaMedicamentos = new ArrayList<>();
        Medicamento medicamento = null;
        Cursor cursosMedicamentos = null;

        cursosMedicamentos = db.rawQuery("SELECT * FROM medicamentos",null);

        if (cursosMedicamentos.moveToFirst()){
            do {
                medicamento = new Medicamento();
                medicamento.setId(cursosMedicamentos.getInt(0));
                medicamento.setNombre(cursosMedicamentos.getString(1));
                medicamento.setDosis(cursosMedicamentos.getInt(2));
                medicamento.setIntervalo(cursosMedicamentos.getInt(3));
                medicamento.setFecha_inicio(cursosMedicamentos.getString(4));
                medicamento.setHora_inicio(cursosMedicamentos.getString(5));
                medicamento.setFecha_fin(cursosMedicamentos.getString(6));
                listaMedicamentos.add(medicamento);
            } while (cursosMedicamentos.moveToNext());
        }

        cursosMedicamentos.close();
        return listaMedicamentos;
    }

    public void init(){
        ArrayList<Medicamento> listaMedicamentos = mostrarMedicamentos();
        for (Medicamento medicamento : listaMedicamentos) {
            Log.d("MiApp", "Nombre: " + medicamento.getNombre() + ", Dosis: " + medicamento.getDosis());
        }
        ListaMedicamentosAdapter listaAdapter = new ListaMedicamentosAdapter(mostrarMedicamentos(), this);
        RecyclerView recyclerView = findViewById(R.id.listaRecetas);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(listaAdapter);
    }
}