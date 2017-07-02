package com.app.mbaappo.mbaappo.UI;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.app.mbaappo.mbaappo.R;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import com.app.mbaappo.mbaappo.Modelo.Usuario;
import com.app.mbaappo.mbaappo.Modelo.estructura_servicio;
import com.app.mbaappo.mbaappo.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import static com.app.mbaappo.mbaappo.R.id.spTarifa;

/**
 * Created by Antunez on 28/6/2017.
 */

public class editar_servicios extends AppCompatActivity {
    FirebaseDatabase database_serv;
    String servicio_ID = "id";
    String servid;
    DatabaseReference serv;
    String categoriasp, tarifasp;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editar_servicios);
        Intent intent = this.getIntent();
        //MessageID is the location of the messages for this specific chat
        servid = intent.getStringExtra(servicio_ID);

        inicializar();
        editarservicio();

    }
    private void inicializar(){
        FirebaseAuth auth = FirebaseAuth.getInstance();
        database_serv = FirebaseDatabase.getInstance();
        serv = database_serv.getReference().child("Servicios").child(servid);
    }
    private void asignarspinner(){

    }
    private void editarservicio(){
        final String keys = servid;
        serv.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                final estructura_servicio servicio = dataSnapshot.getValue(estructura_servicio.class);
                final Spinner spTarifa, spCateg;
                final ArrayAdapter<String> aaTarifa;
                final ArrayAdapter<String> aaCateg;
                final String[] opTarifa = new String[]{"","Por hora", "Precio Fijo", "A convenir"};
                final String[] opCateg = new String[]{"","Deporte", "Reparaciones","Transporte","Entretenimiento","Hogar","Otros"};
                spTarifa = (Spinner) findViewById(R.id.spTarifa_editar);
                aaTarifa = new ArrayAdapter<String>(editar_servicios.this, android.R.layout.simple_spinner_item, opTarifa);
                spTarifa.setAdapter(aaTarifa);

                spTarifa.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                        tarifasp = opTarifa[position];
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> parent) {

                    }
                });

                spCateg = (Spinner) findViewById(R.id.spCategoria_editar);
                aaCateg = new ArrayAdapter<String>(editar_servicios.this, android.R.layout.simple_spinner_item, opCateg);
                spCateg.setAdapter(aaCateg);
                spCateg.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                        categoriasp = opCateg[position];
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> parent) {

                    }

                });

                final EditText edittitulo,edidescripcion,editprecio;


                edittitulo = (EditText) findViewById(R.id.edittitulo_editar);
                edidescripcion = (EditText) findViewById(R.id.editdescripcion_editar);
                editprecio = (EditText) findViewById(R.id.editprecio_editar);
                try{
                edittitulo.setText(servicio.getTitulo());
                edidescripcion.setText(servicio.getDescripcion());
                editprecio.setText(servicio.getPrecio());
                }catch (Exception e){

                }

                final FirebaseAuth auth = FirebaseAuth.getInstance();
                final Button publicar = (Button) findViewById(R.id.btn_guardar_modificacion);
                final FirebaseDatabase aiuda = FirebaseDatabase.getInstance();
                final DatabaseReference editlis = FirebaseDatabase.getInstance().getReference().child("ListaServiciosUsuario").child(encodeEmail(auth.getCurrentUser().getEmail())).child(servicio.getKey());
                final DatabaseReference muserguardar = aiuda.getReference().child("Servicios").child(keys);
                publicar.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        final  String tituloguardar =  edittitulo.getText().toString().trim();
                        final String descripcionguardar = edidescripcion.getText().toString().trim();
                        final String precioguardar = editprecio.getText().toString().trim();

                        if (tituloguardar.equals(null)||tituloguardar.length() == 0 || tituloguardar.equals("")){

                        }else{
                            muserguardar.child("titulo").setValue(tituloguardar);
                            editlis.child("titulo").setValue(tituloguardar);
                        }
                        if (descripcionguardar.equals(null)||descripcionguardar.length() == 0 || descripcionguardar.equals("")){

                        }else{
                            muserguardar.child("descripcion").setValue(descripcionguardar);
                        }
                        if (precioguardar.equals(null)||precioguardar.length() == 0 || precioguardar.equals("")){

                        }else {
                            muserguardar.child("precio").setValue(precioguardar);
                            editlis.child("precio").setValue(precioguardar);
                        }
                        if (categoriasp.equals(null)||categoriasp.length() == 0 || categoriasp.equals("")){

                        }else{
                            muserguardar.child("categoria").setValue(categoriasp);
                        }
                        if (tarifasp.equals(null)||tarifasp.length() == 0 || tarifasp.equals("")){

                        }else {
                            muserguardar.child("tarifa").setValue(tarifasp);
                        }


                                Intent loginIntent = new Intent(editar_servicios.this, MainActivity.class);
                              // loginIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                startActivity(loginIntent);







                       // asignarspinner();

                    }
                });
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }

    @Override
    public void onBackPressed() {
        Intent loginIntent = new Intent(editar_servicios.this, servicios_realizados.class);
      //  loginIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(loginIntent);
    }
    public String encodeEmail(String userEmail) {
        return userEmail.replace(".", ",");
    }
}

