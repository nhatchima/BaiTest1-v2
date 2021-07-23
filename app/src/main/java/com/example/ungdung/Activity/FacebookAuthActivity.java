package com.example.ungdung.Activity;

import androidx.annotation.NonNull;


import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;


import com.example.ungdung.MainActivity;
import com.facebook.AccessToken;
import com.facebook.AccessTokenTracker;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;

import com.facebook.FacebookSdk;
import com.facebook.GraphRequest;
import com.facebook.Profile;
import com.facebook.appevents.AppEventsLogger;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FacebookAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import org.json.JSONException;

import java.util.Arrays;

import static com.example.ungdung.Activity.LoaiSpActivity.profileEmail;
import static com.example.ungdung.Activity.LoaiSpActivity.profileName;

public class FacebookAuthActivity extends MainActivity {

    CallbackManager callbackManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FacebookSdk.sdkInitialize(getApplicationContext());
        AppEventsLogger.activateApp(this);



        callbackManager = CallbackManager.Factory.create();
        mAuth = FirebaseAuth.getInstance();

        LoginManager.getInstance().logInWithReadPermissions(this,Arrays.asList("public_profile"));
        LoginManager.getInstance().registerCallback(callbackManager,
                new FacebookCallback<LoginResult>() {
                    @Override
                    public void onSuccess(LoginResult loginResult) {
                        handleFacebookAccessToken(loginResult.getAccessToken());

                    }

                    @Override
                    public void onCancel() {
                        // App code
                    }

                    @Override
                    public void onError(FacebookException exception) {
                        // App code
                    }
                });
     }
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {

        super.onActivityResult(requestCode, resultCode, data);
        callbackManager.onActivityResult(requestCode, resultCode, data);

    }
    @Override
    public void onStart() {
        super.onStart();
        AccessToken accessToken = AccessToken.getCurrentAccessToken();
        loaduserProfile(accessToken);

        AccessTokenTracker tracker =  new AccessTokenTracker() {
            @Override
            protected void onCurrentAccessTokenChanged(AccessToken oldAccessToken, AccessToken currentAccessToken) {
                if(currentAccessToken==null){
                    Toast.makeText(FacebookAuthActivity.this, "Vui lòng đăng nhập", Toast.LENGTH_SHORT).show();
                }else{
                    loaduserProfile(currentAccessToken);
                }
            }
        };
        FirebaseUser currentUser = mAuth.getCurrentUser();

        updateUI(currentUser);
    }
    private void loaduserProfile(AccessToken currentAccessToken) {
        Profile.getCurrentProfile();

        GraphRequest request = GraphRequest.newMeRequest(currentAccessToken, ((object, response) -> {
            if (object != null) {
                try {
                    String name = object.getString("last name");
                    String email = object.getString("name");
                    profileName.setText(name);
                    profileEmail.setText(email);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }));
    }
    private void handleFacebookAccessToken(AccessToken token) {

        AuthCredential credential = FacebookAuthProvider.getCredential(token.getToken());
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            FirebaseUser user = mAuth.getCurrentUser();
                            Toast.makeText(FacebookAuthActivity.this, "Log In Success.",
                                    Toast.LENGTH_SHORT).show();
                            updateUI(user);

                        } else {

                            Toast.makeText(FacebookAuthActivity.this, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();
                            updateUI(null);
                        }
                    }
                });
    }
    private void updateUI(FirebaseUser user) {
        Intent intent = new Intent(FacebookAuthActivity.this, LoaiSpActivity.class);
        startActivity(intent);
    }

    @Override
    protected void onPause() {
        super.onPause();
        FirebaseAuth.getInstance().signOut();
    }
}