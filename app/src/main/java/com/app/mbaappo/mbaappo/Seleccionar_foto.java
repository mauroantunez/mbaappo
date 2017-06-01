package com.app.mbaappo.mbaappo;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;


import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.UUID;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.AuthResult;
import com.squareup.picasso.Picasso;



public class Seleccionar_foto extends AppCompatActivity {
    private Button mcargar, finalizar;
    private static final int GALLERY_INTENT=1;
    FirebaseAuth auth;
    private String useremail;
    private StorageReference mstorage;
    private DatabaseReference mCurrentUserDatabaseReference;
    private FirebaseDatabase mFirebaseDatabase;
    private Context mView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_seleccionar_foto);
        inicializar();
        mcargar.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_PICK);
                intent.setType("image/*");
                startActivityForResult(intent, GALLERY_INTENT);
                //mView = view;
            }

        });
        initializeUserInfo();
        finalizar.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                //subirimagen(selectedImage);
                Intent mnu_main = new Intent(Seleccionar_foto.this, MainActivity.class);
                mnu_main.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(mnu_main);
            }
        });
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, final Intent data){
        mstorage = FirebaseStorage.getInstance().getReference();
        super.onActivityResult(requestCode, requestCode, data);

        if(requestCode ==GALLERY_INTENT && resultCode == RESULT_OK){

            Uri uri = data.getData();
            //Keep all images for a specific chat grouped together
            String imageLocation = "Photos/profile_picture/" + useremail;
            final String imageLocationId = imageLocation + "/" + uri.getLastPathSegment();
            final String uniqueId = UUID.randomUUID().toString();
            final StorageReference filepath = mstorage.child(imageLocation).child(uniqueId + "/profile_pic");
            final String downloadURl = filepath.getPath();
            filepath.putFile(uri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    //create a new message containing this image
                    addImageToProfile(downloadURl);

                }
            });
        }

    }
    public void addImageToProfile(final String imageLocation){
        final ImageView imageView = (ImageView) findViewById(R.id.view_foto);
        mCurrentUserDatabaseReference
                .child("profilePicLocation").setValue(imageLocation).addOnCompleteListener(
                new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        StorageReference storageRef = FirebaseStorage.getInstance()
                                .getReference().child(imageLocation);
                        String Url = storageRef.toString();
                        Picasso.with(Seleccionar_foto.this)
                                .load(Url)
                                .into(imageView);

                    }
                }
        );

    }
    private void initializeUserInfo(){
        final ImageView imageView = (ImageView) findViewById(R.id.view_foto);
        mCurrentUserDatabaseReference
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        Usuario user = dataSnapshot.getValue(Usuario.class);
                        //try{
                            if(user.getUbicacionfoto() != null){
                                StorageReference storageRef = FirebaseStorage.getInstance()
                                        .getReference().child(user.getUbicacionfoto());
                                String Url = storageRef.toString();

                                Picasso
                                        .with(Seleccionar_foto.this)
                                        .load(Url)
                                        .into(imageView);
                            }
                        //}catch (Exception e){
                            //Log.e("Err", "glide");
                        //}

                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });
    }
    protected void inicializar(){
        auth = FirebaseAuth.getInstance();
        mcargar = (Button) findViewById(R.id.btn_agregar_foto);
        finalizar =(Button) findViewById(R.id.btn_finalizar);
        useremail =  encodeEmail(auth.getCurrentUser().getEmail());
        mFirebaseDatabase = FirebaseDatabase.getInstance();
        mCurrentUserDatabaseReference = mFirebaseDatabase.getReference().child("Usuarios/"+useremail);
    }

    public String encodeEmail(String userEmail) {
        return userEmail.replace(".", ",");
}

}