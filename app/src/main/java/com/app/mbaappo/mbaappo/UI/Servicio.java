package com.app.mbaappo.mbaappo.UI;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.app.mbaappo.mbaappo.FirebaseUI.FirebaseImageLoader;
import com.app.mbaappo.mbaappo.Modelo.Chat;
import com.app.mbaappo.mbaappo.Modelo.Usuario;
import com.app.mbaappo.mbaappo.Modelo.estructura_servicio;
import com.app.mbaappo.mbaappo.R;
import com.bumptech.glide.Glide;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
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
    private DatabaseReference musuario, musu;
    // private DatabaseReference database_chat, database_chat_reference;
    private String servid, title;
    private String servicio_ID = "id";
    private FirebaseAuth auth;
    public FirebaseAuth.AuthStateListener authListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_servicio);
        Intent intent = this.getIntent();
        //MessageID is the location of the messages for this specific chat
        servid = intent.getStringExtra(servicio_ID);
        title = intent.getStringExtra("id2");
        getSupportActionBar().setTitle(title);
        authListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                if (firebaseAuth.getCurrentUser() != null) {
                    FloatingActionButton contratar = (FloatingActionButton) findViewById(R.id.contratar_message);
                    contratar.setEnabled(true);
                }
                if (firebaseAuth.getCurrentUser() == null) {
                    FloatingActionButton contratar = (FloatingActionButton) findViewById(R.id.contratar_message);
                    contratar.setEnabled(false);
                }

            }
        };
        inicializacion();
        agregardatosserv();


    }

    private void inicializacion() {
        auth = FirebaseAuth.getInstance();
        mFirebaseDatabase = FirebaseDatabase.getInstance();
        database = mFirebaseDatabase.getReference().child("Servicios").child(servid);
        mUserDatabase = FirebaseDatabase.getInstance();


    }

    private void agregardatosserv() {

        database.addValueEventListener(new ValueEventListener() {


            private static final String TAG = "2";

            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                final estructura_servicio servicio = dataSnapshot.getValue(estructura_servicio.class);
                try {
                    final TextView c_descripcion = (TextView) findViewById(R.id.contenido_descripcion_servicio);
                    c_descripcion.setText(servicio.getDescripcion());
                    final TextView c_precio = (TextView) findViewById(R.id.contenido_precio);
                    c_precio.setText(servicio.getPrecio() + " Gs.");
                    final TextView tarifa = (TextView) findViewById(R.id.contenido_tarifa);
                    tarifa.setText(servicio.getTarifa());
                    final RatingBar c_rating = (RatingBar) findViewById(R.id.calificacion);
                    c_rating.setRating(servicio.getRating());

                    musuario = mUserDatabase.getReference().child("Usuarios").child(servicio.getEmail());
                    musu = mUserDatabase.getReference().child("Usuarios").child(servicio.getEmail());
                    final FloatingActionButton llamar = (FloatingActionButton) findViewById(R.id.id_phone_call);
                    llamar.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            musuario.addValueEventListener(new ValueEventListener() {
                                @Override
                                public void onDataChange(DataSnapshot dataSnapshot) {
                                    Usuario user = dataSnapshot.getValue(Usuario.class);
                                    Intent intent = new Intent(Intent.ACTION_CALL);
                                    intent.setData(Uri.parse("tel:" + user.getTelefono()));
                                    if (ActivityCompat.checkSelfPermission(Servicio.this, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                                        // TODO: Consider calling
                                        //    ActivityCompat#requestPermissions
                                        // here to request the missing permissions, and then overriding
                                        //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                                        //                                          int[] grantResults)
                                        // to handle the case where the user grants the permission. See the documentation
                                        // for ActivityCompat#requestPermissions for more details.
                                        return;
                                    }
                                    startActivity(intent);
                            }

                            @Override
                            public void onCancelled(DatabaseError databaseError) {

                            }
                        });
                        }
                    });
                        final FirebaseAuth usu = FirebaseAuth.getInstance();


                            if(encodeEmail(usu.getCurrentUser().getEmail()).equals(servicio.getEmail())) {



                            }  }
                            catch(Exception e){
                                Log.d((String) TAG, "Email sent.");
                    }
                  //  try{
                final FloatingActionButton contratar = (FloatingActionButton) findViewById(R.id.contratar_message);
                            contratar.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    try{
                                        FirebaseAuth usu = FirebaseAuth.getInstance();
                                        Log.d((String) TAG, "Email."+servicio.getEmail()+"+"+usu.getCurrentUser().getEmail());
                                        if (usu.getCurrentUser().getEmail() != null &&  !encodeEmail(usu.getCurrentUser().getEmail()).equals(servicio.getEmail())) {
                                            final FirebaseAuth authh = FirebaseAuth.getInstance();
                                            final DatabaseReference database_chat_reference= FirebaseDatabase.getInstance().getReference().child("Chat");
                                             final DatabaseReference database_chat = database_chat_reference.child(encodeEmail(authh.getCurrentUser().getEmail())).push();
                                            final String key= database_chat.getKey();
                                            final DatabaseReference database_chat_contratado = FirebaseDatabase.getInstance().getReference().child("Chat").child(servicio.getEmail()).child(key);
                                             final Chat chat = new Chat(servicio.getKey(),servicio.getEmail(),key,encodeEmail(auth.getCurrentUser().getEmail()));
                                             database_chat.setValue(chat);
                                             database_chat_contratado.setValue(chat);
                                            Intent intent = new Intent(Servicio.this, mensajeria.class);
                                            intent.putExtra("idmessage", key);
                                             startActivity(intent);

                                        }
                                        else{

                                        }
                                }
                                    catch (Exception e){
                                        Intent loginIntent = new Intent(Servicio.this, inicio.class);
                                        loginIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                        startActivity(loginIntent);

                                    }


                                }

                            });




                        // ...



                musuario.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        Usuario user = dataSnapshot.getValue(Usuario.class);
                        if (user.getProfilePicLocation()!=null){
                            try {
                                final ImageView image = (ImageView) findViewById(R.id.app_bar_image);
                                StorageReference url = FirebaseStorage.getInstance().getReference().child(user.getProfilePicLocation());
                                Glide.with(Servicio.this)
                                        .using(new FirebaseImageLoader())
                                        .load(url)
                                        //.bitmapTransform(new CropCircleTransformation(v.getContext()))
                                        .into(image);
                            }
                           catch (Exception e){

                           }
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
