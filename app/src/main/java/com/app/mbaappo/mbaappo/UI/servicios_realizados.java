package com.app.mbaappo.mbaappo.UI;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;

import com.app.mbaappo.mbaappo.R;
import com.app.mbaappo.mbaappo.adapter.adapter_serv_realizados;

import java.util.ArrayList;

public class servicios_realizados extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_servicios_realizados);

        final ListView lista = (ListView) findViewById(R.id.list_serv_realizados);
        ArrayList<datos_serv_realizados> arraydatos = new ArrayList<datos_serv_realizados>();
        datos_serv_realizados datos;

        datos = new datos_serv_realizados("Electricista","80.000Gs/hora");
        arraydatos.add(datos);
        datos = new datos_serv_realizados("Profesora de Matematica","20.000Gs/Hora");
        arraydatos.add(datos);
        datos = new datos_serv_realizados("Mecanico","A convenir");
        arraydatos.add(datos);
        datos = new datos_serv_realizados("Pintor","50.000Gs/hora");
        arraydatos.add(datos);
        datos = new datos_serv_realizados("Profesor de Ingles","30.000Gs/hora");
        arraydatos.add(datos);
        datos = new datos_serv_realizados("Dj de eventos","40.000Gs/hora");
        arraydatos.add(datos);

        adapter_serv_realizados adapter = new adapter_serv_realizados(this, arraydatos);
        lista.setAdapter(adapter);
    }
}
