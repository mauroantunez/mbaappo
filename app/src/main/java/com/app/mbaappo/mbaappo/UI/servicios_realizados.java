package com.app.mbaappo.mbaappo.UI;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.app.mbaappo.mbaappo.FirebaseUI.FirebaseListAdapter;
import com.app.mbaappo.mbaappo.Modelo.Categorias;
import com.app.mbaappo.mbaappo.Modelo.Usuario;
import com.app.mbaappo.mbaappo.Modelo.estructura_categoria;
import com.app.mbaappo.mbaappo.Modelo.estructura_servicio;
import com.app.mbaappo.mbaappo.Modelo.lista_servicio_ofrecido;
import com.app.mbaappo.mbaappo.R;
import com.app.mbaappo.mbaappo.adapter.adapter_serv_realizados;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class servicios_realizados extends AppCompatActivity {
    private DatabaseReference database;
    private FirebaseDatabase mFirebaseDatabase;
    private FirebaseListAdapter mChatAdapter;
    private ListView mChatListView;
    private DatabaseReference mCurrentUserDatabaseReference;
    private FirebaseAuth auth;
    private DatabaseReference databasee;
    private FirebaseDatabase mFirebaseDatabasee;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_servicios_realizados);
        inicializar();
        agregarlis();
    }
    private void inicializar(){
        auth=FirebaseAuth.getInstance();
        mFirebaseDatabase = FirebaseDatabase.getInstance();
        database = mFirebaseDatabase.getReference().child("ListaServiciosUsuario").child(encodeEmail(auth.getCurrentUser().getEmail()));
        mFirebaseDatabasee = FirebaseDatabase.getInstance();
        databasee = mFirebaseDatabasee.getReference().child("Servicios");

    }
    private void agregarlis(){
        mChatListView = (ListView) findViewById(R.id.list_serv_realizados);
        mChatAdapter = new FirebaseListAdapter<estructura_servicio>(this, estructura_servicio.class, R.layout.item_serv_realizados, database) {
            @Override
            protected void populateView(final View v, final estructura_servicio model, int position) {


                        ((TextView) v.findViewById(R.id.nombre_serv_reali)).setText(model.getTitulo());
                        ((TextView) v.findViewById(R.id.precio_serv_reali)).setText(model.getPrecio());
                        Button btn_eliminar = (Button) v.findViewById(R.id.btn_eliminar_serv);
                        btn_eliminar.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                try{
                                database.child(model.getKey()).removeValue();
                                databasee.child(model.getKey()).removeValue();
                                }
                                catch (Exception e){

                                }

                            }


                        });
                Button bt_editar = (Button) v.findViewById(R.id.btn_editar_serv);
                bt_editar.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(servicios_realizados.this, editar_servicios.class);
                        String serviciokey = model.getKey();
                        intent.putExtra("id", serviciokey);
                        startActivity(intent);
                        finish();

                    }
                });
            }





    };
        mChatListView.setAdapter(mChatAdapter);
    }
    public String encodeEmail(String userEmail) {
        return userEmail.replace(".", ",");
    }
    public String encodeEmaill(String userEmail) {
        return userEmail.replace(".", "_");
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(servicios_realizados.this, MainActivity.class);

        startActivity(intent);
    }
}