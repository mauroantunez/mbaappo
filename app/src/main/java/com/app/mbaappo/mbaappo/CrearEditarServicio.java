package com.app.mbaappo.mbaappo;

import android.app.Activity;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import java.util.ArrayList;


import com.google.android.gms.common.api.GoogleApiClient;

public class CrearEditarServicio extends Activity implements  AdapterView.OnItemSelectedListener{
    public Spinner spTarifa, spCateg;
    public ArrayAdapter<String> aaTarifa;
    public ArrayAdapter<String> aaCateg;

    public Categorias cat = new Categorias();
    String[] opTarifa = new String[]{"Por hora", "Precio Fijo", "A convenir"};
    ArrayList<String> opCateg = new ArrayList<String>();

    private void CargarCategoria(){
        opCateg.add(cat.getNombre());

    }



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crear_editar_servicio);


        spTarifa = (Spinner) findViewById(R.id.spTarifa);
        spTarifa.setOnItemSelectedListener(this);
        aaTarifa = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, opTarifa);

        spTarifa.setAdapter(aaTarifa);

        spCateg = (Spinner) findViewById(R.id.spCateg);
        spCateg.setOnItemSelectedListener(this);
        aaCateg = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, opCateg);

        spTarifa.setAdapter(aaTarifa);

    }


    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */

}
