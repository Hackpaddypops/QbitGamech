package com.qbit.qbitgamech;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.IntentSenderRequest;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.auth.api.identity.BeginSignInRequest;
import com.google.android.gms.auth.api.identity.Identity;
import com.google.android.gms.auth.api.identity.SignInClient;
import com.google.android.gms.auth.api.identity.SignInCredential;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.common.api.CommonStatusCodes;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;
import com.qbit.qbitgamech.homeui.home.Home;

public class Login extends AppCompatActivity {

    SignInButton googleSignInButton;
    SessionManager sessionManager;

    private SignInClient oneTapClient;
    private BeginSignInRequest signInRequest;
    private BeginSignInRequest signUpRequest;
    ActivityResultLauncher<IntentSenderRequest> activityResultLauncher;
    FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        oneTapClient = Identity.getSignInClient(this);
        signInRequest = BeginSignInRequest.builder()
                .setGoogleIdTokenRequestOptions(BeginSignInRequest.GoogleIdTokenRequestOptions.builder()
                        .setSupported(true)
                        // Your server's client ID, not your Android client ID.
                        .setServerClientId(getString(R.string.default_web_client_id))
                        // Only show accounts previously used to sign in.
                        .setFilterByAuthorizedAccounts(true)
                        .build())
                // Automatically sign in when exactly one credential is retrieved.
                .setAutoSelectEnabled(true)
                .build();
        signUpRequest = BeginSignInRequest.builder()
                .setGoogleIdTokenRequestOptions(BeginSignInRequest.GoogleIdTokenRequestOptions.builder()
                        .setSupported(true)
                        // Your server's client ID, not your Android client ID.
                        .setServerClientId(getString(R.string.default_web_client_id))
                        // Only show accounts previously used to sign in.
                        .setFilterByAuthorizedAccounts(false)
                        .build())
                .build();
        firebaseAuth = FirebaseAuth.getInstance();
        sessionManager = new SessionManager(getApplicationContext());
        googleSignInButton=findViewById(R.id.sign_in_button);
        activityResultLauncher=registerForActivityResult(new ActivityResultContracts.StartIntentSenderForResult(), result -> {
             try {
                    SignInCredential googleCredential = oneTapClient.getSignInCredentialFromIntent(result.getData());
                 if (googleCredential.getGoogleIdToken() != null) {
                     AuthCredential credential= GoogleAuthProvider.getCredential(googleCredential.getGoogleIdToken(),null);
                     firebaseAuth.signInWithCredential(credential).addOnSuccessListener(authResult -> {

                         FirebaseUser firebaseUser=firebaseAuth.getCurrentUser();
                         assert firebaseUser != null;
                         sessionManager.createSession(firebaseUser);
                         navigateToHome();


                     }).addOnFailureListener(e -> Log.e("SignIn", e.toString()));
                 }


                } catch (ApiException e) {
                 switch (e.getStatusCode()) {
                     case CommonStatusCodes.CANCELED:
                         Toast.makeText(Login.this, "Please click on sign in button whenever you are ready.", Toast.LENGTH_SHORT).show();
                         Log.d("SignIn", "One-tap dialog was closed.");
                         break;
                     case CommonStatusCodes.NETWORK_ERROR:
                         Toast.makeText(Login.this, "We encountered a network error.", Toast.LENGTH_SHORT).show();
                         Log.d("SignIn", "One-tap encountered a network error.");
                         // Try again or just ignore.
                         break;
                     default:
                         Toast.makeText(Login.this, "Something went wrong. Please try again later.", Toast.LENGTH_SHORT).show();
                         Log.e("Login", String.valueOf(e));
                         break;
                 }

                }
        });
       googleSignInButton.setOnClickListener(view -> signIn());
    }

    private void signIn() {
        oneTapClient.beginSignIn(signInRequest)
                .addOnSuccessListener(this, result -> {
                    IntentSenderRequest intentSenderRequest = new IntentSenderRequest.Builder(result.getPendingIntent()).build();
                    activityResultLauncher.launch(intentSenderRequest);
                })
                .addOnFailureListener(this, e -> {
                    // No saved credentials found. Launch the One Tap sign-up flow, or
                    Log.e("SignUp",e.getMessage());
                    oneTapClient.beginSignIn(signUpRequest).addOnSuccessListener(this, beginSignInResult -> {
                        IntentSenderRequest intentSenderRequest = new IntentSenderRequest.Builder(beginSignInResult.getPendingIntent()).build();
                        activityResultLauncher.launch(intentSenderRequest);
                    }).addOnFailureListener(this, e1 -> Toast.makeText(Login.this, "Something went wrong. Please try again later.", Toast.LENGTH_SHORT).show());
                    // do nothing and continue presenting the signed-out UI.
                    Log.d("SignIn", e.getLocalizedMessage());
                });



    }



    private void navigateToHome() {
        finish();
        Intent intent = new Intent(Login.this, Home.class);
        startActivity(intent);
    }
}