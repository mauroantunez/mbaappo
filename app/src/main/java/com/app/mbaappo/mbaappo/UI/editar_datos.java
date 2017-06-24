package com.app.mbaappo.mbaappo.UI;

import android.content.Intent;
import android.support.v4.content.SharedPreferencesCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.app.mbaappo.mbaappo.Modelo.Usuario;
import com.app.mbaappo.mbaappo.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class editar_datos extends AppCompatActivity {
    FirebaseDatabase databaseuser;
    DatabaseReference muser;
    FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editar_datos);
    }
    private void inicializacion(){
        auth = FirebaseAuth.getInstance();
        databaseuser = FirebaseDatabase.getInstance();
        muser = databaseuser.getReference().child("Usuarios").child(encodeEmail(auth.getCurrentUser().getEmail()));
    }
    public String encodeEmail(String userEmail) {
        return userEmail.replace(".", ",");
    }
    private void editardatos(){
       muser.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
             final Usuario user = dataSnapshot.getValue(Usuario.class);
                if (user!=null){
                    final EditText nombre = (EditText) findViewById(R.id.editTextnombre);
                    final EditText apellido = (EditText) findViewById(R.id.editTextapellidos);
                    final EditText telefono = (EditText) findViewById(R.id.editTexttelefono);
                    final EditText direccion = (EditText) findViewById(R.id.editTextdireccion);
                    final String nombretxt = nombre.getText().toString().trim();
                    final String apellidotxt = apellido.getText().toString().trim();
                    final String telefonotxt = telefono.getText().toString().trim();
                    final String direcciontxt = direccion.getText().toString().trim();
                    final Button aceptar = (Button) findViewById(R.id.guardardatos);

                    aceptar.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            if (nombretxt!=null){
                            muser.child("nombre").setValue(nombretxt);}
                            if (apellidotxt!=null){
                            muser.child("apellido").setValue(apellidotxt);}
                            if (direccion!=null){
                            muser.child("direccion").setValue(direcciontxt);}
                            if (telefonotxt!=null){
                            muser.child("telefono").setValue(telefonotxt);}
                    }});

                    final Button cancelar = (Button) findViewById(R.id.cancelar_datos);
                    cancelar.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Intent mnu_serv = new Intent(editar_datos.this, Perfil.class);
                            mnu_serv.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                            startActivity(mnu_serv);
                    }});
            }}

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
   }
    }