package com.app.mbaappo.mbaappo;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

public class inicio_principal extends AppCompatActivity {
    private AdaptadorCategoria adapter;
    private RecyclerView.LayoutManager lManager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inicio_principal);
        List items = new ArrayList();

        items.add(new Categorias("Categoria 1",R.drawable.entretenimiento));

        items.add(new Categorias("Categoria 2",R.drawable.hogar));

        items.add(new Categorias("Categoria 3",R.drawable.transporte));

        items.add(new Categorias("Categoria 4",R.drawable.reparaciones));

        items.add(new Categorias("Categoria 5",R.drawable.deporte));

        items.add(new Categorias("Categoria 6",R.drawable.otros));

        /*****************/
        RecyclerView recycler = (RecyclerView) findViewById(R.id.itemsCategoria);
        recycler.setHasFixedSize(true);

        lManager = new LinearLayoutManager(this);
        recycler.setLayoutManager(lManager);

        adapter = new AdaptadorCategoria(items);

        adapter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent mainIntent = new Intent(inicio_principal.this, Lista_servicios.class);
                mainIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(mainIntent);
            }
        });


        recycler.setAdapter(adapter);
    }
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.mnu_iniciar_sesion, menu);
        getMenuInflater().inflate(R.menu.main,menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();

        if (id == R.id.iniciar) {
            // lo ideal aquí sería hacer un intent para abrir una nueva clase como lo siguiente
            Intent mnu_perf = new Intent(inicio_principal.this, inicio.class);
            mnu_perf.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(mnu_perf);
            return true;
        }

        return super.onOptionsItemSelected(item);


    }
}
