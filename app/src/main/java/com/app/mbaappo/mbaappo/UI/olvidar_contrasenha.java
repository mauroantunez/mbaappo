package com.app.mbaappo.mbaappo.UI;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.app.mbaappo.mbaappo.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

import java.io.IOException;

public class olvidar_contrasenha extends AppCompatActivity {

    private FirebaseAuth auth;
    private EditText correo;
    private Button boton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_olvidar_contrasenha);
        correo = (EditText) findViewById(R.id.edit_correo);
        boton = (Button) findViewById(R.id.id_correo_btn_email);
        correo.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                boton.setEnabled(true);
            }
        });

        inicializacion();

        boton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    agregar_editext();
                }
                catch (Exception e){
                    Toast toast2 = Toast.makeText(getApplicationContext(),
                            "Correo no enviado", Toast.LENGTH_SHORT);
                    toast2.setGravity(Gravity.CENTER, 0, 0);
                    toast2.show();
                }

    }

}); }
    private void agregar_editext(){
        final String email = correo.getText().toString().trim();
        enviar_correo(email);
    }
    private void inicializacion(){
        auth = FirebaseAuth.getInstance();
    }
    private void enviar_correo(String correos){
        auth.sendPasswordResetEmail(correos)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    public static final String TAG = "1";

                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            Log.d(TAG, "Email sent.");
                            Intent btn_ingresar = new Intent(olvidar_contrasenha.this, inicio.class);
                            startActivity(btn_ingresar);

                    }


                }
    });
}}