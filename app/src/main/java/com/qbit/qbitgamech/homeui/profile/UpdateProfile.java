package com.qbit.qbitgamech.homeui.profile;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.github.drjacky.imagepicker.ImagePicker;
import com.github.drjacky.imagepicker.constant.ImageProvider;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.qbit.qbitgamech.R;
import com.qbit.qbitgamech.SessionManager;
import com.squareup.picasso.Picasso;

import org.jetbrains.annotations.NotNull;

import kotlin.Unit;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;

public class UpdateProfile extends AppCompatActivity {
    ImageView profilePic;
    FloatingActionButton editProfileButton;
    SessionManager sessionManager;
    EditText name;
    EditText email;
    Button update;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_profile);

        profilePic = findViewById(R.id.profilepic);
        editProfileButton = findViewById(R.id.editprofilepic);
        name=findViewById(R.id.name);
        email=findViewById(R.id.emailid);
        update=findViewById(R.id.updatebutton);
        sessionManager = new SessionManager(getApplicationContext());
        Picasso.get().load(sessionManager.getSessionDetails("key_session_photo_uri")).into(profilePic);
        ActivityResultLauncher<Intent> launcher=
                registerForActivityResult(new ActivityResultContracts.StartActivityForResult(),(ActivityResult result)->{
                    if(result.getResultCode()==RESULT_OK){
                        Uri uri=result.getData().getData();
                        profilePic.setImageURI(uri);
                        sessionManager.updateSessionDetails("key_session_photo_uri",uri.toString());
                        // Use the uri to load the image
                    }else if(result.getResultCode()==ImagePicker.RESULT_ERROR){
                        // Use ImagePicker.Companion.getError(result.getData()) to show an error
                        String error = ImagePicker.Companion.getError(result.getData()).toString();
                        Log.e("Image Error",error);
                        Toast.makeText(UpdateProfile.this, error, Toast.LENGTH_SHORT).show();
                    }
                });
        String[] mimeTypes = { "image/png","image/jpg","image/jpeg"};
        editProfileButton.setOnClickListener(view -> launcher.launch(
                ImagePicker.with(UpdateProfile.this)
                        .galleryOnly().crop().cropFreeStyle().galleryMimeTypes(mimeTypes).createIntent()));

        update.setOnClickListener(view -> {
            sessionManager.updateSessionDetails("key_session_name",name.getText().toString());
            sessionManager.updateSessionDetails("key_session_email",email.getText().toString());

        });

    }
}