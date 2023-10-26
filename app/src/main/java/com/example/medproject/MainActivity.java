package com.example.medproject;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.util.Calendar;

public class MainActivity extends AppCompatActivity {

    Button btnRegresar, btnRegistrar;
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

        txtFechaInicio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                abrirCalendario(txtFechaInicio);
            }
        });

        txtFechaFin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                abrirCalendario(txtFechaFin);
            }
        });

        cbCronico.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                txtFechaFin.setEnabled(!isChecked);
            }
        });
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

    public void abrirCalendario(TextView txt) {
        Calendar cal = Calendar.getInstance();
        int year = cal.get(Calendar.YEAR);
        int month = cal.get(Calendar.MONTH);
        int day = cal.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog dpd = new DatePickerDialog(MainActivity.this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                String fecha =  year + "/" + (month + 1) + "/" + dayOfMonth;
                txt.setText(fecha);
            }
        }, year, month, day);
        dpd.show();
    }

    public void abrirHora(View view){
        Calendar cal = Calendar.getInstance();
        int hour = cal.get(Calendar.HOUR_OF_DAY);
        int minute = cal.get(Calendar.MINUTE);

        TimePickerDialog tpd = new TimePickerDialog(MainActivity.this, new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                String hora = hourOfDay + ":" + minute;
                txtHoraInicio.setText(hora);
            }
        }, hour, minute, true);
        tpd.show();
    }
}