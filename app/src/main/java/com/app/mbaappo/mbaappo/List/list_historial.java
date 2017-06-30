package com.app.mbaappo.mbaappo.List;

import android.content.Intent;
import android.provider.ContactsContract;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.app.mbaappo.mbaappo.FirebaseUI.FirebaseImageLoader;
import com.app.mbaappo.mbaappo.FirebaseUI.FirebaseListAdapter;
import com.app.mbaappo.mbaappo.Modelo.Historial;
import com.app.mbaappo.mbaappo.Modelo.Usuario;
import com.app.mbaappo.mbaappo.Modelo.estructura_servicio;
import com.app.mbaappo.mbaappo.R;
import com.app.mbaappo.mbaappo.UI.Servicio;
import com.app.mbaappo.mbaappo.UI.lista_mensaje_historial;
import com.app.mbaappo.mbaappo.adapter.adapter_historial;
import com.app.mbaappo.mbaappo.UI.mensajeria;
import com.bumptech.glide.Glide;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;

public class list_historial extends AppCompatActivity {
    private FirebaseDatabase mFirebaseDatabase;
    private DatabaseReference mhistorial;
    private FirebaseAuth auth;
    private FirebaseListAdapter mChatAdapter;
    private ListView mChatListView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_historial);
        inicializacion();
        agregarlist();


    }
    public void inicializacion(){
        mFirebaseDatabase = FirebaseDatabase.getInstance();
        auth = FirebaseAuth.getInstance();
        mhistorial = mFirebaseDatabase.getReference().child("Historial").child(encodeEmail(auth.getCurrentUser().getEmail()));

    }
    @Override
    public void onBackPressed() {
        finish();
    }
    private void agregarlist(){
        mChatListView = (ListView) findViewById(R.id.list_historial);
        final DatabaseReference mCurrentUserDatabaseReference = mFirebaseDatabase.getReference().child("Usuarios");
        final DatabaseReference mServicio = mFirebaseDatabase.getReference().child("Servicios");
        mChatAdapter = new FirebaseListAdapter<Historial>(this, Historial.class, R.layout.item_historial, mhistorial) {
            @Override
            protected void populateView(final View v,Historial model, int position) {
                ((TextView) v.findViewById(R.id.servicio_fecha)).setText(model.getFecha());
                mServicio.child(model.getKey_servicio()).addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        estructura_servicio servicio = dataSnapshot.getValue(estructura_servicio.class);
                        ((TextView) v.findViewById(R.id.servicio_historial)).setText(servicio.getTitulo());
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });


                mCurrentUserDatabaseReference.child(model.getKey_usuario_contratado()).addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        Usuario user = dataSnapshot.getValue(Usuario.class);
                        if (user != null){
                            ((TextView) v.findViewById(R.id.usuario_servicio)).setText(user.getNombre()+" "+user.getApellido());
                        }
                        if (user.getProfilePicLocation()!=null){
                            final ImageView image = (ImageView) v.findViewById(R.id.historial_foto);
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
        };
        mChatListView.setAdapter(mChatAdapter);
        mChatListView.setOnItemClickListener(new AdapterView.OnItemClickListener(){
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String messageLocation = mChatAdapter.getRef(position).toString();

                if(messageLocation != null){
                    Intent intent = new Intent(view.getContext(), lista_mensaje_historial.class);
                    String serviciokey = mChatAdapter.getRef(position).getKey();
                    intent.putExtra("idmessage", serviciokey);
                    startActivity(intent);
                }

                //Log.e("TAG", mChatAdapter.getRef(position).toString());
            }
        });
    }
    public String encodeEmail(String userEmail) {
        return userEmail.replace(".", ",");
    }
}
