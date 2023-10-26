package com.example.medproject;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import java.util.Calendar;

public class MainActivity extends AppCompatActivity {
    EditText txtNombreMedicamento, txtIntervalo, txtDosis;
    TextView txtFechaInicio, txtHoraInicio, txtFechaFin;
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

        txtFechaInicio.setOnClickListener(v -> abrirCalendario(txtFechaInicio));

        txtFechaFin.setOnClickListener(v -> abrirCalendario(txtFechaFin));

        cbCronico.setOnCheckedChangeListener((buttonView, isChecked) -> txtFechaFin.setEnabled(!isChecked));
    }

    public void IrListaRecetas(View view) {
        Intent listarRecetas = new Intent(this, ListaMedicamento.class);
        startActivity(listarRecetas);
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
            Limpiar();
        } catch (Exception e) {
            String error = e.getMessage();
            Toast.makeText(getApplicationContext(), error, Toast.LENGTH_LONG).show();
        }
    }

    public void Limpiar() {
        txtNombreMedicamento.setText("");
        txtHoraInicio.setText("hh:mm");
        txtFechaInicio.setText("yyyy/mm/dd");
        txtIntervalo.setText("");
        txtDosis.setText("");
        txtFechaFin.setText("yyyy/mm/dd");
        rgIntervalo.clearCheck();
        cbCronico.setChecked(false);
    }

    public void abrirCalendario(TextView txt) {
        Calendar cal = Calendar.getInstance();
        int year = cal.get(Calendar.YEAR);
        int month = cal.get(Calendar.MONTH);
        int day = cal.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog dpd = new DatePickerDialog(MainActivity.this, (view, year1, month1, dayOfMonth) -> {
            String fecha =  year1 + "/" + (month1 + 1) + "/" + dayOfMonth;
            txt.setText(fecha);
        }, year, month, day);
        dpd.show();
    }

    public void abrirHora(View view){
        Calendar cal = Calendar.getInstance();
        int hour = cal.get(Calendar.HOUR_OF_DAY);
        int minute = cal.get(Calendar.MINUTE);

        TimePickerDialog tpd = new TimePickerDialog(MainActivity.this, (view1, hourOfDay, minute1) -> {
            String hora = hourOfDay + ":" + minute1;
            txtHoraInicio.setText(hora);
        }, hour, minute, true);
        tpd.show();
    }
}