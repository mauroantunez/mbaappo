package com.app.mbaappo.mbaappo.UI;
import android.app.Activity;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.design.widget.TextInputLayout;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import com.app.mbaappo.mbaappo.Modelo.Usuario;
import com.app.mbaappo.mbaappo.Modelo.estructura_servicio;
import com.app.mbaappo.mbaappo.Modelo.lista_servicio_ofrecido;
import com.app.mbaappo.mbaappo.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.weiwangcn.betterspinner.library.material.MaterialBetterSpinner;

public class CrearEditarServicio extends Activity implements  AdapterView.OnItemSelectedListener{
    /** Spinner*/
    public Spinner spTarifa, spCateg;
    public ArrayAdapter<String> aaTarifa;
    public ArrayAdapter<String> aaCateg;
    String[] opTarifa = new String[]{"Por hora", "Precio Fijo", "A convenir"};
    String[] opCateg = new String[]{"Deporte", "Profesores", "Reparaciones","Transporte","Bienestar y Salud","Entretenimiento","Hogar","Mascotas","Cuidador"};
    /**-------------------------------------------------------------------------------------------------------------------------------------------------------------*/


    /** Variables para Referencia*/
    Button publicar;
    EditText edittitulo,edidescripcion,editprecio;
    String categoriasp;
    /**-------------------------------------------*/

    /** Firebase*/
    private FirebaseAuth auth;
    private DatabaseReference database;
    private DatabaseReference agregar_s;
    DatabaseReference usuario_actual_db;
    private FirebaseDatabase l_servicio;
    private DatabaseReference lista_servicios;

    /**----------------------------------------------------------*/

    public static String nombre_usuario,urlfoto;
    /**--------------------------------------------------------*/

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crear_editar_servicio);
        /** Referencias*/
        publicar = (Button) findViewById(R.id.btn_publicar);
        edittitulo = (EditText) findViewById(R.id.edittitulo_);
        edidescripcion = (EditText) findViewById(R.id.editdescripcion_);
        editprecio = (EditText) findViewById(R.id.editprecio_);
        /**---------------------------------------------------------------*/

        inicializar();

        /** Spinner Tarifa*/
        spTarifa = (Spinner) findViewById(R.id.spTarifa);
        spTarifa.setOnItemSelectedListener(this);
        aaTarifa = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, opTarifa);
        spTarifa.setAdapter(aaTarifa);

        /**Spinner Categoria*/
        spCateg = (Spinner) findViewById(R.id.spCateg);
        spCateg.setOnItemSelectedListener(this);
        aaCateg = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, opCateg);
        spCateg.setAdapter(aaCateg);
        spCateg.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                categoriasp= opCateg[position];
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        /**--------------------------------------------------------------------------------------------*/


        publicar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                guardardatos();
            }
        });





    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    private void guardardatos(){
        final String nombre = edittitulo.getText().toString().trim();
        final String descripcion = edidescripcion.getText().toString().trim();
        final String precio = editprecio.getText().toString().trim();
        agregar_servicio(nombre,descripcion,precio);


    }
    public void agregar_servicio(String nombre, String descripcionn,String precios){
        final String uid = auth.getCurrentUser().getUid();
        final String email = encodeEmail(auth.getCurrentUser().getEmail());
        final String titulo = nombre;
        final String precio = precios;
        final String descripcion = descripcionn;
        final float rating = (float) 0.0;
        final String categoria = categoriasp;
        //usuario_actual_db.child("titulo").setValue(nombre);
        //usuario_actual_db.child("uid").setValue(uid);
        //usuario_actual_db.child("precio").setValue(precio);
        //usuario_actual_db.child("descripcion").setValue(descripcion);
        //usuario_actual_db.child("rating").setValue(0.0);
        //usuario_actual_db.child("categoria").setValue(categoria);
        lista_servicio_ofrecido servicio_reali = new lista_servicio_ofrecido(titulo,precio);
        lista_servicios.setValue(servicio_reali);
        database.addValueEventListener(new ValueEventListener() {
           @Override
           public void onDataChange(DataSnapshot dataSnapshot) {
               Usuario user = dataSnapshot.getValue(Usuario.class);
                if(user != null){
                       nombre_usuario = user.getNombre()+user.getApellido();
                        //usuario_actual_db.child("NombreUsuario").setValue(nombre_usuario);
                        urlfoto = user.getProfilePicLocation();
                    //usuario_actual_db.child("urlfoto").setValue(urlfoto);
                    estructura_servicio servicio = new estructura_servicio(titulo,uid,precio,nombre_usuario,descripcion,rating,categoria,urlfoto,email);
                    usuario_actual_db.setValue(servicio);


        }}

           @Override
           public void onCancelled(DatabaseError databaseError) {

            }
        });

    }


    public String encodeEmail(String userEmail) {
        return userEmail.replace(".", ",");
    }
    //public String obtenernombreusuario(String nombre)
    //{
      //  nombre_usuario = nombre_usuario+nombre;
       // return nombre_usuario;
    //}
    public String encodeEmaill(String userEmail) {
        return userEmail.replace(".", "_");
    }
private void inicializar(){
    auth = FirebaseAuth.getInstance();
    l_servicio = FirebaseDatabase.getInstance();
    database = FirebaseDatabase.getInstance().getReference().child("Usuarios").child(encodeEmail(auth.getCurrentUser().getEmail()));
    agregar_s = FirebaseDatabase.getInstance().getReference();
    usuario_actual_db = agregar_s.child("Servicios").push();
    lista_servicios = l_servicio.getReference().child("ListaServiciosUsuario").child(encodeEmail(auth.getCurrentUser().getEmail())).child(usuario_actual_db.getKey());
}
}