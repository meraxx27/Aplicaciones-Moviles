package com.example.appfinalfinal;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.button.MaterialButton;
import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.text.DateFormat;



import okhttp3.*;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class MainActivity extends AppCompatActivity {

    EditText emailusuario, contrasena;
    TextView Noregistro;
    Button botoningreso;
    TextView resultado;
    String endpoint = "https://user-res-api.herokuapp.com/user/checkUser";
    String endpoint2 = "https://user-res-api.herokuapp.com/user/getUser";
    CheckBox keepsesion;
    SharedPreferences preferences;
    SharedPreferences.Editor editor;
    String llave = "sesion";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getSupportActionBar().hide();


        Noregistro = (TextView) findViewById(R.id.Noregistro);
        emailusuario = (EditText) findViewById(R.id.emailusuario);
        contrasena = (EditText) findViewById(R.id.contrasena);
        resultado = (TextView) findViewById(R.id.resultado);
        botoningreso = (Button) findViewById(R.id.botoningreso);

        botoningreso.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!emailusuario.getText().toString().equals("") && !contrasena.getText().toString().equals("")) {
                    String Email = emailusuario.getText().toString();
                    String password = contrasena.getText().toString();
                    LoginUser login = new LoginUser();
                    login.execute(Email, password);
                } else {
                    Toast.makeText(MainActivity.this, "Esta faltando un elemento :c", Toast.LENGTH_SHORT).show();
                }
            }
        });
        Noregistro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(MainActivity.this, RegistroPrron.class);
                startActivity(i);
            }
        });
    }

    public static final MediaType JSON = MediaType.parse("application/json; charset=utf-8");

    public class LoginUser extends AsyncTask<String, String, String> {
        @Override
        protected String doInBackground(String... strings) {
            String Email = strings[0];
            String Password = strings[1];
            String postBody = "{\n   \"email\" : \""+Email+"\",\n   \"password\" : \""+Password+"\"\n}";

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

                if (response.isSuccessful()) {
                    result = response.body().string();
                    String finalResult = result;
                    MainActivity.this.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            System.out.println(finalResult);
                            if (finalResult.equals("true")){
                                Toast.makeText(MainActivity.this, "Log in Exitoso", Toast.LENGTH_SHORT).show();
                                resultado.setText("El usuario si existe");
                                Intent i = new Intent(MainActivity.this, LoginConseguido.class);
                                getUserxd getuserxd = new getUserxd();
                                getuserxd.execute(Email);
                                startActivity(i);

                            }else{
                                resultado.setText("Incorrecto");
                                Toast.makeText(MainActivity.this, "Error en usuario o contraseña", Toast.LENGTH_SHORT).show();
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
    public class getUserxd extends AsyncTask<String, String, String>{

        @Override
        protected String doInBackground(String... strings) {
            String Email = strings[0];
            String postBody = "{\n   \"email\" : \""+Email+"\"\n}";
            RequestBody body = RequestBody.create(JSON, postBody);

            OkHttpClient client = new OkHttpClient();

            Request request = new Request.Builder()
                    .addHeader("Content-Type", "application/json; charset=utf8")
                    .url(endpoint2)
                    .post(body)
                    .build();

            Response response = null;
            String result = " ";
            try {
                response = client.newCall(request).execute();

                if (response.isSuccessful()) {
                    result = response.body().string();
                    String finalResult = result;
                    JSONObject json = new JSONObject(finalResult);
                    System.out.println(json);
                    String username = json.getString("name");
                    String userlastname = json.getString("lastname");
                    String useremail = json.getString("email");
                    String userdob = json.getString("fecha");
                    String userAdmin = json.getString("isAdmin");
                    String usernationality = json.getString("nationality");
                    SharedPreferences sharedPreferences = getSharedPreferences("KafrezzedeOreoChico", Context.MODE_PRIVATE);
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.putString("name", username);
                    editor.putString("lastname", userlastname);
                    editor.putString("email", useremail);
                    editor.putString("fecha", userdob);
                    editor.putString("nationality", usernationality);
                    editor.putString("isAdmin", userAdmin);
                    editor.commit();
                    System.out.println(sharedPreferences.getString("isAdmin", ""));
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }
    }
}