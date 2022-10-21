package com.example.appfinalfinal;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    EditText emailusuario, contrasena;
    TextView Noregistro;
    Button botoningreso;
    String url = " https://user-res-api.herokuapp.com/user/checkUser";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Noregistro = (TextView) findViewById(R.id.Noregistro);
        Noregistro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(MainActivity.this, RegistroPrron.class);
                startActivity(i);
            }
        });
    }
}