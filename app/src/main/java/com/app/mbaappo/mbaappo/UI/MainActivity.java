package com.app.mbaappo.mbaappo.UI;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.view.MenuItemCompat;
import android.support.v4.widget.SearchViewCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.util.Log;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.app.mbaappo.mbaappo.FirebaseUI.FirebaseImageLoader;
import com.app.mbaappo.mbaappo.List.buscador;
import com.app.mbaappo.mbaappo.List.list_categoria_servicio;
import com.app.mbaappo.mbaappo.List.list_historial;
import com.app.mbaappo.mbaappo.Modelo.Categorias;
import com.app.mbaappo.mbaappo.Modelo.Usuario;
import com.app.mbaappo.mbaappo.R;
import com.app.mbaappo.mbaappo.adapter.AdaptadorCategoria;

import com.bumptech.glide.Glide;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;


import java.util.ArrayList;
import java.util.List;

import jp.wasabeef.glide.transformations.CropCircleTransformation;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, SearchViewCompat.OnQueryTextListener, MenuItemCompat.OnActionExpandListener, SearchView.OnQueryTextListener {

    private RecyclerView recycler;
    private AdaptadorCategoria adapter;
    private RecyclerView.LayoutManager lManager;
    private FirebaseAuth auth;
    private FirebaseDatabase mFirebaseDatabase;
    private DatabaseReference mCurrentUserDatabaseReference;
    private FirebaseAuth.AuthStateListener authListener;
    private DatabaseReference nombreUsuario;
    private inicio mail;
    private Context mView;
    private String posi;
    private Toolbar toolbar;
    private DatabaseReference sto;
    private FirebaseDatabase mFirebasedatabase;
    private static final int INTERVALO = 2000; //2 segundos para salir
    private long tiempoPrimerClick;

    ValueEventListener probar ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mView = MainActivity.this;
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        inicializacion();
        asignardatos();

        toolbar.setTitleTextColor(Color.parseColor("#FFFFFF"));
        authListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                if(firebaseAuth.getCurrentUser() != null){

                }
                if(firebaseAuth.getCurrentUser() == null){
                    Intent loginIntent = new Intent(MainActivity.this, inicio.class);
                    loginIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(loginIntent);
                }

            }
        };


        FloatingActionButton publicar = (FloatingActionButton) findViewById(R.id.btn_agregar);
        publicar.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v) {
                Intent crearpublicacion = new Intent(MainActivity.this, CrearEditarServicio.class);
               // crearpublicacion.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(crearpublicacion);
            }

        });
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);


        List items = new ArrayList();

        items.add(new Categorias("Entretenimiento",R.drawable.entretenimiento));

        items.add(new Categorias("Hogar",R.drawable.hogar));

        items.add(new Categorias("Transporte",R.drawable.transporte));

        items.add(new Categorias("Reparaciones",R.drawable.reparaciones));

        items.add(new Categorias("Deporte",R.drawable.deporte));

        items.add(new Categorias("Otros",R.drawable.otros));




        recycler = (RecyclerView) findViewById(R.id.itemsCategoria);
        recycler.setHasFixedSize(true);

        lManager = new LinearLayoutManager(this);
        recycler.setLayoutManager(lManager);

        adapter = new AdaptadorCategoria(items);




        recycler.setAdapter(adapter);
        recycler.addOnItemTouchListener(
                new RecyclerTouchListener(MainActivity.this, recycler ,new RecyclerTouchListener.OnItemClickListener() {
                    @Override public void onItemClick(View view, int position) {
                        posi = String.valueOf(position);
                        Intent intent = new Intent(view.getContext(), list_categoria_servicio.class);
                        intent.putExtra("id", posi);
                        startActivity(intent);
                    }

                    @Override public void onLongItemClick(View view, int position) {

                    }
                }));





    }

    @Override
    protected void onStart() {
        super.onStart();
        auth.addAuthStateListener(authListener);
    }
    public void inicializacion(){
        mFirebaseDatabase = FirebaseDatabase.getInstance();
        auth = FirebaseAuth.getInstance();
        sto = mFirebaseDatabase.getReference().child("Usuarios").child(encodeEmail(auth.getCurrentUser().getEmail()));
    }


    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
            drawer.onFinishTemporaryDetach();
        } else {
            //super.onBackPressed();
        }
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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);

        MenuItem searchItem = menu.findItem(R.id.app_bar_search);

        SearchView searchView = (SearchView) MenuItemCompat.getActionView(searchItem);
        searchView.setOnQueryTextListener(this);

        MenuItemCompat.setOnActionExpandListener(searchItem, this);

        return super.onCreateOptionsMenu(menu);

    }




    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {

        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.mnu_perfil) {
            Intent mnu_perf = new Intent(MainActivity.this, Perfil.class);
           /// mnu_perf.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(mnu_perf);

        } else if (id == R.id.mnu_historial) {
            Intent mnu_per = new Intent(MainActivity.this, list_historial.class);
           // mnu_per.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(mnu_per);

        } else if (id == R.id.mnu_servicios) {
            Intent mnu_serv = new Intent(MainActivity.this, servicios_realizados.class);
           // mnu_serv.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(mnu_serv);

        } else if (id == R.id.mnu_solicitudes) {
            Intent mnu_serv = new Intent(MainActivity.this, servicio_solicitado.class);
          //  mnu_serv.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(mnu_serv);

        }
        else if (id == R.id.cerrar_sesion_menu) {
            cerrar_sesion();

        }
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }


    private void cerrar_sesion() {
        auth.signOut();
    }

    public String encodeEmail(String userEmail) {
        return userEmail.replace(".", ",");
    }

    public void asignardatos(){
        sto.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if(dataSnapshot.exists()){
                    try{
                        Usuario user = dataSnapshot.getValue(Usuario.class);
                        Log.e("Err", user.getNombre()+user.getApellido());
                        final TextView usuarioTxt = (TextView) findViewById(R.id.id_editar_nombre );
                        usuarioTxt.setText(user.getNombre()+" "+user.getApellido());
                         TextView mailTxt = (TextView) findViewById(R.id.mailUsuarioNavHeader);
                        mailTxt.setText(auth.getCurrentUser().getEmail());
                        Log.e("Err", user.getNombre()+user.getApellido());
                        ImageView imageperfil = (ImageView) findViewById(R.id.imageperfil);
                        StorageReference storageRef = FirebaseStorage.getInstance()
                                .getReference().child(user.getProfilePicLocation());
                        Glide.with(MainActivity.this)
                                .using(new FirebaseImageLoader())
                                .load(storageRef)
                                //.bitmapTransform(new CropCircleTransformation(mView))
                                .into(imageperfil); }
                catch(Exception e){

                }}
                }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });  }





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
        Intent intent = new Intent(MainActivity.this, buscador.class);
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