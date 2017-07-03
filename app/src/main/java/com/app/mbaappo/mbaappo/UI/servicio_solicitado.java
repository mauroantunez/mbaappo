package com.app.mbaappo.mbaappo.UI;

import android.app.Dialog;
import android.app.assist.AssistStructure;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.app.mbaappo.mbaappo.FirebaseUI.FirebaseImageLoader;
import com.app.mbaappo.mbaappo.FirebaseUI.FirebaseListAdapter;
import com.app.mbaappo.mbaappo.Modelo.Chat;
import com.app.mbaappo.mbaappo.Modelo.FriendlyMessage;
import com.app.mbaappo.mbaappo.Modelo.Historial;
import com.app.mbaappo.mbaappo.Modelo.Usuario;
import com.app.mbaappo.mbaappo.Modelo.buzon;
import com.app.mbaappo.mbaappo.Modelo.estructura_servicio;
import com.app.mbaappo.mbaappo.R;
import com.bumptech.glide.Glide;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.text.SimpleDateFormat;
import java.util.Date;

import jp.wasabeef.glide.transformations.CropCircleTransformation;

public class servicio_solicitado extends AppCompatActivity {
    private String key_chat;

    /**------------------------Firebase---------------------*/
    /**private FirebaseDatabase mFirebasedatabase;
    private DatabaseReference database;
    private DatabaseReference mchat;
    private FirebaseAuth auth;
    private DatabaseReference muser;
    /**-----------------------------------------------------*/
    private FirebaseListAdapter mChatAdapter;
    private ListView mChatListView;

