package com.example.appfinalfinal;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.hardware.biometrics.BiometricManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.io.SyncFailedException;
import java.util.Calendar;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;

import okhttp3.*;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class RegistroPrron extends AppCompatActivity {
    String endpoint = "https://user-res-api.herokuapp.com/user/Registro";
    private DatePickerDialog datePickerDialog;
    Button botonfecha,botonregistrar;
    TextView txtVolver, prueba;
    EditText PrimerNombre,ApellidoPrimero,EmailUsuario,contrasena2,contrasena1;
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
        botonregistrar = (Button) findViewById(R.id.botonregistrar);
        PrimerNombre = (EditText) findViewById(R.id.PrimerNombre);
        ApellidoPrimero = (EditText) findViewById(R.id.ApellidoPrimero);
        EmailUsuario = (EditText) findViewById(R.id.EmailUsuario);
        contrasena2 = (EditText) findViewById(R.id.contrasena2);
        contrasena1 = (EditText) findViewById(R.id.contrasena1);

        String [] leNatioonalite = {"Argentina", "Bolivia", "Canada", "Colombia", "Costa Rica", "Chile", "Ecuador", "México", "Perú", "Estados Unidos", "Malasia"  };
        spinner1 = findViewById(R.id.spinnerNationality);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, leNatioonalite);
        spinner1.setAdapter(adapter);
        txtVolver.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(RegistroPrron.this, MainActivity.class);
                startActivity(i);
            }
        });
        botonregistrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String conuntry = spinner1.getSelectedItem().toString();
                String DOB = botonfecha.getText().toString();
                String intetento = getTodayDate();
                if (!PrimerNombre.getText().toString().equals("") && !ApellidoPrimero.getText().toString().equals("") && !EmailUsuario.getText().toString().equals("")
                        && !contrasena2.getText().toString().equals("") && !contrasena1.getText().toString().equals("") && !conuntry.equals("")) {
                    if (DOB.equals(intetento)){
                        Toast.makeText(RegistroPrron.this, "Selecciona una fecha valida", Toast.LENGTH_SHORT).show();
                    } else {
                    if (contrasena1.getText().toString().equals(contrasena2.getText().toString())){
                        RegisterUser regiser = new RegisterUser();
                        String name = PrimerNombre.getText().toString();
                        String lastname = ApellidoPrimero.getText().toString();
                        String email = EmailUsuario.getText().toString();
                        String pass1 = contrasena1.getText().toString();
                        regiser.execute(name, lastname,DOB,email,pass1,conuntry);
                    } else{
                        Toast.makeText(RegistroPrron.this, "Las contraseñas no coinciden", Toast.LENGTH_SHORT).show();
                    }
                    }
                } else {
                    Toast.makeText(RegistroPrron.this, "Esta faltando un elemento :c", Toast.LENGTH_SHORT).show();
                }

            }
        });
    }
    public static final MediaType JSON = MediaType.parse("application/json; charset=utf-8");
    public class RegisterUser extends AsyncTask<String, String, String> {
        @Override
        protected String doInBackground(String... strings) {
            String Name = strings[0];
            String LastName = strings[1];
            String DOB = strings[2];
            String email = strings[3];
            String pass = strings[4];
            String cuntry = strings[5];
            Boolean isAdmin = false;
            String postBody = "{\n" +
                    "\"name\" : \""+Name+"\",\n" +
                    "\"lastname\" : \""+LastName+"\",\n" +
                    "\"fecha\" : \""+DOB+"\",\n" +
                    "\"email\" : \""+email+"\",\n" +
                    "\"password\" : \""+pass+"\",\n" +
                    "\"nationality\" : \""+cuntry+"\",\n" +
                    "\"isAdmin\" : \""+isAdmin+"\"\n"+"}";
            System.out.println(postBody);

            RequestBody body = RequestBody.create(JSON, postBody);

            OkHttpClient client = new OkHttpClient();

            Request request = new Request.Builder()
                    .addHeader("Content-Type", "application/json; charset=utf8")
                    .url(endpoint)
                    .post(body)
                    .build();

            Response response = null;
            String result = " ";
            try {
                response = client.newCall(request).execute();
                System.out.println(response);

                if (response.isSuccessful()) {
                    result = response.body().string();
                    String finalResult = result;
                    System.out.println(finalResult);
                    RegistroPrron.this.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            System.out.println(finalResult);
                            if (finalResult.equals("true")){
                                Toast.makeText(RegistroPrron.this, "Usuario registrado correctamente", Toast.LENGTH_SHORT).show();
                                prueba.setText("+");
                                Intent i = new Intent(RegistroPrron.this, MainActivity.class);
                                startActivity(i);
                            }else{
                                prueba.setText("-");
                                Toast.makeText(RegistroPrron.this, "Error en registrar el usuario", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            return result;
        }
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
        return getMonthformat(month) + "/" + day + "/" + year;
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