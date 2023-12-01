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

import com.example.medproject.adaptador.ListaMedicamentosAdapter;
import com.example.medproject.entidad.Recordatorio;

import java.util.ArrayList;
import java.util.List;

public class ListaRecordatorio extends AppCompatActivity {
    List<Recordatorio> medicamentos;
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

    public ArrayList<Recordatorio> mostrarRecordatorios() {
        AdminSQLiteOpenHelper admin = new AdminSQLiteOpenHelper(this, "med_project.db", null, 1);
        SQLiteDatabase db = admin.getWritableDatabase();
        ArrayList<Recordatorio> listaRecordatorios = new ArrayList<>();
        Recordatorio recordatorio = null;
        Cursor cursorRecordatorio = null;

        cursorRecordatorio = db.rawQuery("SELECT * FROM medicamentos",null);

        if (cursorRecordatorio.moveToFirst()){
            do {
                recordatorio = new Recordatorio();
                recordatorio.setId(cursorRecordatorio.getInt(0));
                recordatorio.setNombre(cursorRecordatorio.getString(1));
                recordatorio.setDosis(cursorRecordatorio.getInt(2));
                recordatorio.setIntervalo(cursorRecordatorio.getInt(3));
                recordatorio.setFecha_inicio(cursorRecordatorio.getString(4));
                recordatorio.setHora_inicio(cursorRecordatorio.getString(5));
                recordatorio.setFecha_fin(cursorRecordatorio.getString(6));
                recordatorio.setImagen(cursorRecordatorio.getString(7));
                recordatorio.setDescripcion(cursorRecordatorio.getString(8));
                listaRecordatorios.add(recordatorio);
            } while (cursorRecordatorio.moveToNext());
        }

        cursorRecordatorio.close();
        return listaRecordatorios;
    }

    public void init(){
        ArrayList<Recordatorio> listaRecordatorios = mostrarRecordatorios();
        for (Recordatorio recordatorio : listaRecordatorios) {
            Log.d("MiApp", "Nombre: " + recordatorio.getNombre() + ", Dosis: " + recordatorio.getDosis() + ", Link: " + recordatorio.getImagen());
        }
        ListaMedicamentosAdapter listaAdapter = new ListaMedicamentosAdapter(mostrarRecordatorios(), this);
        RecyclerView recyclerView = findViewById(R.id.listaRecetas);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(listaAdapter);
    }
}