package com.app.mbaappo.mbaappo.List;


import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
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
import com.app.mbaappo.mbaappo.UI.MainActivity;
import com.app.mbaappo.mbaappo.UI.Servicio;

import com.app.mbaappo.mbaappo.adapter.adapter_servicios;
import com.bumptech.glide.Glide;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;

public class list_categoria_servicio extends AppCompatActivity {
    private static final String TAG ="1" ;
    private DatabaseReference database;
    private FirebaseDatabase mFirebaseDatabase;
    private FirebaseDatabase mFirebaseDatabasee;
    estructura_servicio datos;
    ArrayList <estructura_servicio> arraydatos = new ArrayList<estructura_servicio>();
    private Context mView;
    private ListView mMessageListView;
    private adapter_servicios mMessageAdapter;
    private FirebaseListAdapter mChatAdapter;
    private ListView mChatListView;
    String cate;
    String servid;
    String servicio_ID =  "id" ;
    String catego;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista_servicios);
        // mView = Lista_servicios.this;
        //final ListView lista = (ListView) findViewById(R.id.list_serv);
        // ArrayList <estructura_servicio> arraydatos = new ArrayList<estructura_servicio>();
        //adapter_servicios adapter = new adapter_servicios(arraydatos);
        //lista.setAdapter(adapter);
        Intent intent = this.getIntent();
        //MessageID is the location of the messages for this specific chat
        servid = intent.getStringExtra(servicio_ID);
        catego = comprobar(servid);
        getSupportActionBar().setTitle(catego);
        inicializar();
        agregarlist();


        /**lista.setOnItemClickListener(new AdapterView.OnItemClickListener()
         {
         @Override
         public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
         Intent servicio = new Intent(Lista_servicios.this, Servicio.class);
         servicio.putExtra("Servicio", lista.getItemAtPosition(position).toString());
         startActivity(servicio);
         }
         });*/






    }



    private void inicializar(){
        mFirebaseDatabase = FirebaseDatabase.getInstance();



    }

    private void agregarlist(){
        final DatabaseReference mCurrentUserDatabaseReference = FirebaseDatabase.getInstance().getReference().child("Usuarios");
        Log.d(TAG, "Email sent."+catego);
        Query database = mFirebaseDatabase.getReference().child("Servicios").orderByChild("categoria").equalTo(catego).limitToFirst(100);
        mChatListView = (ListView) findViewById(R.id.list_serv);
        mChatAdapter = new FirebaseListAdapter<estructura_servicio>(this, estructura_servicio.class, R.layout.item_servicio, database) {
            @Override
            protected void populateView(final View v,estructura_servicio model, int position) {
                ((TextView) v.findViewById(R.id.nombre_servicio)).setText(model.getTitulo());
                ((TextView) v.findViewById(R.id.id_precio_list)).setText(model.getPrecio()+" Gs.");

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
        };
        mChatListView.setAdapter(mChatAdapter);
        mChatListView.setOnItemClickListener(new AdapterView.OnItemClickListener(){
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String messageLocation = mChatAdapter.getRef(position).toString();

                if(messageLocation != null){
                    FirebaseAuth ath = FirebaseAuth.getInstance();
                    Intent intent = new Intent(view.getContext(), Servicio.class);
                    String serviciokey = mChatAdapter.getRef(position).getKey();
                    intent.putExtra("id", serviciokey);
                    estructura_servicio chatItem = (estructura_servicio) mChatAdapter.getItem(position);
                    intent.putExtra("id2", chatItem.getTitulo());
                    startActivity(intent);


                }
            }
                //Log.e("TAG", mChatAdapter.getRef(position).toString());

        });
}
    private String comprobar(String aiuda){
        String codigo = aiuda;

        if (codigo.equals("4"))
        {
            cate = "Deporte";
        }
        if (codigo.equals("3")){
            cate = "Reparaciones";
        }
        if (codigo.equals("2")) {
            cate = "Transporte";
        }
        if (codigo.equals("0"))
        {
            cate = "Entretenimiento";
        }
        if (codigo.equals("1"))
        {
            cate = "Hogar";
        }
        if (codigo.equals( "5"))
        {
            cate = "Otros";
        }
        return cate;
    }

}
