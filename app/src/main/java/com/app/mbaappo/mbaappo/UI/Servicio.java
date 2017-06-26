package com.app.mbaappo.mbaappo.UI;

import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.app.mbaappo.mbaappo.FirebaseUI.FirebaseImageLoader;
import com.app.mbaappo.mbaappo.Modelo.Chat;
import com.app.mbaappo.mbaappo.Modelo.Usuario;
import com.app.mbaappo.mbaappo.Modelo.estructura_servicio;
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

public class Servicio extends AppCompatActivity {
    private DatabaseReference database;
    private FirebaseDatabase mFirebaseDatabase;
    private FirebaseDatabase mUserDatabase;
    private DatabaseReference musuario,musu;
    private DatabaseReference database_chat, database_chat_reference;
    private String servid;
    private String servicio_ID = "id";
    private FirebaseAuth auth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_servicio);
        Intent intent = this.getIntent();
        //MessageID is the location of the messages for this specific chat
        servid = intent.getStringExtra(servicio_ID);
        inicializacion();
        agregardatosserv();


    }
    private void inicializacion(){
        auth = FirebaseAuth.getInstance();
        mFirebaseDatabase = FirebaseDatabase.getInstance();
        database = mFirebaseDatabase.getReference().child("Servicios").child(servid);
        mUserDatabase = FirebaseDatabase.getInstance();
        database_chat_reference= mFirebaseDatabase.getReference().child("Chat");
        database_chat = database_chat_reference.child(encodeEmail(auth.getCurrentUser().getEmail())).push();

    }
    private void agregardatosserv(){
        database.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                final estructura_servicio servicio = dataSnapshot.getValue(estructura_servicio.class);

                final TextView c_descripcion = (TextView) findViewById(R.id.contenido_descripcion_servicio);
                c_descripcion.setText(servicio.getDescripcion());
                final TextView c_precio = (TextView) findViewById(R.id.contenido_precio);
                c_precio.setText(servicio.getPrecio());
                final RatingBar c_rating = (RatingBar) findViewById(R.id.calificacion);
                c_rating.setRating(servicio.getRating());
                final FloatingActionButton contratar = (FloatingActionButton) findViewById(R.id.contratar_message);
                final String key= database_chat.getKey();
                final DatabaseReference database_chat_contratado = mFirebaseDatabase.getReference().child("Chat").child(servicio.getEmail()).child(key);
                musuario = mUserDatabase.getReference().child("Usuarios").child(servicio.getEmail());
                musu = mUserDatabase.getReference().child("Usuarios").child(servicio.getEmail());
                contratar.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        final Chat chat = new Chat(servicio.getKey(),servicio.getEmail(),key,encodeEmail(auth.getCurrentUser().getEmail()));
                        database_chat.setValue(chat);
                        database_chat_contratado.setValue(chat);
                        Intent intent = new Intent(Servicio.this, mensajeria.class);
                        intent.putExtra("idmessage", key);
                        startActivity(intent);
                        /**musu.addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(DataSnapshot dataSnapshot) {

                            }

                            @Override
                            public void onCancelled(DatabaseError databaseError) {

                            }
                        });*/

                    }
                });

                musuario.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        Usuario user = dataSnapshot.getValue(Usuario.class);
                        if (user.getProfilePicLocation()!=null){
                            final ImageView image = (ImageView) findViewById(R.id.app_bar_image);
                            StorageReference url = FirebaseStorage.getInstance().getReference().child(user.getProfilePicLocation());
                            Glide.with(Servicio.this)
                                    .using(new FirebaseImageLoader())
                                    .load(url)
                                    //.bitmapTransform(new CropCircleTransformation(v.getContext()))
                                    .into(image);
                        }
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

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
