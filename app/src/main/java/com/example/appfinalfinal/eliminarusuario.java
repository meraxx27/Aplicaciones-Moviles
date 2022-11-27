package com.example.appfinalfinal;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import kotlin.contracts.Returns;
import okhttp3.MediaType;
import okhttp3.*;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class eliminarusuario extends AppCompatActivity {
    String endpoint = "https://user-res-api.herokuapp.com/user/deleteUser";
    String endpoint2 = "https://user-res-api.herokuapp.com/user/fetchUsers";
    Button btnvolver,btnborrar;
    EditText emailus1,emailus2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_eliminarusuario);
        getSupportActionBar().hide();

        btnvolver = (Button) findViewById(R.id.btnvolver);
        btnborrar = (Button) findViewById(R.id.btnborrar);
        emailus1 = (EditText) findViewById(R.id.emailus1);
        emailus2 = (EditText) findViewById(R.id.emailus2);

        btnvolver.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(eliminarusuario.this, LoginConseguido.class);
                startActivity(i);
            }
        });
        btnborrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!emailus1.getText().toString().equals("") && !emailus2.getText().toString().equals("")) {
                    if (emailus1.getText().toString().equals(emailus2.getText().toString())){
                        String Email = emailus1.getText().toString();
                        DeleteUser delete = new DeleteUser();
                        delete.execute(Email);
                    } else{
                        Toast.makeText(eliminarusuario.this, "Los correos no coinciden, checalos ue :c", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(eliminarusuario.this, "Esta faltando un elemento para borrrar a la victima :c", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    public static final MediaType JSON = MediaType.parse("application/json; charset=utf-8");
    public class DeleteUser extends AsyncTask<String, String, String> {
        @Override
        protected String doInBackground(String... strings) {
            String Email = strings[0];
            String postBody = "{\n   \"email\" : \""+Email+"\"\n }";
            RequestBody body = RequestBody.create(JSON, postBody);
            System.out.println(postBody);

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
                    eliminarusuario.this.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            System.out.println(finalResult);
                            if (finalResult.equals("true")){
                                Toast.makeText(eliminarusuario.this, "Usuario eliminado correctamente", Toast.LENGTH_SHORT).show();
                            }else{
                                Toast.makeText(eliminarusuario.this, "Error al eliminar el usuario, puede que no exista", Toast.LENGTH_SHORT).show();
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
}
