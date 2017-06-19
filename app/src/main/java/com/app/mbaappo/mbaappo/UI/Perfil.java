package com.app.mbaappo.mbaappo.UI;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.app.mbaappo.mbaappo.FirebaseUI.FirebaseImageLoader;
import com.app.mbaappo.mbaappo.Modelo.Usuario;
import com.app.mbaappo.mbaappo.R;
import com.bumptech.glide.Glide;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

public class Perfil extends AppCompatActivity implements View.OnClickListener {
    Button cam_contraseña, editar_perfil;
    private FirebaseAuth mFirebaseAuth;
    private FirebaseDatabase DtabaseFirebase;
    private DatabaseReference muserreference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_perfil);
        cam_contraseña = (Button) findViewById(R.id.btn_cambiar_contraseña);
        editar_perfil = (Button) findViewById(R.id.btn_editar_info);
        editar_perfil.setOnClickListener(this);
        cam_contraseña.setOnClickListener(this);
        inicializar();
        agregardatosperfil();
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
    private void inicializar(){
        mFirebaseAuth = FirebaseAuth.getInstance();
        DtabaseFirebase = FirebaseDatabase.getInstance();
        muserreference = DtabaseFirebase.getReference().child("Usuarios").child(encodeEmail(mFirebaseAuth.getCurrentUser().getEmail()));
    }
    public String encodeEmail(String userEmail) {
        return userEmail.replace(".", ",");
    }
    private void agregardatosperfil(){
        TextView correo = (TextView) findViewById(R.id.id_correo_perfil);
        correo.setText(mFirebaseAuth.getCurrentUser().getEmail());
        muserreference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Usuario user = dataSnapshot.getValue(Usuario.class);
                if (user != null){
                    final TextView username = (TextView) findViewById(R.id.id_nombre);
                    username.setText(user.getNombre()+" "+user.getApellido());
                    final TextView telefono = (TextView) findViewById(R.id.id_numero_telf);
                    telefono.setText(user.getTelefono());
                    final TextView direccion = (TextView) findViewById(R.id.id_direccion_perfil);
                    direccion.setText(user.getDireccion());
                }


            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

}