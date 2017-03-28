package com.app.mbaappo.mbaappo;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class activity_registro_usuarios extends AppCompatActivity {

    private EditText campo_nombre;
    private EditText campo_apellido;
    private EditText campo_mail;
    private EditText campo_password;

    private Button boton_registrar;

    private FirebaseAuth auth;
    private DatabaseReference database;

    private ProgressDialog progreso_registro;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_registro_usuarios);

        auth = FirebaseAuth.getInstance();
        database = FirebaseDatabase.getInstance().getReference().child("Usuarios");

        progreso_registro = new ProgressDialog(this);

        campo_nombre = (EditText) findViewById(R.id.nombreUsuarioReg);
        campo_apellido = (EditText) findViewById(R.id.apellidosUsuarioReg);
        campo_mail = (EditText) findViewById(R.id.maiUsuarioReg);
        campo_password = (EditText) findViewById(R.id.passUsuarioReg);

        boton_registrar = (Button) findViewById(R.id.registrarUsuarioBtn);

        boton_registrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                registrar();
            }
        });
    }

    private void registrar() {
        final String nombre = campo_nombre.getText().toString().trim();
        final String apellidos = campo_apellido.getText().toString().trim();
        final String email = campo_mail.getText().toString().trim();
        final String password = campo_password.getText().toString().trim();

        if(!TextUtils.isEmpty(nombre) && !TextUtils.isEmpty(apellidos) &&
                !TextUtils.isEmpty(email) && !TextUtils.isEmpty(password)){

            progreso_registro.setMessage("Registrando");
            progreso_registro.show();

            auth.createUserWithEmailAndPassword(email,password)
                    .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {

                    if(task.isSuccessful()){
                        String userUID =  auth.getCurrentUser().getUid();
                        DatabaseReference usuario_actual_db = database.child(userUID);
                        usuario_actual_db.child("nombre").setValue(nombre);
                        usuario_actual_db.child("apellidos").setValue(apellidos);

                        progreso_registro.dismiss();

                        Intent mainIntent = new Intent(activity_registro_usuarios.this, MainActivity.class);
                        mainIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        startActivity(mainIntent);
                    }

                }
            });
        }
    }
}
