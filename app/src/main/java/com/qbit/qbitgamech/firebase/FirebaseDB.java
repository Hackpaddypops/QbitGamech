package com.qbit.qbitgamech.firebase;

import android.util.Log;

import com.google.firebase.firestore.FirebaseFirestore;
import com.qbit.qbitgamech.model.User;

import java.time.LocalDateTime;
import java.util.Map;

public class FirebaseDB {
    String collection = "User";
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    public void registerUser(User user){
        user.setCreatedAt(LocalDateTime.now());
        db.collection(collection).document(user.getEmail())
                .set(user).addOnSuccessListener(unused -> Log.d("Firestore","User saved successfully."))
                .addOnFailureListener(e -> Log.e("Firestore", e.getMessage()));
    }


    public void updateUser(Map<String,Object> objectMap, String email){
        db.collection(collection).document(email)
                .update(objectMap).addOnSuccessListener(unused -> Log.d("Firestore","User updated successfully."))
                .addOnFailureListener(e -> Log.e("Firestore", e.getMessage()));
    }




}