    public servicio_solicitado(String key_chat) {
        this.key_chat = key_chat;
    }
    public  servicio_solicitado(){}

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_servicio_solicitado);
        inicializacion();
        agregarlista();
    }
    public void inicializacion(){

       /** final FirebaseAuth auth = FirebaseAuth.getInstance();
        final FirebaseDatabase mFirebasedatabase = FirebaseDatabase.getInstance();
        final DatabaseReference muser = mFirebasedatabase.getReference().child("Usuario");
        final DatabaseReference database = mFirebasedatabase.getReference().child("Servicio");
        final DatabaseReference mchat = mFirebasedatabase.getReference().child("Chat").child(encodeEmail(auth.getCurrentUser().getEmail()));*/
    }
    public void agregarlista(){
        final FirebaseAuth auth = FirebaseAuth.getInstance();
        final FirebaseDatabase mFirebasedatabase = FirebaseDatabase.getInstance();
        final DatabaseReference muser = mFirebasedatabase.getReference().child("Usuarios");
        final DatabaseReference database = mFirebasedatabase.getReference().child("Servicios");
        final DatabaseReference mchat = mFirebasedatabase.getReference().child("Buzon").child(encodeEmail(auth.getCurrentUser().getEmail()));
        mChatListView = (ListView) findViewById(R.id.lis_servicio_solicitado);
        mChatAdapter = new FirebaseListAdapter <Chat>(this, Chat.class, R.layout.item_servicio_solicitado, mchat) {
            @Override
            protected void populateView(final View v, final Chat model, int position) {

               if (model.getKey_usuario_contrato().equals(encodeEmail(auth.getCurrentUser().getEmail()))){
                  muser.child(model.getKey_usuario_contratado()).addValueEventListener(new ValueEventListener() {
                     @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                         Usuario user = dataSnapshot.getValue(Usuario.class);
                            if (user != null){
                            ((TextView) v.findViewById(R.id.text_titulo_servicio)).setText(user.getNombre()+" "+user.getApellido());
                            }
                            if (user.getProfilePicLocation()!=null){
                            final ImageView image = (ImageView) v.findViewById(R.id.photo_servicio_solicitado);
                            StorageReference url = FirebaseStorage.getInstance().getReference().child(user.getProfilePicLocation());
                            Glide.with(v.getContext())
                                    .using(new FirebaseImageLoader())
                                    .load(url)
                                    .bitmapTransform(new CropCircleTransformation(v.getContext()))
                                    .into(image);
                        }
                         database.child(model.getKey_servicio()).addValueEventListener(new ValueEventListener() {
                             @Override
                             public void onDataChange(DataSnapshot dataSnapshot) {
                                 estructura_servicio servicio = dataSnapshot.getValue(estructura_servicio.class);
                                 if (servicio != null){
                                     ((TextView) v.findViewById(R.id.text_nombre_usuario)).setText(servicio.getTitulo());
                                 }
                             }

                             @Override
                             public void onCancelled(DatabaseError databaseError) {

                             }
                         });

                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });

            }
            if (model.getKey_usuario_contratado().equals(encodeEmail(auth.getCurrentUser().getEmail()))){
                muser.child(model.getKey_usuario_contrato()).addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        Usuario user = dataSnapshot.getValue(Usuario.class);
                        if (user != null){
                            ((TextView) v.findViewById(R.id.text_titulo_servicio)).setText(user.getNombre()+" "+user.getApellido());
                        }
                        if (user.getProfilePicLocation()!=null){
                            final ImageView image_ = (ImageView) v.findViewById(R.id.photo_servicio_solicitado);
                            StorageReference url_ = FirebaseStorage.getInstance().getReference().child(user.getProfilePicLocation());
                            Glide.with(v.getContext())
                                    .using(new FirebaseImageLoader())
                                    .load(url_)
                                    .bitmapTransform(new CropCircleTransformation(v.getContext()))
                                    .into(image_);
                        }
                        if (user.getDireccion() != null){
                            ((TextView) v.findViewById(R.id.text_nombre_usuario)).setText(user.getDireccion());
                        }
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });

            }

            }
        };
        mChatListView.setAdapter(mChatAdapter);
        mChatListView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                final String serviciokeys = mChatAdapter.getRef(position).getKey();
                DatabaseReference chat_ = mFirebasedatabase.getReference().child("Chat").child(encodeEmail(auth.getCurrentUser().getEmail())).child(serviciokeys);
                chat_.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        Chat chat = dataSnapshot.getValue(Chat.class);
                        if (chat.getKey_usuario_contratado().equals(encodeEmail(auth.getCurrentUser().getEmail()))){
                           createSimpleDialogg(serviciokeys);
                        }
                        if (chat.getKey_usuario_contrato().equals(encodeEmail(auth.getCurrentUser().getEmail()))){
                            createSimpleDialog(serviciokeys);
                        }
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });
                //createSimpleDialog(serviciokeys);
                return true;
            }
        });
        mChatListView.setOnItemClickListener(new AdapterView.OnItemClickListener(){
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String messageLocation = mChatAdapter.getRef(position).toString();

                if(messageLocation != null){
                    final Intent intent = new Intent(view.getContext(), mensajeria.class);
                    String serviciokey = mChatAdapter.getRef(position).getKey();
                    intent.putExtra("idmessage", serviciokey);
                    startActivity(intent);

                }

                //Log.e("TAG", mChatAdapter.getRef(position).toString());
            }
        });

    }
    public  void createSimpleDialog(final String key) {
        AlertDialog.Builder builder = new AlertDialog.Builder(servicio_solicitado.this);
        builder.setTitle("Opciones")
                .setMessage("Seleccione la actividad a realizar:")
                .setNegativeButton("ELIMINAR",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                FirebaseAuth auth = FirebaseAuth.getInstance();
                                FirebaseDatabase chat;
                                DatabaseReference mchat;
                                chat= FirebaseDatabase.getInstance();
                                mchat = chat.getReference().child("Buzon").child(encodeEmail(auth.getCurrentUser().getEmail())).child(key);
                                mchat.removeValue();
                                DatabaseReference message = FirebaseDatabase.getInstance().getReference().child("Messages").child(encodeEmail(auth.getCurrentUser().getEmail())).child(key);
                                message.removeValue();
                            }})
                       .setPositiveButton("CONCRETADO",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                FirebaseAuth auth = FirebaseAuth.getInstance();
                                FirebaseDatabase chat;
                                chat= FirebaseDatabase.getInstance();
                                final DatabaseReference mhistorial;
                                DatabaseReference mchat,mchat_;
                                mchat = chat.getReference().child("Buzon").child(encodeEmail(auth.getCurrentUser().getEmail())).child(key);

                                mhistorial = chat.getReference().child("Historial").child(encodeEmail(auth.getCurrentUser().getEmail())).child(key);
                                mchat.addValueEventListener(new ValueEventListener() {
                                    @Override
                                    public void onDataChange(DataSnapshot dataSnapshot) {
                                      buzon Buzon = dataSnapshot.getValue(buzon.class);
                                        if (Buzon != null){
                                            String key_servicio = Buzon.getKey_servicio();
                                            String key_usuario_contratado = Buzon.getKey_usuario_contratado();
                                            String key_padre = Buzon.getKey_padre();
                                            String key_usuario_contrato = Buzon.getKey_usuario_contrato();
                                            SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
                                            Date date = new Date();
                                            String timestamp = dateFormat.format(date);
                                            Historial historial = new Historial(key_servicio,key_usuario_contratado,key_padre,key_usuario_contrato,timestamp);
                                            mhistorial.setValue(historial);
                                        }
                                    }

                                    @Override
                                    public void onCancelled(DatabaseError databaseError) {

                                    }
                                });
                                mchat.removeValue();
                                }
                        })
                .setNeutralButton("SALIR",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.cancel();
                            }
                        });
         Dialog dialog = builder.create();
         dialog.show();
    }
   public final void createSimpleDialogg(final String key) {
        AlertDialog.Builder builder = new AlertDialog.Builder(servicio_solicitado.this);
        builder.setTitle("Opciones")
                .setMessage("Seleccione la actividad a realizar:")
                .setNegativeButton("ELIMINAR",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                FirebaseAuth auth = FirebaseAuth.getInstance();
                                FirebaseDatabase chat;
                                DatabaseReference mchat;
                                chat= FirebaseDatabase.getInstance();
                                mchat = chat.getReference().child("Buzon").child(encodeEmail(auth.getCurrentUser().getEmail())).child(key);
                                mchat.removeValue();
                                DatabaseReference message = chat.getReference().child("Messages").child(encodeEmail(auth.getCurrentUser().getEmail())).child(key);
                                message.removeValue();
                                }
                        })
                .setPositiveButton("SALIR",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.cancel();
                            }
                        });
        Dialog dialog = builder.create();
        dialog.show();
    }
    public String encodeEmail(String userEmail) {
        return userEmail.replace(".", ",");
    }


}
