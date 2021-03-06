package com.app.mbaappo.mbaappo.UI;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v4.content.SharedPreferencesCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.app.mbaappo.mbaappo.FirebaseUI.FirebaseImageLoader;
import com.app.mbaappo.mbaappo.Modelo.FirebaseRef;
import com.app.mbaappo.mbaappo.Modelo.Usuario;
import com.app.mbaappo.mbaappo.R;
import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import jp.wasabeef.glide.transformations.CropCircleTransformation;

public class editar_datos extends AppCompatActivity {
    private static final int GALLERY_INTENT = 1;
    FirebaseDatabase databaseuser;
    DatabaseReference muser;
    Button mphotoPickerButton;
    private ProgressDialog mProgress;
    private StorageReference mStorage;
    private FirebaseAuth mFirebaseAuth;
    private String currentUserEmail;
    private ImageView profileImage;
    private FirebaseDatabase mFirebaseDatabase;
    private DatabaseReference mCurrentUserDatabaseReference;
    private Context mView;
    private ImageButton edit_photo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editar_datos);
        mView = editar_datos.this;
        inicializacion();
       //initializeScreen();
       // openImageSelector();
        //initializeUserInfo();
        try{
        agregar_texto();
        }
        catch (Exception e){

        }
        Button cancelar =(Button) findViewById(R.id.cancelar_datos);
        cancelar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent loginIntent = new Intent(editar_datos.this, Perfil.class);
                loginIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(loginIntent);
                finish();
            }
        });
        Button problematico = (Button) findViewById(R.id.id_seleccion);
        problematico.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent loginIntent = new Intent(editar_datos.this, Seleccionar_foto.class);
                loginIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(loginIntent);
            }
        });


    }


    private void inicializacion(){
        mFirebaseAuth = FirebaseAuth.getInstance();
        FirebaseAuth auth = FirebaseAuth.getInstance();
        databaseuser = FirebaseDatabase.getInstance();
        muser = databaseuser.getReference().child("Usuarios").child(encodeEmail(auth.getCurrentUser().getEmail()));

    }
    public void agregar_texto(){
        final FirebaseAuth uth = FirebaseAuth.getInstance();

        muser.addValueEventListener(new ValueEventListener() {
            public static final String TAG = "1";

            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                final Usuario user = dataSnapshot.getValue(Usuario.class);
                  final AlertDialog dialog,dialogap,dialogdi,dialogtel;
                  final  EditText editet,editTextap,editextdi,editTexttel;
                  final TextView nombre = (TextView) findViewById(R.id.id_editar_nombre);
                  final TextView apellido = (TextView) findViewById(R.id.id_editar_Apellido);
                  final TextView direccion = (TextView) findViewById(R.id.id_editar_direccion);
                  final TextView telefono = (TextView) findViewById(R.id.id_editar_telefono);
                    nombre.setText(user.getNombre());
                    apellido.setText(user.getApellido());
                    direccion.setText(user.getDireccion());
                    telefono.setText(user.getTelefono());

               // final FirebaseDatabase aiuda = FirebaseDatabase.getInstance();
                final Button guardar = (Button) findViewById(R.id.guardardatos);

                //final DatabaseReference muserguardar = aiuda.getReference().child("Usuarios").child(encodeEmail(uth.getCurrentUser().getEmail()));
                guardar.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        try{
                            final  String nombreguardar =  nombre.getText().toString().trim();
                            final String apellidoguardar = apellido.getText().toString().trim();
                            final String direccionguardar = direccion.getText().toString().trim();
                            final String telefonoguardar = telefono.getText().toString().trim();

                            if (nombreguardar.equals(null)||nombreguardar.length() == 0 || nombreguardar.equals("")){

                        }
                        else {
                            muser.child("nombre").setValue(nombreguardar);
                        }
                        if (apellidoguardar.equals(null)||apellidoguardar.length() == 0 || apellidoguardar.equals("") ){

                        }
                        else{
                            muser.child("apellido").setValue(apellidoguardar);
                        }
                        if (direccionguardar.equals(null)||direccionguardar.length() == 0 || direccionguardar.equals("")){

                        }
                        else{
                            muser.child("direccion").setValue(direccionguardar);
                        }
                        if (telefonoguardar.equals(null)||telefonoguardar.length() == 0 || telefonoguardar.equals("")){

                        }
                        else {
                            muser.child("telefono").setValue(telefonoguardar);
                        }
                        Log.d(TAG, "Email sent."+nombreguardar);


                        }
                        catch (Exception e){
                            Intent loginIntent = new Intent(editar_datos.this, MainActivity.class);
                            startActivity(loginIntent);
                            finish();

                        }

                        Intent loginIntent = new Intent(editar_datos.this, MainActivity.class);
                        startActivity(loginIntent);
                        finish();

                    }
                });
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }

    public String encodeEmail(String userEmail) {
        return userEmail.replace(".", ",");
    }

    }