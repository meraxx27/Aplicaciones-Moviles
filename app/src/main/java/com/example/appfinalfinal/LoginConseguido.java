package com.example.appfinalfinal;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContract;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import android.Manifest;
import android.app.Instrumentation;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Base64;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.os.Handler;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;


public class LoginConseguido extends AppCompatActivity {
    private static final String CHANNEL_ID = "channel_id01";
    private static final int NOTIFICATION_ID = 1;
    Button Volver,GetNotify,edit,delete,changephoto,btnmapa;
    TextView n,ln,dob,email,nac;
    ImageView porfilpicture;
    private static final int IMAGE_PICK_CODE = 1;
    private static final int PERMISSION_CODE = 100;
    Uri uri;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_conseguido);
        getSupportActionBar().hide();

        btnmapa = (Button) findViewById(R.id.btnmapa);
        GetNotify = (Button) findViewById(R.id.Notify);
        Volver = (Button) findViewById(R.id.Volver);
        edit = (Button) findViewById(R.id.Edit);
        delete = (Button) findViewById(R.id.delete);
        n = (TextView) findViewById(R.id.txtname);
        ln = (TextView) findViewById(R.id.txtlastname);
        email = (TextView) findViewById(R.id.txtemail);
        dob = (TextView) findViewById(R.id.txtfecha);
        nac = (TextView) findViewById(R.id.txtnac);
        changephoto = (Button) findViewById(R.id.cambiarimagen);
        porfilpicture = (ImageView) findViewById(R.id.porfilpicture);
        SharedPreferences sharedPreferences = getSharedPreferences("KafrezzedeOreoChico", Context.MODE_PRIVATE);
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            public void run() {
                n.setText(sharedPreferences.getString("name", ""));
                ln.setText(sharedPreferences.getString("lastname", ""));
                email.setText(sharedPreferences.getString("email", ""));
                dob.setText(sharedPreferences.getString("fecha",""));
                nac.setText(sharedPreferences.getString("nationality",""));
                String xd = sharedPreferences.getString("isAdmin", "False");
                String imagenkafreze = sharedPreferences.getString("imageURI","");
                System.out.println(imagenkafreze);
              /*  if (imagenkafreze.equals("")){
                    System.out.println(imagenkafreze);
                } else {
                    SharedPreferences sharedPreferences = getSharedPreferences("KafrezzedeOreoChico", Context.MODE_PRIVATE);
                    String imageUriString = sharedPreferences.getString("imageURI", null);
                    Uri imageUri = Uri.parse(imageUriString);
                    porfilpicture.setImageURI(imageUri);
                }
                if (xd.equals("true")){
                    edit.setVisibility(View.VISIBLE);
                    delete.setVisibility(View.VISIBLE);
                } else {
                    edit.setVisibility(View.INVISIBLE);
                    delete.setVisibility(View.INVISIBLE);
                }
               */
            }
        }, 550);


        Volver.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putBoolean("CHECKED",false);
                editor.commit();
                Intent i = new Intent(LoginConseguido.this, MainActivity.class);

                startActivity(i);
                ///editor.clear().commit();
            }
        });
        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(LoginConseguido.this, eliminarusuario.class);
                startActivity(i);
            }
        });
        btnmapa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(LoginConseguido.this, mapachilodelcaffenio.class);
                startActivity(i);
            }
        });
        GetNotify.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
              showNotification();
            }
        });
        changephoto.setOnClickListener(v -> mGetContet.launch("image/*"));

    }
    ActivityResultLauncher<String> mGetContet = registerForActivityResult(
            new ActivityResultContracts.GetContent(),
            new ActivityResultCallback<Uri>() {
                @Override
                public void onActivityResult(Uri result) {
                    SharedPreferences sharedPreferences = getSharedPreferences("KafrezzedeOreoChico", Context.MODE_PRIVATE);
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    if (result != null) {
                        porfilpicture.setImageURI(result);
                        editor.putString("imageURI", result.toString());
                        editor.commit();
                      /*  InputStream inputStream = null; // You can get an inputStream using any I/O API
                        try {
                            inputStream = new FileInputStream(String.valueOf(result));
                        } catch (FileNotFoundException e) {
                            e.printStackTrace();
                        }
                        byte[] bytes;
                        byte[] buffer = new byte[8192];
                        int bytesRead;
                        ByteArrayOutputStream output = new ByteArrayOutputStream();

                        try {
                            while ((bytesRead = inputStream.read(buffer)) != -1) {
                                output.write(buffer, 0, bytesRead);
                            }
                        }
                        catch (IOException e) {
                            e.printStackTrace();
                        }

                        bytes = output.toByteArray();
                        String encodedString = Base64.encodeToString(bytes, Base64.DEFAULT);
                        SharedPreferences.Editor editor = sharedPreferences.edit();
                        editor.putString("image", encodedString);
                        editor.apply();
                        */


                    }
                }
            }

    );


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