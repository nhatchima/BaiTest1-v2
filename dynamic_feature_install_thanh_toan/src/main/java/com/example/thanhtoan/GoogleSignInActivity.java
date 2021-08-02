package com.example.thanhtoan;

import static com.example.ungdung.Activity.LoaiSpActivity.RC_SIGN_IN;
import static com.example.ungdung.Activity.LoaiSpActivity.mGoogleSignInClient;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.ungdung.Activity.LoaiSpActivity;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

public class GoogleSignInActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //Đăng Nhập Google
        FirebaseAuth.getInstance().signOut();
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();
        mGoogleSignInClient = com.google.android.gms.auth.api.signin.GoogleSignIn.getClient(this, gso);
        signIn();

    }
//
//    @Override
//    protected void attachBaseContext(Context base) {
//        super.attachBaseContext(base);
//        SplitCompat.install(this);
//    }
    private void signIn() {
        Intent signInIntent = mGoogleSignInClient.getSignInIntent();
        startActivityForResult(signInIntent, RC_SIGN_IN);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // Result returned from launching the Intent from GoogleSignInClient.getSignInIntent(...);
        if (requestCode == RC_SIGN_IN) {

            Task<GoogleSignInAccount> task = com.google.android.gms.auth.api.signin.GoogleSignIn.getSignedInAccountFromIntent(data);
            handleSignInResult(task);
        }
    }
    private void handleSignInResult(Task<GoogleSignInAccount> completedTask) {
        try {
            GoogleSignInAccount account = completedTask.getResult(ApiException.class);
            Toast.makeText(this, "Đăng Nhập Thành Công", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(this, LoaiSpActivity.class);
            startActivity(intent);
        } catch (ApiException e) {
            Toast.makeText(this, "Đăng Nhập Thất Bại", Toast.LENGTH_SHORT).show();
        }
    }
}