package com.example.medproject;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    Button btnRegresar, btnFechaInicio, btnHoraInicio, btnFechaFin, btnRegistrar;
    EditText txtNombreMedicamento, txtHoraInicio, txtFechaInicio, txtIntervalo, txtDosis, txtFechaFin;
    RadioGroup rgIntervalo;
    RadioButton rbIntervaloDias, rbIntervaloHoras;
    CheckBox cbCronico;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        txtNombreMedicamento = findViewById(R.id.txtNombreMedicamento);
        txtHoraInicio = findViewById(R.id.txtHoraInicio);
        txtFechaInicio = findViewById(R.id.txtFechaInicio);
        txtIntervalo = findViewById(R.id.txtIntervalo);
        rgIntervalo = findViewById(R.id.rgIntervalo);
        rbIntervaloDias = findViewById(R.id.rbIntervaloDias);
        rbIntervaloHoras = findViewById(R.id.rbIntervaloHoras);
        txtDosis = findViewById(R.id.cantDosis);
        txtFechaFin = findViewById(R.id.txtFechaFin);
        cbCronico = findViewById(R.id.cbCronico);
    }

    public void registrar(View view) {
        try {
            AdminSQLiteOpenHelper admin = new AdminSQLiteOpenHelper(this, "med_project.db", null, 1);
            SQLiteDatabase bd = admin.getWritableDatabase();
            String nombreMedicamento = txtNombreMedicamento.getText().toString();
            String horaInicio = txtHoraInicio.getText().toString();
            String fechaInicio = txtFechaInicio.getText().toString();
            int intervalo = Integer.parseInt(txtIntervalo.getText().toString());
            int dosis = Integer.parseInt(txtDosis.getText().toString());
            String fechaFin = txtFechaFin.getText().toString();

            if (rbIntervaloDias.isChecked()) {
                intervalo *= 24;
            }
            if (cbCronico.isChecked()) {
                fechaFin = null;
            }

            ContentValues registro = new ContentValues();
            registro.put("nombre", nombreMedicamento);
            registro.put("dosis", dosis);
            registro.put("hora_inicio", horaInicio);
            registro.put("fecha_inicio", fechaInicio);
            registro.put("fecha_fin", fechaFin);
            registro.put("intervalo", intervalo);
            bd.insert("medicamentos", null, registro);
            bd.close();

            Toast.makeText(getApplicationContext(), "Registro correcto!", Toast.LENGTH_LONG).show();
            // Limpiar();
        } catch (Exception e) {
            String error = e.getMessage();
            Toast.makeText(getApplicationContext(), error, Toast.LENGTH_LONG).show();
        }
    }

    public void Limpiar() {
        txtNombreMedicamento.setText("");
        txtHoraInicio.setText("");
        txtFechaInicio.setText("");
        txtIntervalo.setText("");
        txtDosis.setText("");
        txtFechaFin.setText("");
    }
}