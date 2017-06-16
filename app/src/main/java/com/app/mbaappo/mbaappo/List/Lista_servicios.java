package com.app.mbaappo.mbaappo.List;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.app.mbaappo.mbaappo.Modelo.estructura_servicio;
import com.app.mbaappo.mbaappo.R;
import com.app.mbaappo.mbaappo.UI.Servicio;
import com.app.mbaappo.mbaappo.adapter.adapter_servicios;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class Lista_servicios extends AppCompatActivity {
    private DatabaseReference database;
    private FirebaseDatabase mFirebaseDatabase;
    estructura_servicio datos;
    ArrayList <estructura_servicio> arraydatos = new ArrayList<estructura_servicio>();
    private Context mView;
    private ListView mMessageListView;
    private adapter_servicios mMessageAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista_servicios);
        mView = Lista_servicios.this;
        final ListView lista = (ListView) findViewById(R.id.list_serv);
        ArrayList <estructura_servicio> arraydatos = new ArrayList<estructura_servicio>();
       adapter_servicios adapter = new adapter_servicios(arraydatos);
        lista.setAdapter(adapter);


        lista.setOnItemClickListener(new AdapterView.OnItemClickListener()
        {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent servicio = new Intent(Lista_servicios.this, Servicio.class);
                servicio.putExtra("Servicio", lista.getItemAtPosition(position).toString());
                startActivity(servicio);
            }
        });






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
    }
    private void agregarlist(){
        database.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                 estructura_servicio servicio = dataSnapshot.getValue(estructura_servicio.class);

                // datos = new datos_lista_servicios(storageRef,servicio.getTitulo(),servicio.getPrecio(),servicio.getNombreUsuario(),servicio.getRating());
                datos = new estructura_servicio(servicio.getTitulo(),servicio.getPrecio(),servicio.getDescripcion(),servicio.getRating(),servicio.getUrlfoto());
                arraydatos.add(datos);

            }
            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });


}
     }
