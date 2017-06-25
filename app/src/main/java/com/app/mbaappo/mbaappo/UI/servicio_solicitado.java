package com.app.mbaappo.mbaappo.UI;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.app.mbaappo.mbaappo.FirebaseUI.FirebaseImageLoader;
import com.app.mbaappo.mbaappo.FirebaseUI.FirebaseListAdapter;
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

import jp.wasabeef.glide.transformations.CropCircleTransformation;

public class servicio_solicitado extends AppCompatActivity {
    private String key_chat;

    /**------------------------Firebase---------------------*/
    /**private FirebaseDatabase mFirebasedatabase;
    private DatabaseReference database;
    private DatabaseReference mchat;
    private FirebaseAuth auth;
    private DatabaseReference muser;
    /**-----------------------------------------------------*/
    private FirebaseListAdapter mChatAdapter;
    private ListView mChatListView;

    public servicio_solicitado(String key_chat) {
        this.key_chat = key_chat;
    }
    public  servicio_solicitado(){}

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_servicio_solicitado);
        inicializacion();
        agregarlista();
    }
    public void inicializacion(){

       /** final FirebaseAuth auth = FirebaseAuth.getInstance();
        final FirebaseDatabase mFirebasedatabase = FirebaseDatabase.getInstance();
        final DatabaseReference muser = mFirebasedatabase.getReference().child("Usuario");
        final DatabaseReference database = mFirebasedatabase.getReference().child("Servicio");
        final DatabaseReference mchat = mFirebasedatabase.getReference().child("Chat").child(encodeEmail(auth.getCurrentUser().getEmail()));*/
    }
    public void agregarlista(){
        final FirebaseAuth auth = FirebaseAuth.getInstance();
        final FirebaseDatabase mFirebasedatabase = FirebaseDatabase.getInstance();
        final DatabaseReference muser = mFirebasedatabase.getReference().child("Usuarios");
        final DatabaseReference database = mFirebasedatabase.getReference().child("Servicios");
        final DatabaseReference mchat = mFirebasedatabase.getReference().child("Chat").child(encodeEmail(auth.getCurrentUser().getEmail()));
        mChatListView = (ListView) findViewById(R.id.lis_servicio_solicitado);
        mChatAdapter = new FirebaseListAdapter <Chat>(this, Chat.class, R.layout.item_servicio_solicitado, mchat) {
            @Override
            protected void populateView(final View v, Chat model, int position) {


                muser.child(model.getKey_usuario_contratado()).addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        Usuario user = dataSnapshot.getValue(Usuario.class);
                        if (user != null){
                            ((TextView) v.findViewById(R.id.text_nombre_usuario)).setText(user.getNombre()+" "+user.getApellido());
                        }
                        if (user.getProfilePicLocation()!=null){
                            final ImageView image = (ImageView) v.findViewById(R.id.photo_servicio_solicitado);
                            StorageReference url = FirebaseStorage.getInstance().getReference().child(user.getProfilePicLocation());
                            Glide.with(v.getContext())
                                    .using(new FirebaseImageLoader())
                                    .load(url)
                                    .bitmapTransform(new CropCircleTransformation(v.getContext()))
                                    .into(image);
                        }
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });
                database.child(model.getKey_servicio()).addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        estructura_servicio servicio = dataSnapshot.getValue(estructura_servicio.class);
                        if (servicio != null){
                            ((TextView) v.findViewById(R.id.text_titulo_servicio)).setText(servicio.getTitulo());
                        }
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });

            }
        };
        mChatListView.setAdapter(mChatAdapter);
    }
    public String encodeEmail(String userEmail) {
        return userEmail.replace(".", ",");
    }
}
