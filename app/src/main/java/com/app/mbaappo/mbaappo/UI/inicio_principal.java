package com.app.mbaappo.mbaappo.UI;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v4.view.MenuItemCompat;
import android.support.v4.widget.SearchViewCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.app.mbaappo.mbaappo.List.buscador;
import com.app.mbaappo.mbaappo.List.list_categoria_servicio;
import com.app.mbaappo.mbaappo.Modelo.Categorias;
import com.app.mbaappo.mbaappo.R;
import com.app.mbaappo.mbaappo.adapter.AdaptadorCategoria;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.ArrayList;
import java.util.List;

public class inicio_principal extends AppCompatActivity implements SearchViewCompat.OnQueryTextListener, MenuItemCompat.OnActionExpandListener, SearchView.OnQueryTextListener {
    private AdaptadorCategoria adapter;
    private RecyclerView.LayoutManager lManager;
    int codigo;
    String posi;
    private final String TAG ="1";
    private static final int INTERVALO = 2000; //2 segundos para salir
    private long tiempoPrimerClick;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        FirebaseAuth mFirebaseAuthh = FirebaseAuth.getInstance();
        FirebaseAuth.AuthStateListener mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if (user != null) {
                    Intent loginIntent = new Intent(inicio_principal.this, MainActivity.class);
                    loginIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(loginIntent);
                    finish();
                    Log.d(TAG, "onAuthStateChanged:signed_out");
                    Log.d(TAG, "onAuthStateChanged:signed_in:" + user.getUid());
                } else {

                }
                // ...
            }
        };
        mFirebaseAuthh.addAuthStateListener(mAuthListener);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inicio_principal);
        List items = new ArrayList();

        items.add(new Categorias("Entretenimiento",R.drawable.entretenimiento));

        items.add(new Categorias("Hogar",R.drawable.hogar));

        items.add(new Categorias("Transporte",R.drawable.transporte));

        items.add(new Categorias("Reparaciones",R.drawable.reparaciones));

        items.add(new Categorias("Deporte",R.drawable.deporte));

        items.add(new Categorias("Otros",R.drawable.otros));


        final RecyclerView recycler = (RecyclerView) findViewById(R.id.itemsCategoria);
        recycler.setHasFixedSize(true);

        lManager = new LinearLayoutManager(this);
        recycler.setLayoutManager(lManager);

        adapter = new AdaptadorCategoria(items);




        recycler.setAdapter(adapter);
       recycler.addOnItemTouchListener(
                new RecyclerTouchListener(inicio_principal.this, recycler ,new RecyclerTouchListener.OnItemClickListener() {
                    @Override public void onItemClick(View view, int position) {
                        posi = String.valueOf(position);
                        Intent intent = new Intent(view.getContext(), list_categoria_servicio.class);
                        intent.putExtra("id", posi);
                        startActivity(intent);

                        /**if (codigo == 5){
                            String cate;
                             = "Deporte";
                        }
                        if (codigo == 4){
                            cate = "Reparaciones";
                        } if (codigo == 3){
                            cate = "Transporte";
                        } if (codigo == 1){
                            cate = "Entretenimiento";
                        } if (codigo == 2){
                            cate = "Hogar";
                        } if (codigo == 6){
                            cate = "Otros";
                        }*/

                    }

                    @Override public void onLongItemClick(View view, int position) {

                    }
                })
        );


    }

    @Override
    public void onBackPressed() {
        if (tiempoPrimerClick + INTERVALO > System.currentTimeMillis()){
            Intent intent = new Intent(Intent.ACTION_MAIN);
            intent.addCategory(Intent.CATEGORY_HOME);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
        }else {
            Toast.makeText(this, "Vuelve a presionar para salir", Toast.LENGTH_SHORT).show();
        }
        tiempoPrimerClick = System.currentTimeMillis();
        }

    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        getMenuInflater().inflate(R.menu.mnu_iniciar_sesion, menu);
        MenuItem searchItem = menu.findItem(R.id.app_bar_search);

        SearchView searchView = (SearchView) MenuItemCompat.getActionView(searchItem);
        searchView.setOnQueryTextListener(this);

        MenuItemCompat.setOnActionExpandListener(searchItem, this);

        return super.onCreateOptionsMenu(menu);
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();

        if (id == R.id.iniciar) {
            // lo ideal aquí sería hacer un intent para abrir una nueva clase como lo siguiente
            Intent mnu_perf = new Intent(inicio_principal.this, inicio.class);
            mnu_perf.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(mnu_perf);
            finish();
            return true;
        }

        return super.onOptionsItemSelected(item);


    }



    @Override
    public boolean onMenuItemActionExpand(MenuItem item) {
        Toast.makeText(getApplicationContext(), "Buscador activado", Toast.LENGTH_SHORT).show();
        return true;

    }

    @Override
    public boolean onMenuItemActionCollapse(MenuItem item) {
        Toast.makeText(getApplicationContext(), "Buscador desactivado", Toast.LENGTH_SHORT).show();
        return true;
    }

    @Override
    public boolean onQueryTextSubmit(String query) {
        Intent intent = new Intent(inicio_principal.this, buscador.class);
        String serviciokey = query;
        intent.putExtra("id", serviciokey);
        startActivity(intent);
        return true;

    }

    @Override
    public boolean onQueryTextChange(String newText) {
        return false;
    }
}
