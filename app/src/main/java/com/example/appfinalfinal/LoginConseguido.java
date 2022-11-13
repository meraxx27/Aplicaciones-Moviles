package com.example.appfinalfinal;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.core.app.TaskStackBuilder;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.security.keystore.StrongBoxUnavailableException;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.nio.channels.Channel;


public class LoginConseguido extends AppCompatActivity {
    private static final String CHANNEL_ID = "channel_id01";
    private static final int NOTIFICATION_ID = 1;
    Button Volver,GetNotify;
    TextView n,ln,dob,email,nac;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_conseguido);
        getSupportActionBar().hide();

        GetNotify = (Button) findViewById(R.id.Notify);
        Volver = (Button) findViewById(R.id.Volver);
        n = (TextView) findViewById(R.id.txtname);
        ln = (TextView) findViewById(R.id.txtlastname);
        email = (TextView) findViewById(R.id.txtemail);
        dob = (TextView) findViewById(R.id.txtfecha);
        nac = (TextView) findViewById(R.id.txtnac);
        SharedPreferences sharedPreferences = getSharedPreferences("KafrezzedeOreoChico", Context.MODE_PRIVATE);
        n.setText(sharedPreferences.getString("name", ""));
        ln.setText(sharedPreferences.getString("lastname", ""));
        email.setText(sharedPreferences.getString("email", ""));
        dob.setText(sharedPreferences.getString("fecha",""));
        nac.setText(sharedPreferences.getString("nationality",""));



        Volver.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(LoginConseguido.this, MainActivity.class);
                startActivity(i);
            }
        });
        GetNotify.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
              showNotification();
            }
        });
    }

    private void showNotification() {
        createNoticationChannel();

        NotificationCompat.Builder builder = new NotificationCompat.Builder(this, CHANNEL_ID);

        builder.setSmallIcon(R.drawable.icon_password);

        builder.setContentTitle("Ahi va profe");

        builder.setContentText("Yo digo que un cienon profe, o ya paseme ya me quiero ir :c");

        builder.setPriority(NotificationCompat.PRIORITY_DEFAULT);

        NotificationManagerCompat notificationManagerCompat = NotificationManagerCompat.from(this);
        notificationManagerCompat.notify(NOTIFICATION_ID, builder.build());
    }

    private void createNoticationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){

            CharSequence name = "My Notification";
            String desceription = "Mu notification description";

            int importance = NotificationManager.IMPORTANCE_DEFAULT;

            NotificationChannel notificationChannel = new NotificationChannel(CHANNEL_ID, name,importance);
            notificationChannel.setDescription(desceription);

            NotificationManager notificationManager = (NotificationManager)getSystemService(NOTIFICATION_SERVICE);
            notificationManager.createNotificationChannel(notificationChannel);
        }
    }
}