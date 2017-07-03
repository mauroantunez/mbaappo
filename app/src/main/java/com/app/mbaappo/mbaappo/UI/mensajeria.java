package com.app.mbaappo.mbaappo.UI;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.MediaRecorder;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.res.ResourcesCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Gravity;

import android.view.View;

import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Date;

import com.app.mbaappo.mbaappo.FirebaseUI.FirebaseImageLoader;
import com.app.mbaappo.mbaappo.FirebaseUI.FirebaseListAdapter;
import com.app.mbaappo.mbaappo.Modelo.Chat;
import com.app.mbaappo.mbaappo.Modelo.FriendlyMessage;
import com.app.mbaappo.mbaappo.Modelo.Usuario;
import com.app.mbaappo.mbaappo.Modelo.buzon;
import com.app.mbaappo.mbaappo.R;
import com.bumptech.glide.Glide;


import com.google.android.gms.tasks.OnCompleteListener;

import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;



import java.util.HashMap;
import java.util.Map;



import jp.wasabeef.glide.transformations.CropCircleTransformation;

public class mensajeria extends AppCompatActivity {

    private static final String TAG ="2" ;
    private String messageId;
    private TextView mMessageField;
    private ImageButton mSendButton;
    private String chatName;
    private ListView mMessageList;
    private Toolbar mToolBar;
    private String currentUserEmail;

    private FirebaseDatabase mFirebaseDatabase;
    private DatabaseReference mMessageDatabaseReference;
    private DatabaseReference mUsersDatabaseReference;
    private FirebaseListAdapter<FriendlyMessage> mMessageListAdapter;
    private FirebaseAuth mFirebaseAuth;
    private FirebaseAuth mFirebaseAuthh;
    private ImageButton mphotoPickerButton;
    private static final int GALLERY_INTENT=2;
    private StorageReference mStorage;
    private ProgressDialog mProgress;

    private ImageButton mrecordVoiceButton;
    private TextView mRecordLable;

    private MediaRecorder mRecorder;
    private String mFileName = null;
    private FirebaseAuth.AuthStateListener mAuthListener;

    private static final String LOG_TAG = "Record_log";
    private ValueEventListener mValueEventListener;

