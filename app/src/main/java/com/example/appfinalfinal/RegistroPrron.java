package com.example.appfinalfinal;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.Calendar;

public class RegistroPrron extends AppCompatActivity {
    String url = "https://user-res-api.herokuapp.com/user/Registro";
    private DatePickerDialog datePickerDialog;
    Button botonfecha,botonregistrar;
    TextView txtVolver, prueba;
    private Spinner spinner1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro_prron);
        getSupportActionBar().hide();

        initDatePicker();
        prueba = (TextView) findViewById(R.id.prueba);
        txtVolver = (TextView) findViewById(R.id.txtVolver);
        botonfecha = findViewById(R.id.botonfecha);
        botonfecha.setText(getTodayDate());


        String [] leNatioonalite = {"Argentina", "Bolivia", "Canada", "Colombia", "Costa Rica", "Chile", "Ecuador", "México", "Perú", "Estados Unidos", "Malasia"  };
        spinner1 = findViewById(R.id.spinnerNationality);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, leNatioonalite);
        spinner1.setAdapter(adapter);
        prueba.setText(spinner1.getSelectedItem().toString());


        txtVolver.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(RegistroPrron.this, MainActivity.class);
                startActivity(i);
            }
        });
    }
    private String getTodayDate() {
        Calendar cal = Calendar.getInstance();
        int year = cal.get(Calendar.YEAR);
        int month = cal.get(Calendar.MONTH);
        month = month+1;
        int day = cal.get(Calendar.DAY_OF_MONTH);
        return makeDateString(day, month, year);
    }
    private void initDatePicker() {
        DatePickerDialog.OnDateSetListener dateSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                month = month+1;
                String date = makeDateString(day, month, year);
                botonfecha.setText(date);
            }
        };
        Calendar cal = Calendar.getInstance();
        int year = cal.get(Calendar.YEAR);
        int month = cal.get(Calendar.MONTH);
        int day = cal.get(Calendar.DAY_OF_MONTH);

        int style = 0;
        if (android.os.Build.VERSION.SDK_INT >= 21) {
            style = android.R.style.Theme_Material_Light_Dialog;
        }


        datePickerDialog = new DatePickerDialog(this, style, dateSetListener, year, month, day);
        datePickerDialog.getDatePicker().setMaxDate(System.currentTimeMillis());

    }
    private String makeDateString(int day, int month, int year) {
        return getMonthformat(month) + " " + day + " " + year;
    }
    private String getMonthformat(int month) {
        if (month == 1)
            return "Ene";
        if (month == 2)
            return "Feb";
        if (month == 3)
            return "Mar";
        if (month == 4)
            return "Abr";
        if (month == 5)
            return "May";
        if (month == 6)
            return "Jun";
        if (month == 7)
            return "Jul";
        if (month == 8)
            return "Ago";
        if (month == 9)
            return "Sep";
        if (month == 10)
            return "Oct";
        if (month == 11)
            return "Nov";
        if (month == 12)
            return "Dic";

        return "Ene";
    }
    public void OpenDatePicker(View view) {
        datePickerDialog.show();
    }
}