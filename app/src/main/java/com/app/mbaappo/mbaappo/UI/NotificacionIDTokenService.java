package com.app.mbaappo.mbaappo.UI;

import android.util.Log;

import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.FirebaseInstanceIdService;

/**
 * Created by Antunez on 29/6/2017.
 */

public class NotificacionIDTokenService extends FirebaseInstanceIdService {
    public static final String TAG = "aiuda";
    @Override

    public void onTokenRefresh() {
        //super.onTokenRefresh();
        String token = FirebaseInstanceId.getInstance().getToken();
       // enviarTokenRegistro(token);
    }
    private void enviarTokenRegistro(String token){
        Log.d(TAG, token);
    }
}
