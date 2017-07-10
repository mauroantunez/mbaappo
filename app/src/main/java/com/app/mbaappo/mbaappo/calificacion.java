package com.app.mbaappo.mbaappo;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.app.mbaappo.mbaappo.FirebaseUI.FirebaseListAdapter;
import com.app.mbaappo.mbaappo.Modelo.Comentario;
import com.app.mbaappo.mbaappo.Modelo.Usuario;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.Date;

public class calificacion extends AppCompatActivity {
    private FirebaseDatabase mFirebaseDatabase;
    private DatabaseReference mComentarioref;
    private String serviceid;
    private ListView mComentarioListView;
    private FirebaseListAdapter mComentarioAdapter;
    private DatabaseReference mServiceref;
    private EditText elcomentario;

    private DatabaseReference mUsuarioref;
    private FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calificacion);
        Intent intent = this.getIntent();
        //MessageID is the location of the messages for this specific chat
        serviceid = intent.getStringExtra("id");
        inicializar();
        agregar_lista();
        elcomentario = (EditText) findViewById(R.id.messageEditText);
        final Button comentar = (Button) findViewById(R.id.sendcomentar);

        comentar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                comentar();
            }
        });

    }

    private void inicializar(){
        mFirebaseDatabase = FirebaseDatabase.getInstance();
        auth = FirebaseAuth.getInstance();
        mComentarioref = mFirebaseDatabase.getReference().child("Comentarios").child(serviceid);
        mServiceref = mFirebaseDatabase.getReference().child("Servicios").child(serviceid);
        mUsuarioref = mFirebaseDatabase.getReference().child("Usuarios").child(encodeEmail(auth.getCurrentUser().getEmail()));

    }

    private void agregar_lista(){
        Query probar = mComentarioref.orderByPriority();
        mComentarioListView = (ListView) findViewById(R.id.id_list_comentario);
        mComentarioAdapter = new FirebaseListAdapter<Comentario>(this, Comentario.class, R.layout.item_comentario, probar) {
            @Override
            protected void populateView(View v, Comentario model, int position) {
                Log.e("Err", String.valueOf(position));
                Log.e("Err", model.getElcomentario());
                ((TextView) v.findViewById(R.id.messageTextView)).setText(model.getElcomentario());
                ((TextView) v.findViewById(R.id.nameTextView)).setText(model.getNombre());
                ((TextView) v.findViewById(R.id.namefecha)).setText(model.getFecha());
                ((RatingBar) v.findViewById(R.id.ratingBar_come)).setRating(model.getRating());
            }
        };

        mComentarioListView.setAdapter(mComentarioAdapter);
    }
    private void comentar(){
                final String elcomentarioText = elcomentario.getText().toString().trim();
                final DatabaseReference mcomen = mComentarioref.push();
        mUsuarioref.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        Usuario user = dataSnapshot.getValue(Usuario.class);
                        SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
                        Date date = new Date();
                        String timestamp = dateFormat.format(date);
                       // SimpleDateFormat dateFormathora = new SimpleDateFormat("HH:mm");
                        //Date datehora = new Date();
                      //  String timestamphora = dateFormathora.format(datehora);
                        Comentario coment = new Comentario(mcomen.getKey(),elcomentarioText,user.getNombre()+" "+user.getApellido(),timestamp);
                        mcomen.setValue(coment);


                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });


        final RatingBar c_rating = (RatingBar) findViewById(R.id.ratingBar_cali_come);
        c_rating.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {
                mcomen.setValue(rating);
            }
        });

            }

    public String encodeEmail(String userEmail) {
        return userEmail.replace(".", ",");
    }
}
