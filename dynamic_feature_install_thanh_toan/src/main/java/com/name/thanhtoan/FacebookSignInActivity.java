package com.name.thanhtoan;

import static com.name.ungdung.Activity.LoaiSpActivity.profileEmail;
import static com.name.ungdung.Activity.LoaiSpActivity.profileName;
import static com.name.ungdung.MainActivity.mAuth;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.name.ungdung.Activity.LoaiSpActivity;
import com.facebook.AccessToken;
import com.facebook.AccessTokenTracker;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
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
import org.json.JSONObject;

import java.util.Arrays;

public class FacebookSignInActivity extends AppCompatActivity {

    CallbackManager callbackManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        FacebookSdk.sdkInitialize(getApplicationContext());
        AppEventsLogger.activateApp(this);
        callbackManager = CallbackManager.Factory.create();
        mAuth = FirebaseAuth.getInstance();

        LoginManager.getInstance().logInWithReadPermissions(this, Arrays.asList("email","public_profile"));
        LoginManager.getInstance().registerCallback(callbackManager,
                new FacebookCallback<LoginResult>() {
                    @Override
                    public void onSuccess(LoginResult loginResult) {
                        GraphRequest request = GraphRequest.newMeRequest(
                                loginResult.getAccessToken(),new GraphRequest.GraphJSONObjectCallback() {

                                    @Override
                                    public void onCompleted(JSONObject object,
                                                            GraphResponse response)
                                    {

                                        if (object != null) {
                                            try {
                                                String name = object.getString("name");
                                                String email = object.getString("email");
                                                String fbUserID = object.getString("id");
                                                profileName.setText(name);
                                                profileEmail.setText(fbUserID);
                                                // do action after Facebook login success
                                                // or call your API
                                            }
                                            catch (JSONException | NullPointerException e) {
                                                e.printStackTrace();
                                            }
                                        }
                                    }
                                });

                        handleFacebookAccessToken(loginResult.getAccessToken());

                    }

                    @Override
                    public void onCancel() {
                        Log.v("LoginScreen", "---onCancel");
                    }

                    @Override
                    public void onError(FacebookException exception) {
                        Log.v("LoginScreen", "----onError: "
                                + exception.getMessage());
                    }
                });
    }
//    @Override
//    protected void attachBaseContext(Context base) {
//        super.attachBaseContext(base);
//        SplitCompat.install(this);
//    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        callbackManager.onActivityResult(requestCode, resultCode, data);
        super.onActivityResult(requestCode, resultCode, data);

    }
    @Override
    public void onStart() {
        super.onStart();
        AccessToken accessToken = AccessToken.getCurrentAccessToken();

        AccessTokenTracker tracker =  new AccessTokenTracker() {
            @Override
            protected void onCurrentAccessTokenChanged(AccessToken oldAccessToken, AccessToken currentAccessToken) {
                if(currentAccessToken==null){
                    Toast.makeText(FacebookSignInActivity.this, "Vui lòng đăng nhập", Toast.LENGTH_SHORT).show();
                }else{

                }
            }
        };
        FirebaseUser currentUser = mAuth.getCurrentUser();
        updateUI(currentUser);
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
                            Toast.makeText(FacebookSignInActivity.this, "Log In Success.",
                                    Toast.LENGTH_SHORT).show();
                            updateUI(user);

                        } else {

                            Toast.makeText(FacebookSignInActivity.this, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();
                            updateUI(null);
                        }
                    }
                });
    }
    private void updateUI(FirebaseUser user) {
        Intent intent = new Intent(FacebookSignInActivity.this, LoaiSpActivity.class);
        startActivity(intent);
    }

}