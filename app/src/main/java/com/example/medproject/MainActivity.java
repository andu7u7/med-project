package com.example.medproject;

import androidx.appcompat.app.AppCompatActivity;
import androidx.work.Data;

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

import com.example.medproject.worker.WManagerNotif;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.UUID;

public class MainActivity extends AppCompatActivity {
    EditText txtNombreMedicamento, txtIntervalo, txtDosis;
    TextView txtFechaInicio, txtHoraInicio, txtFechaFin;
    RadioGroup rgIntervalo;
    RadioButton rbIntervaloDias, rbIntervaloHoras;
    CheckBox cbCronico;

    Calendar f_actual = Calendar.getInstance(); //fecha actual
    Calendar calendar = Calendar.getInstance();

    private int hour, minute, day, month, year;

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

            String tag = generarId();
            Long HoraAlerta = calendar.getTimeInMillis() - System.currentTimeMillis();
            int random = (int) (Math.random() * 50 +1);

            Data data = guardarDatos("Hora de tu medicamento!", "Te toca ingerir '"+nombreMedicamento+"'", random);
            WManagerNotif.GuardarNotificacion(HoraAlerta, data, tag);


            Toast.makeText(getApplicationContext(), "Medicamento registrado!", Toast.LENGTH_LONG).show();
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
        year = f_actual.get(Calendar.YEAR);
        month = f_actual.get(Calendar.MONTH);
        day = f_actual.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog dpd = new DatePickerDialog(MainActivity.this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int y, int m, int d) {
                calendar.set(Calendar.DAY_OF_MONTH, d);
                calendar.set(Calendar.MONTH, m);
                calendar.set(Calendar.YEAR, y);

                SimpleDateFormat formato = new SimpleDateFormat("yyyy/MM/dd");
                String strFecha = formato.format(calendar.getTime());
                txt.setText(strFecha);
            }
        }, year, month, day);
        dpd.show();
    }

    public void abrirHora(View view){
        hour = f_actual.get(Calendar.HOUR_OF_DAY);
        minute = f_actual.get(Calendar.MINUTE);

        TimePickerDialog tpd = new TimePickerDialog(view.getContext(), new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker view, int h, int m) {
                calendar.set(Calendar.HOUR_OF_DAY, h);
                calendar.set(Calendar.MINUTE, m);

                txtHoraInicio.setText(String.format("%02d:%02d", h, m));
            }
        }, hour, minute, true);
        tpd.show();
    }

    private String generarId(){
        return UUID.randomUUID().toString();
    }

    private Data guardarDatos(String titulo, String detalle, int id_noti){
        return new Data.Builder()
                .putString("titulo", titulo)
                .putString("detalle", detalle)
                .putInt("id_noti", id_noti).build();
    }

}