package com.app.mbaappo.mbaappo;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;

import java.util.ArrayList;

public class list_historial extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_historial);

        final ListView lista = (ListView) findViewById(R.id.list_historial);
        ArrayList<datos_lista_historial> arraydatos = new ArrayList<datos_lista_historial>();
        datos_lista_historial datos;

        datos = new datos_lista_historial(getResources().getDrawable(R.drawable.ic_people),"Electricista","Ivan Velazquez","23/05/2017");
        arraydatos.add(datos);
        datos = new datos_lista_historial(getResources().getDrawable(R.drawable.ic_people),"Profesora de Matematica","Antonella Duarte","21/05/2017");
        arraydatos.add(datos);
        datos = new datos_lista_historial(getResources().getDrawable(R.drawable.ic_people),"Mecanico","Mauro Antunez","20/05/2017");
        arraydatos.add(datos);
        datos = new datos_lista_historial(getResources().getDrawable(R.drawable.ic_people),"Pintor","Fabricio Mendoza","16/05/2017");
        arraydatos.add(datos);
        datos = new datos_lista_historial(getResources().getDrawable(R.drawable.ic_people),"Profesor de Ingles","Luis Mendez","13/05/2017");
        arraydatos.add(datos);
        datos = new datos_lista_historial(getResources().getDrawable(R.drawable.ic_people),"Dj de eventos","Diego Oviedo","10/05/2017");
        arraydatos.add(datos);

        adapter_historial adapter = new adapter_historial(this, arraydatos);
        lista.setAdapter(adapter);
    }
}
