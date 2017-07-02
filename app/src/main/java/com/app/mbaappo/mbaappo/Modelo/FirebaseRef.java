package com.app.mbaappo.mbaappo.Modelo;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;

/**
 * Created by Antunez on 1/7/2017.
 */

public class FirebaseRef {
    final public static FirebaseAuth auth = FirebaseAuth.getInstance();
    final public static FirebaseStorage storageref = FirebaseStorage.getInstance();
    final public static ValueEventListener listener = null;
    public static String encodeEmail(String userEmail) {
        return userEmail.replace(".", ",");
    }
    final public static DatabaseReference sto = FirebaseDatabase.getInstance().getReference().child("Usuarios");

}
