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
        initializeScreen();
        openImageSelector();
        initializeUserInfo();
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
            }
        });



    }
    private void initializeScreen(){
        FirebaseAuth mauth = FirebaseAuth.getInstance();
        mFirebaseDatabase = FirebaseDatabase.getInstance();
        mCurrentUserDatabaseReference = mFirebaseDatabase
                .getReference().child("Usuarios" + "/" +encodeEmail(mauth.getCurrentUser().getEmail()));

        mCurrentUserDatabaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Usuario user = dataSnapshot.getValue(Usuario.class);
                final ImageView imageView = (ImageView) findViewById(R.id.image_btn_editar);
                StorageReference url = FirebaseStorage.getInstance().getReference().child(user.getProfilePicLocation());
                Glide.with(mView)
                        .using(new FirebaseImageLoader())
                        .load(url)
                        //.bitmapTransform(new CropCircleTransformation(mView))
                        .into(imageView);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }
    private void initializeUserInfo(){
       /** FirebaseAuth ai = FirebaseAuth.getInstance();
       DatabaseReference fire = FirebaseDatabase.getInstance().getReference().child("Usuarios").child(encodeEmail(ai.getCurrentUser().getEmail()));
        fire
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        Usuario user = dataSnapshot.getValue(Usuario.class);
                        final ImageView imageVieww = (ImageView) findViewById(R.id.image_btn_editar);
                        try{
                            if(user.getProfilePicLocation() != null){
                                StorageReference storageRef = FirebaseStorage.getInstance()
                                        .getReference().child(user.getProfilePicLocation());

                                Glide.with(mView)
                                        .using(new FirebaseImageLoader())
                                        .load(storageRef)
                                        .bitmapTransform(new CropCircleTransformation(mView))
                                        .into(imageVieww);
                            }
                        }catch (Exception e){
                            Log.e("Err", "glide");
                        }

                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });*/
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
                   /** dialog = new AlertDialog.Builder(editar_datos.this).create();
                    editet = new EditText(editar_datos.this);
                    dialogap = new AlertDialog.Builder(editar_datos.this).create();
                    editTextap = new EditText(editar_datos.this);
                    dialogdi = new AlertDialog.Builder(editar_datos.this).create();
                    editextdi = new EditText(editar_datos.this);
                    dialogtel = new AlertDialog.Builder(editar_datos.this).create();
                    editTexttel = new EditText(editar_datos.this);
                    /**final ImageButton image =(ImageButton) findViewById(R.id.image_btn_editar);
                    int al = image.getMeasuredHeight();
                    int an = image.getMeasuredWidth();
                    StorageReference url = FirebaseStorage.getInstance().getReference().child(user.getProfilePicLocation());
                    Glide.with(editar_datos.this)
                            .using(new FirebaseImageLoader())
                            .load(url)
                            //.bitmapTransform(new CropCircleTransformation(v.getContext()))
                            .into(image);
                    nombre.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.setTitle("Editar Nombre:");
                        dialog.setView(editet);
                        dialog.setButton(DialogInterface.BUTTON_POSITIVE, "Aceptar", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                nombre.setText(editet.getText());
                            }


                        });
                        dialog.show();
                    }
                });

                apellido.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        dialogap.setTitle("Editar Apellido:");
                        dialogap.setView(editTextap);
                        dialogap.setButton(DialogInterface.BUTTON_POSITIVE, "Aceptar", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                apellido.setText(editTextap.getText());

                            }


                        });
                        dialogap.show();
                    }
                });
                direccion.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialogdi.setTitle("Editar Dirección");
                        dialogdi.setView(editextdi);
                        dialogdi.setButton(DialogInterface.BUTTON_POSITIVE, "Aceptar", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                direccion.setText(editextdi.getText());

                            }


                        });
                        dialogdi.show();
                    }
                });
                telefono.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialogtel.setTitle("Editar Teléfono");
                        dialogtel.setView(editTexttel);
                        dialogtel.setButton(DialogInterface.BUTTON_POSITIVE, "Aceptar", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                telefono.setText(editTexttel.getText());

                            }


                        });
                        dialogtel.show();
                    }
                });*/




                final Button guardar = (Button) findViewById(R.id.guardardatos);
                final FirebaseDatabase aiuda = FirebaseDatabase.getInstance();
                final DatabaseReference muserguardar = aiuda.getReference().child("Usuarios").child(encodeEmail(uth.getCurrentUser().getEmail()));
                guardar.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        try{
                        final  String nombreguardar =  nombre.getText().toString().trim();
                        final String apellidoguardar = apellido.getText().toString().trim();
                        final String direccionguardar = direccion.getText().toString().trim();
                        final String telefonoguardar = telefono.getText().toString().trim();
                        if (nombreguardar.equals(null)||nombreguardar.length() == 0 || nombreguardar.equals("")){
                            nombreguardar.equals(user.getNombre());
                            muserguardar.child("nombre").setValue(nombre);
                        }
                        else {
                            muserguardar.child("nombre").setValue(nombreguardar);
                        }
                        if (apellidoguardar.equals(null)||apellidoguardar.length() == 0 || apellidoguardar.equals("") ){
                            apellidoguardar.equals(user.getApellido());
                            muserguardar.child("apellido").setValue(apellido);
                        }
                        else{
                            muserguardar.child("apellido").setValue(apellidoguardar);
                        }
                        if (direccionguardar.equals(null)||direccionguardar.length() == 0 || direccionguardar.equals("")){
                            direccionguardar.equals(user.getDireccion());
                            muserguardar.child("direccion").setValue(direccion);
                        }
                        else{
                            muserguardar.child("direccion").setValue(direccionguardar);
                        }
                        if (telefonoguardar.equals(null)||telefonoguardar.length() == 0 || telefonoguardar.equals("")){
                            telefonoguardar.equals(user.getTelefono());
                            muserguardar.child("telefono").setValue(telefono);
                        }
                        else {
                            muserguardar.child("telefono").setValue(telefonoguardar);
                        }
                        Log.d(TAG, "Email sent."+nombreguardar);

                            //Usuario usu = new Usuario(nombreguardar,apellidoguardar,encodeEmail(uth.getCurrentUser().getEmail()),telefonoguardar,direccionguardar,user.getProfilePicLocation());
                            //muserguardar.setValue(usu);
                        }
                        catch (Exception e){
                            Intent loginIntent = new Intent(editar_datos.this, MainActivity.class);
                            loginIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                            startActivity(loginIntent);
                        }

                        Intent loginIntent = new Intent(editar_datos.this, MainActivity.class);
                        loginIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        startActivity(loginIntent);
                    }
                });
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }
    public void openImageSelector(){
        edit_photo = (ImageButton) findViewById(R.id.id_btn_photo_edit);
        mProgress = new ProgressDialog(this);
        edit_photo.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                Intent intent = new Intent(Intent.ACTION_PICK);
                intent.setType("image/*");
                startActivityForResult(intent, GALLERY_INTENT);
                //mView = view;
            }
        });
    }
    public String encodeEmail(String userEmail) {
        return userEmail.replace(".", ",");
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, final Intent data){

        mStorage = FirebaseStorage.getInstance().getReference(); //make global
        super.onActivityResult(requestCode, requestCode, data);

        if(requestCode ==GALLERY_INTENT && resultCode == RESULT_OK){

            mProgress.setMessage("Cargando...");
            mProgress.show();

            Uri uri = data.getData();
            //Keep all images for a specific chat grouped together
            final String imageLocation = "Photos/profile_picture/" + currentUserEmail;
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
            });
        }

    }
    public void addImageToProfile(final String imageLocation){
        final ImageView imageView = (ImageView) findViewById(R.id.image_btn_editar);
        mCurrentUserDatabaseReference
                .child("profilePicLocation").setValue(imageLocation).addOnCompleteListener(
                new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        StorageReference storageRef = FirebaseStorage.getInstance()
                                .getReference().child(imageLocation);
                        Glide.with(mView)
                                .using(new FirebaseImageLoader())
                                .load(storageRef)
                               // .bitmapTransform(new CropCircleTransformation(mView))
                                .into(imageView);
                    }
                }
        );

    }
   /** private void editardatos(){
       muser.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
             final Usuario user = dataSnapshot.getValue(Usuario.class);
                if (user!=null){
                   /final EditText nombre = (EditText) findViewById(R.id.editTextnombre);
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
   }*/
    }