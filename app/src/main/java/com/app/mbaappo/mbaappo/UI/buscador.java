package com.app.mbaappo.mbaappo.UI;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.app.mbaappo.mbaappo.FirebaseUI.FirebaseImageLoader;
import com.app.mbaappo.mbaappo.FirebaseUI.FirebaseListAdapter;
import com.app.mbaappo.mbaappo.Modelo.Usuario;
import com.app.mbaappo.mbaappo.Modelo.estructura_servicio;
import com.app.mbaappo.mbaappo.R;
import com.app.mbaappo.mbaappo.adapter.adapter_servicios;
import com.bumptech.glide.Glide;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;

public class buscador extends AppCompatActivity {
    private DatabaseReference database;
    private FirebaseDatabase mFirebaseDatabase;
    private FirebaseDatabase mFirebaseDatabasee;
    private Context mView;
    private ListView mMessageListView;
    private adapter_servicios mMessageAdapter;
    private FirebaseListAdapter mChatAdapter;
    private ListView mChatListView;
    private DatabaseReference mCurrentUserDatabaseReference;
    private String servicio_ID = "id";
    private String servid;
    private DatabaseReference databasee;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_buscador);
        Intent intent = this.getIntent();
        servid = intent.getStringExtra(servicio_ID);
        inicializar();
        agregarlist();
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;

    }
    private void inicializar(){
        mFirebaseDatabase = FirebaseDatabase.getInstance();
        database = mFirebaseDatabase.getReference().child("Servicios");
        databasee = mFirebaseDatabase.getReference().child("Servicios");
        mFirebaseDatabasee = FirebaseDatabase.getInstance();

    }
    private void agregarlist(){
        final String palabrass = servid;
        mCurrentUserDatabaseReference = mFirebaseDatabasee.getReference().child("Usuarios");
        mChatListView = (ListView) findViewById(R.id.id_list_buscador);
        mChatAdapter = new FirebaseListAdapter<estructura_servicio>(this, estructura_servicio.class, R.layout.item_servicio, database) {
            @Override
            protected void populateView(final View v, estructura_servicio model, int position) {
                String cadenaDondeBuscar = model.getTitulo();
                String loQueQuieroBuscar = palabrass;
                String[] palabras = loQueQuieroBuscar.split("\\s+");
               // for (String palabra : palabras) {
                    if (cadenaDondeBuscar.contains(loQueQuieroBuscar)) {
                      ((TextView) v.findViewById(R.id.nombre_servicio)).setText(model.getTitulo());
                      ((TextView) v.findViewById(R.id.precio_servicio)).setText(model.getPrecio());

                //final TextView nombreusuario =(TextView) v.findViewById(R.id.servicio_descripcion);
                ((RatingBar) v.findViewById(R.id.rating_servicio)).setRating(model.getRating());


                mCurrentUserDatabaseReference.child(model.getEmail()).addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        Usuario user = dataSnapshot.getValue(Usuario.class);
                        if (user != null){
                            ((TextView) v.findViewById(R.id.servicio_descripcion)).setText(user.getNombre()+user.getApellido());
                        }
                        if (user.getProfilePicLocation()!=null){
                            final ImageView image = (ImageView) v.findViewById(R.id.foto_serv);
                            StorageReference url = FirebaseStorage.getInstance().getReference().child(user.getProfilePicLocation());
                            Glide.with(v.getContext())
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
                    }


            //}
        };
        mChatListView.setAdapter(mChatAdapter);
        mChatListView.setOnItemClickListener(new AdapterView.OnItemClickListener(){
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String messageLocation = mChatAdapter.getRef(position).toString();

                if(messageLocation != null){
                    Intent intent = new Intent(view.getContext(), Servicio.class);
                    String serviciokey = mChatAdapter.getRef(position).getKey();
                    intent.putExtra("id", serviciokey);
                    startActivity(intent);
                }

                //Log.e("TAG", mChatAdapter.getRef(position).toString());
            }
        });
    }
}
