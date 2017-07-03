package com.app.mbaappo.mbaappo.UI;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.app.mbaappo.mbaappo.Modelo.Usuario;
import com.app.mbaappo.mbaappo.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.util.UUID;

public class activity_registro_usuarios extends AppCompatActivity {

    private EditText campo_nombre;
    private EditText campo_apellido;
    private EditText campo_mail;
    private EditText campo_password;
    private EditText campo_telefono;
    private EditText campo_direccion;

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
        campo_telefono =(EditText) findViewById(R.id.teleUsuarioReg);
        campo_direccion = (EditText) findViewById(R.id.direccionUsuarioReg);

        boton_registrar = (Button) findViewById(R.id.registrarUsuarioBtn);

        boton_registrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                registrar();
            }
        });
    }

    private void registrar() {

        final String email = campo_mail.getText().toString().trim();
        final String password = campo_password.getText().toString().trim();


        progreso_registro.setMessage("Registrando");
        progreso_registro.show();
        if (password.length()<6){
            progreso_registro.dismiss();
            Toast toast1 = Toast.makeText(getApplicationContext(),
                    "La contraseña debe tener 6 o mas carácteres", Toast.LENGTH_SHORT);
            toast1.setGravity(Gravity.CENTER, 0, 0);
            toast1.show();

        }else{
            auth.createUserWithEmailAndPassword(email,password)
                    .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {

                            if(task.isSuccessful()){
                                guardardatos();
                                progreso_registro.dismiss();
                                Intent mainIntent = new Intent(activity_registro_usuarios.this, Seleccionar_foto.class);
                                mainIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                startActivity(mainIntent);

                            }

                        }
                    });
        }


    }

    private void guardardatos(){
        final String nombre = campo_nombre.getText().toString().trim();
        final String apellidos = campo_apellido.getText().toString().trim();
        final String correo = encodeEmail(campo_mail.getText().toString().trim());
        final String telefono = campo_telefono.getText().toString().trim();
        final String direccion = campo_direccion.getText().toString().trim();
        agregar_usuario(correo,nombre,apellidos,telefono,direccion);


    }
    public void agregar_usuario(String usermail,String nombre,String apellido,String telefono, String direccion){
        DatabaseReference usuario_actual_db = database.child(usermail);
        Usuario user = new Usuario(nombre,apellido,usermail,telefono,direccion,null);
        usuario_actual_db.setValue(user);
       /** final String imageLocation = "Photos/profile_picture/" + usermail;

        Uri uri = data.getData();
        final String imageLocationId = imageLocation + "/" + uri.getLastPathSegment();
        final String uniqueId = UUID.randomUUID().toString();
        final StorageReference filepath = mStorage.child(imageLocation).child(uniqueId + "/profile_pic");
        final String downloadURl = filepath.getPath();
        filepath.putFile(uri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                //create a new message containing this image
                addImageToProfile(downloadURl);
                mProgress.dismiss();
            }
        });*/
    }
    public String encodeEmail(String userEmail) {
        return userEmail.replace(".", ",");
    }
}