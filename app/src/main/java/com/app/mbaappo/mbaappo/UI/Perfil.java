package com.app.mbaappo.mbaappo.UI;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.app.mbaappo.mbaappo.R;

public class Perfil extends AppCompatActivity implements View.OnClickListener {
    Button cam_contraseña, editar_perfil;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_perfil);
        cam_contraseña = (Button) findViewById(R.id.btn_cambiar_contraseña);
        editar_perfil = (Button) findViewById(R.id.btn_editar_info);
        editar_perfil.setOnClickListener(this);
        cam_contraseña.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case (R.id.btn_cambiar_contraseña):
                Intent c_contrasena = new Intent(Perfil.this, c_contrasenha.class);
                c_contrasena.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(c_contrasena);
            case (R.id.btn_editar_info):
                Intent e_dato = new Intent(Perfil.this, editar_datos.class);
                e_dato.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(e_dato);


        }

    }
}
