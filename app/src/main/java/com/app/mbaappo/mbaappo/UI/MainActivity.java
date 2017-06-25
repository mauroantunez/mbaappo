package com.app.mbaappo.mbaappo.UI;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
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
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.TextView;

import com.app.mbaappo.mbaappo.FirebaseUI.FirebaseImageLoader;
import com.app.mbaappo.mbaappo.List.Lista_servicios;
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
        implements NavigationView.OnNavigationItemSelectedListener {

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mView = MainActivity.this;
        auth = FirebaseAuth.getInstance();
        String mail = encodeEmail(auth.getCurrentUser().getEmail());
        inicializar(mail);
        asignardatos();

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
                        crearpublicacion.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
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

        /*Datos de prueba*/
        List items = new ArrayList();

        items.add(new Categorias("Categoria 1",R.drawable.entretenimiento));

        items.add(new Categorias("Categoria 2",R.drawable.hogar));

        items.add(new Categorias("Categoria 3",R.drawable.transporte));

        items.add(new Categorias("Categoria 4",R.drawable.reparaciones));

        items.add(new Categorias("Categoria 5",R.drawable.deporte));

        items.add(new Categorias("Categoria 6",R.drawable.otros));

        /*****************/


        recycler = (RecyclerView) findViewById(R.id.itemsCategoria);
        recycler.setHasFixedSize(true);

        lManager = new LinearLayoutManager(this);
        recycler.setLayoutManager(lManager);

        adapter = new AdaptadorCategoria(items);


        adapter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent mainIntent = new Intent(MainActivity.this, Lista_servicios.class);
                mainIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(mainIntent);
            }
        });

        recycler.setAdapter(adapter);



    }

    @Override
    protected void onStart() {
        super.onStart();
        auth.addAuthStateListener(authListener);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            //super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }




    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.mnu_perfil) {
            Intent mnu_perf = new Intent(MainActivity.this, Perfil.class);
            mnu_perf.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(mnu_perf);
        } else if (id == R.id.mnu_historial) {
            Intent mnu_per = new Intent(MainActivity.this, list_historial.class);
            mnu_per.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(mnu_per);
        } else if (id == R.id.mnu_servicios) {
            Intent mnu_serv = new Intent(MainActivity.this, servicios_realizados.class);
            mnu_serv.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(mnu_serv);
        } else if (id == R.id.mnu_solicitudes) {
            Intent mnu_serv = new Intent(MainActivity.this, servicio_solicitado.class);
            mnu_serv.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
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
    private void inicializar(String mail){

        mFirebaseDatabase = FirebaseDatabase.getInstance();
        mCurrentUserDatabaseReference = mFirebaseDatabase.getReference().child("Usuarios" + "/" +mail);
    }
    public String encodeEmail(String userEmail) {
        return userEmail.replace(".", ",");
    }

    public void asignardatos(){



        mCurrentUserDatabaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Usuario user = dataSnapshot.getValue(Usuario.class);
                try{
                    if(user.getNombre() != null){
                        final TextView usuarioTxt = (TextView) findViewById(R.id.nombreUsuarioNavHeader);
                        usuarioTxt.setText(user.getNombre()+" "+user.getApellido());
                        final TextView mailTxt = (TextView) findViewById(R.id.mailUsuarioNavHeader);
                        mailTxt.setText(auth.getCurrentUser().getEmail());
                    }
                    if(user.getProfilePicLocation() != null){
                        final ImageView imageperfil = (ImageView) findViewById(R.id.imageperfil);
                        StorageReference storageRef = FirebaseStorage.getInstance()
                                .getReference().child(user.getProfilePicLocation());

                        Glide.with(mView)
                                .using(new FirebaseImageLoader())
                                .load(storageRef)
                                .bitmapTransform(new CropCircleTransformation(mView))
                                .into(imageperfil);
                    }
                }catch (Exception e){
                    Log.e("Err", "glide");
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

}

