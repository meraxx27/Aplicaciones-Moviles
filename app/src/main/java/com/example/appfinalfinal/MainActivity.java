package com.example.appfinalfinal;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import com.google.android.material.button.MaterialButton;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;

public class MainActivity extends AppCompatActivity {

    EditText emailusuario, contrasena;
    TextView Noregistro;
    Button botoningreso;
    TextView resultado;
    String endpoint = " https://user-res-api.herokuapp.com/user/checkUser";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getSupportActionBar().hide();

        emailusuario = (EditText) findViewById(R.id.emailusuario);
        contrasena = (EditText) findViewById(R.id.contrasena);
        Noregistro = (TextView) findViewById(R.id.Noregistro);
        botoningreso = (Button) findViewById(R.id.botoningreso);
        botoningreso.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!emailusuario.getText().toString().equals("") && !contrasena.getText().toString().equals(""))
                {
                    String[] credenciales = {emailusuario.getText().toString().trim(), contrasena.getText().toString().trim(), endpoint};
                    API api = new API();
                    api.execute(credenciales);
                }
                else
                {
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
    private class API extends AsyncTask<String, String, String>{
        @Override
        protected String doInBackground(String... credenciales){
            String respuesta = "";
            String username = credenciales[0];
            String password = credenciales[1];
            String endpoint = credenciales[2];
            try {
                URL url = new URL(endpoint);
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                conn.setRequestMethod("POST");
                conn.setRequestProperty("Content-Type", "application/json");
                conn.setRequestProperty("Accept", "*/*");
                conn.setDoOutput(true);
                String payload = "{\n "+username+"\",\n  \""+password+"\"\n}";
                try (OutputStream os = conn.getOutputStream())
                {
                    byte[] input = payload.getBytes(StandardCharsets.UTF_8);
                    os.write(input, 0, input.length);
                }
                try (BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream(), StandardCharsets.UTF_8)))
                {
                    StringBuilder resp = new StringBuilder();
                    String respLine = null;
                    while ((respLine = br.readLine()) != null)
                    {
                        resp.append(respLine.toString());
                    }
                    respuesta = resp.toString();
                }
            }
            catch (Exception e)
            {
                e.printStackTrace();
            }
            return respuesta;
        }

        @Override
        protected void onPostExecute(String respuesta)
        {
            try
            {
                JSONObject json = new JSONObject(respuesta);
                resultado.setText(json.getString("status"));
            }
            catch (Exception e)
            {
                e.printStackTrace();
            }
        }

    }

}