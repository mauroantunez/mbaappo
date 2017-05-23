package com.app.mbaappo.mbaappo;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;

public class Lista_servicios extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista_servicios);

        final ListView lista = (ListView) findViewById(R.id.list_serv);
        ArrayList <datos_lista_servicios> arraydatos = new ArrayList<datos_lista_servicios>();
        datos_lista_servicios datos;

        datos = new datos_lista_servicios(getResources().getDrawable(R.drawable.ic_people),"Nombre de Servicio","Precio de Servicio","Nombre de usuario",3.1f );
        arraydatos.add(datos);
        datos = new datos_lista_servicios(getResources().getDrawable(R.drawable.ic_people),"Nombre de Servicio","Precio de Servicio","Nombre de usuario",2.8f );
        arraydatos.add(datos);
        datos = new datos_lista_servicios(getResources().getDrawable(R.drawable.ic_people),"Nombre de Servicio","Precio de Servicio","Nombre de usuario",4.1f );
        arraydatos.add(datos);
        datos = new datos_lista_servicios(getResources().getDrawable(R.drawable.ic_people),"Nombre de Servicio","Precio de Servicio","Nombre de usuario",4.6f );
        arraydatos.add(datos);
        datos = new datos_lista_servicios(getResources().getDrawable(R.drawable.ic_people),"Nombre de Servicio","Precio de Servicio","Nombre de usuario",2.5f );
        arraydatos.add(datos);
        datos = new datos_lista_servicios(getResources().getDrawable(R.drawable.ic_people),"Nombre de Servicio","Precio de Servicio","Nombre de usuario",4.0f );
        arraydatos.add(datos);

        adapter_servicios adapter = new adapter_servicios(this, arraydatos);
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

}
