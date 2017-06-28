package com.app.mbaappo.mbaappo.UI;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
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
import com.google.firebase.auth.FirebaseUser;

public class c_contrasenha extends AppCompatActivity {
    private static final String TAG = "2";
    private EditText contra_nueva, verificar;
    private Button btn_coincide;
    private FirebaseUser auth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_c_contrasenha);

        contra_nueva = (EditText) findViewById(R.id.contrasena_nueva);
        verificar = (EditText) findViewById(R.id.verificar_contrasena);
        btn_coincide = (Button) findViewById(R.id.acp_contrasena_nueva);
        btn_coincide.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cambiarcontra();
            }
        });

    }
    private void cambiarcontra(){
        String contra = contra_nueva.getText().toString().trim();
        String verificarc = verificar.getText().toString().trim();

        if(contra.equals(verificarc)){
            auth = FirebaseAuth.getInstance().getCurrentUser();


            auth.updatePassword("12345")
                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                        public static final String TAG ="1" ;

                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()) {
                                Log.d(TAG, "User password updated.");
                            }
                        }
                    });
        }
        if (contra != verificarc){
            Toast toast1 = Toast.makeText(getApplicationContext(),
                    "La contraseña no coincide", Toast.LENGTH_SHORT);
            toast1.setGravity(Gravity.CENTER, 0, 0);
            toast1.show();
        }
    }
}
