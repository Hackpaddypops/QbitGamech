package com.qbit.qbitgamech;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.util.Log;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;
import com.qbit.qbitgamech.firebase.FirebaseDB;
import com.qbit.qbitgamech.model.User;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class SessionManager {
    GoogleSignInOptions googleSignInOptions;
    GoogleSignInClient googleSignInClient;



    Context context;
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;

    private final String PREF_FILE_NAME="account-data";
    private final int PRIVATE_MODE=0;

    private final String KEY_NAME = "key_session_name";
    private final String KEY_EMAIL = "key_session_email";
    private final String KEY_PHOTO_URI = "key_session_photo_uri";
    private final String KEY_LOGGED_IN = "key_logged_in";
    private final String KEY_QBITS = "key_qbits";

    public SessionManager(Context context) {
        this.context = context;
        sharedPreferences=context.getSharedPreferences(PREF_FILE_NAME,PRIVATE_MODE);
        editor = sharedPreferences.edit();
    }

    public void createSession(FirebaseUser firebaseUser) {
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        FirebaseDB firebaseDB = new FirebaseDB();
        db.collection("User").document(Objects.requireNonNull(firebaseUser.getEmail()))
                .get().addOnSuccessListener(documentSnapshot -> {
            Map<String,Object> map = new HashMap<>();
            map.put("modifiedAt",LocalDateTime.now());
                    if(documentSnapshot.exists()){
                        firebaseDB.updateUser(map,documentSnapshot.get("email",String.class));
                        editor.putString(KEY_NAME,documentSnapshot.get("name",String.class));
                        editor.putString(KEY_EMAIL,documentSnapshot.get("email",String.class));
                        editor.putInt(KEY_QBITS,documentSnapshot.get("qbits",Integer.class));
                        if (documentSnapshot.get("photoUrl",String.class)!=null){
                            editor.putString(KEY_PHOTO_URI, documentSnapshot.get("photoUrl",String.class));
                        }
                        editor.putBoolean(KEY_LOGGED_IN,true);
                        editor.commit();
                    } else {
                        User user = new User();
                        user.setEmail(firebaseUser.getEmail());
                        user.setName(firebaseUser.getDisplayName());
                        if (firebaseUser.getPhotoUrl()!=null){
                            editor.putString(KEY_PHOTO_URI, firebaseUser.getPhotoUrl().toString());
                            user.setPhotoUrl(firebaseUser.getPhotoUrl().toString());
                        }
                        editor.putString(KEY_NAME,firebaseUser.getDisplayName());
                        editor.putString(KEY_EMAIL,firebaseUser.getEmail());

                        editor.putBoolean(KEY_LOGGED_IN,true);
                        firebaseDB.registerUser(user);
                        editor.commit();
                    }


        }).addOnFailureListener(e -> {
                    Log.e("Firestore",e.getMessage());
        });


    }

    public boolean checkSession(){
        return sharedPreferences.contains(KEY_LOGGED_IN);
    }

    public void logout(){
        editor.clear();
        editor.commit();
        context.startActivity(new Intent(context,Login.class));
    }

    public String getSessionDetails(String key){
        return sharedPreferences.getString(key,null);
    }
    public int getQbits(){
        return sharedPreferences.getInt(KEY_QBITS,0);
    }

    public void updateSessionDetails(String key, String value){
        editor.putString(key,value);
        editor.apply();
    }

    public void updateQbits(int value){
        editor.putInt(KEY_QBITS,value);
        editor.apply();
        FirebaseDB firebaseDB = new FirebaseDB();
        Map<String,Object> map = new HashMap<>();
        map.put("qbits",value);
        firebaseDB.updateUser(map,getSessionDetails(KEY_EMAIL));
    }

    public GoogleSignInClient googleSignIn(Activity activity){
        googleSignInOptions = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(activity.getString(R.string.default_web_client_id)).requestEmail().build();
        googleSignInClient = GoogleSignIn.getClient(activity,googleSignInOptions);
        return googleSignInClient;
    }


}
