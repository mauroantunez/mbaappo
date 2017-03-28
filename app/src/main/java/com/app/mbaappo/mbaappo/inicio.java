package com.app.mbaappo.mbaappo;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class inicio extends AppCompatActivity implements View.OnClickListener {
    private Button btn_ingresar, btn_registrarse;
    private EditText textusuario, textcontrasena;
    private ProgressDialog espera;
    private String mail, contrasenha;

    private FirebaseAuth auth;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_inicio);

        auth = FirebaseAuth.getInstance();

        btn_ingresar = (Button) findViewById(R.id.btn_ingresar);
        btn_registrarse = (Button) findViewById(R.id.btn_registrarse);
        textusuario = (EditText) findViewById(R.id.textusuario);
        textcontrasena = (EditText) findViewById(R.id.textcontrasena);

        btn_registrarse.setOnClickListener(this);
        btn_ingresar.setOnClickListener(this);
        espera = new ProgressDialog(this);
    }
    private void registrar(){
        Intent registroIntent = new Intent(inicio.this, activity_registro_usuarios.class);
        registroIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(registroIntent);

    }
    private void iniciarsesion(String email, String pass){

            espera.setMessage("Un momento...");
            espera.show();
            auth.signInWithEmailAndPassword(email, pass).
                    addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                espera.dismiss();
                                Intent btn_ingresar = new Intent(inicio.this, MainActivity.class);
                                startActivity(btn_ingresar);
                            } else {
                                espera.dismiss();
                                Toast toast1 = Toast.makeText(getApplicationContext(),
                                        "Credenciales incorrectas", Toast.LENGTH_SHORT);
                                toast1.setGravity(Gravity.CENTER, 0, 0);
                                toast1.show();
                            }
                        }
                    });
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.btn_registrarse:
                registrar();
                break;
            case R.id.btn_ingresar:
                mail = textusuario.getText().toString().trim();
                contrasenha = textcontrasena.getText().toString().trim();
                if(!TextUtils.isEmpty(mail) && !TextUtils.isEmpty(contrasenha))
                    iniciarsesion(mail,contrasenha);
                else{
                    Toast toast2 = Toast.makeText(getApplicationContext(),
                            "Campos obligatorios vacios", Toast.LENGTH_SHORT);
                    toast2.setGravity(Gravity.CENTER, 0, 0);
                    toast2.show();
                }

        }
    }
}