    //Audio Runtime Permissions
    private boolean permissionToRecordAccepted = false;
    private boolean permissionToWriteAccepted = false;
    private String [] permissions = {"android.permission.RECORD_AUDIO", "android.permission.WRITE_EXTERNAL_STORAGE"};


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode){
            case 200:
                permissionToRecordAccepted  = grantResults[0] == PackageManager.PERMISSION_GRANTED;
                permissionToWriteAccepted  = grantResults[1] == PackageManager.PERMISSION_GRANTED;
                break;
        }
        if (!permissionToRecordAccepted ) mensajeria.super.finish();
        if (!permissionToWriteAccepted ) mensajeria.super.finish();

    }

    @Override
    protected void onCreate(final Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        mFirebaseAuthh = FirebaseAuth.getInstance();
        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if (user != null) {

                } else {
                    Intent loginIntent = new Intent(mensajeria.this, inicio.class);
                    loginIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(loginIntent);
                    Log.d(TAG, "onAuthStateChanged:signed_out");
                    //      Log.d(TAG, "onAuthStateChanged:signed_in:" + user.getUid());
                }
                // ...
            }
        };
        mFirebaseAuthh.addAuthStateListener(mAuthListener);
        setContentView(R.layout.activity_mensajeria);




            Intent intent = this.getIntent();

            messageId =intent.getStringExtra("idmessage");

        if(messageId ==null)

            {
                finish(); // replace this.. nav user back to home
                return;
            }

            //Check Permissions at runtime
            int requestCode = 200;
        if(Build.VERSION.SDK_INT >=Build.VERSION_CODES.M)

            {
                requestPermissions(permissions, requestCode);
            }


            initializeScreen();

            //mToolBar.setTitle(chatName);
            showMessages();

            addListeners();
            //openImageSelector();
            //openVoiceRecorder();

        }
    @Override
    protected void onStart() {
        super.onStart();
        mFirebaseAuthh.addAuthStateListener(mAuthListener);
    }


    public void addListeners(){
        mMessageField.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (charSequence.toString().trim().length() > 0) {
                    mSendButton.setEnabled(true);
                } else {
                    mSendButton.setEnabled(false);
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {
            }
        });
    }




    public void sendMessage(View view){
        //final DatabaseReference messageRef = mFirebaseDatabase.getReference(Constants.MESSAGE_LOCATION);
        String keys = mMessageDatabaseReference.getKey();
        final FirebaseDatabase buz = FirebaseDatabase.getInstance();
        DatabaseReference chatt = buz.getReference().child("Chat").child(encodeEmail(mFirebaseAuth.getCurrentUser().getEmail())).child(keys);
        final DatabaseReference buzonn = buz.getReference().child("Buzon").child(encodeEmail(mFirebaseAuth.getCurrentUser().getEmail())).child(keys);
        chatt.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Chat chacha = dataSnapshot.getValue(Chat.class);
                FirebaseAuth aut = FirebaseAuth.getInstance();
                buzon bu = new buzon(chacha.getKey_servicio(),chacha.getKey_usuario_contratado(),chacha.getKey_padre(),chacha.getKey_usuario_contrato());
                if (chacha.getKey_usuario_contrato().equals(encodeEmail(aut.getCurrentUser().getEmail())))
                {
                    buzonn.setValue(bu);
                    final  DatabaseReference bn = buz.getReference().child("Buzon").child(chacha.getKey_usuario_contratado()).child(chacha.getKey_padre());
                    bn.setValue(bu);
                }
                else{
                    buzonn.setValue(bu);
                    final  DatabaseReference bn = buz.getReference().child("Buzon").child(chacha.getKey_usuario_contrato()).child(chacha.getKey_padre());
                    bn.setValue(bu);

                }



            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        final DatabaseReference pushRef = mMessageDatabaseReference.push();
        final String pushKey = pushRef.getKey();
        Log.d(TAG, "Email sent."+pushKey);
        String messageString = mMessageField.getText().toString();

        SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm");
        Date date = new Date();
        String timestamp = dateFormat.format(date);

        FriendlyMessage message = new FriendlyMessage(encodeEmail(mFirebaseAuth.getCurrentUser().getEmail()), messageString, timestamp);
        pushRef.setValue(message)
                .addOnCompleteListener(this, new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        mMessageField.setText("");
                    }
                });
    }

    private void showMessages() {
        mMessageListAdapter = new FirebaseListAdapter<FriendlyMessage>(this, FriendlyMessage.class, R.layout.item_message, mMessageDatabaseReference) {
            @Override
            protected void populateView(final View view, final FriendlyMessage message, final int position) {
                LinearLayout messageLine = (LinearLayout) view.findViewById(R.id.messageLine);
                TextView messgaeText = (TextView) view.findViewById(R.id.messageTextView);
                TextView senderText = (TextView) view.findViewById(R.id.senderTextView);
                //TextView timeTextView = (TextView) view.findViewById(R.id.timeTextView);
                final ImageView leftImage = (ImageView) view.findViewById(R.id.leftMessagePic);
                final ImageView rightImage = (ImageView) view.findViewById(R.id.rightMessagePic);
                LinearLayout individMessageLayout = (LinearLayout)view.findViewById(R.id.individMessageLayout);


                messgaeText.setText(message.getMessage());
                senderText.setText(message.getTimestamp());

                String mSender = message.getSender();

                if(mSender.equals(currentUserEmail)){

                    messageLine.setGravity(Gravity.RIGHT);
                    leftImage.setVisibility(View.GONE);
                    rightImage.setVisibility(View.VISIBLE);

                    //profile image back to here
                    mUsersDatabaseReference.child(mSender).addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            Usuario userInfo = dataSnapshot.getValue(Usuario.class);
                                if(userInfo != null && userInfo.getProfilePicLocation() != null){
                                    StorageReference storageRef = FirebaseStorage.getInstance()
                                            .getReference().child(userInfo.getProfilePicLocation());
                                    try{
                                    Glide.with(view.getContext())
                                            .using(new FirebaseImageLoader())
                                            .load(storageRef)
                                            .bitmapTransform(new CropCircleTransformation(view.getContext()))
                                            .into(rightImage); }
                                    catch (Exception e){

                                    }
                                }
                            }



                        @Override
                        public void onCancelled(DatabaseError databaseError) {

                        }
                    });

                    individMessageLayout.setBackgroundResource(R.drawable.roundedmessagescolored);

                }else if(mSender.equals("System")){
                    messageLine.setGravity(Gravity.CENTER_HORIZONTAL);
                    leftImage.setVisibility(View.GONE);
                    rightImage.setVisibility(View.GONE);
                }else{

                    messageLine.setGravity(Gravity.LEFT);
                    leftImage.setVisibility(View.VISIBLE);
                    rightImage.setVisibility(View.GONE);
                    individMessageLayout.setBackgroundResource(R.drawable.roundedmessages);



                    //profile image back to here
                    mUsersDatabaseReference.child(mSender).addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            Usuario userInfo = dataSnapshot.getValue(Usuario.class);
                            if(userInfo != null && userInfo.getProfilePicLocation() != null){
                                    StorageReference storageRef = FirebaseStorage.getInstance()
                                            .getReference().child(userInfo.getProfilePicLocation());
                                    Glide.with(view.getContext())
                                            .using(new FirebaseImageLoader())
                                            .load(storageRef)
                                            .bitmapTransform(new CropCircleTransformation(view.getContext()))
                                            .into(leftImage);

                            }
                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {

                        }
                    });
                }

            }
        };
        mMessageList.setAdapter(mMessageListAdapter);
    }



    private void initializeScreen() {
        mMessageList = (ListView) findViewById(R.id.messageListView);
        mMessageField = (TextView)findViewById(R.id.messageToSend);
        mSendButton = (ImageButton)findViewById(R.id.sendButton);

        mFirebaseDatabase = FirebaseDatabase.getInstance();
        mFirebaseAuth = FirebaseAuth.getInstance();
        currentUserEmail = encodeEmail(mFirebaseAuth.getCurrentUser().getEmail());
        mUsersDatabaseReference = mFirebaseDatabase.getReference().child("Usuarios");
        mMessageDatabaseReference = mFirebaseDatabase.getReference().child("Messages"+ "/" + messageId);


    }



    //TODO: Used in multiple places, should probably move to its own class
    public String encodeEmail(String userEmail) {
        return userEmail.replace(".", ",");
    }

}