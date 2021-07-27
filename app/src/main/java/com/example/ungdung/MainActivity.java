package com.example.ungdung;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.example.ungdung.Activity.FacebookAuthActivity;
import com.example.ungdung.Activity.GoogleSignInActivity;
import com.example.ungdung.Activity.LoaiSpActivity;
import com.example.ungdung.ViewModel.AuthViewModel;
import com.facebook.login.widget.LoginButton;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.common.SignInButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;


public class MainActivity extends AppCompatActivity {

    public static GoogleSignInClient mGoogleSignInClient;
    public static final int RC_SIGN_IN = 0;
    public SignInButton signInGoogle;
    LoginButton signInFacebook;
    EditText edtEmail, edtPassword;
    Button btnLogin,btnRegister;
    AuthViewModel authViewModel;
    String emailPattern ="[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";

    public static FirebaseAuth mAuth;
    public static FirebaseUser mUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initView();


        authViewModel = new ViewModelProvider(this).get(AuthViewModel.class);
        authViewModel.getUserMutableLiveData().observe(this, new Observer<FirebaseUser>() {
            @Override
            public void onChanged(FirebaseUser firebaseUser) {
                if(firebaseUser !=null){
                    Toast.makeText(MainActivity.this, "User create success", Toast.LENGTH_SHORT).show();
                    btnLogin.setEnabled(true);
                }else {
                    btnLogin.setEnabled(false);

                }
            }
        });
        if (mUser == null) {
            Toast.makeText(this, "Vui lòng đăng nhập", Toast.LENGTH_SHORT).show();
        }
        authViewModel = new ViewModelProvider(this).get(AuthViewModel.class);
        authViewModel.getLoginLiveData().observe(this, new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean aBoolean) {
                if(aBoolean){
                    Intent intent = new Intent(MainActivity.this, LoaiSpActivity.class);
                    startActivity(intent);
                }
            }
        });
        signInGoogle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, GoogleSignInActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                startActivity(intent);
            }
        });
        signInFacebook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, FacebookAuthActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                startActivity(intent);

            }
        });
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = edtEmail.getText().toString();
                String password = edtPassword.getText().toString();

                if (email.matches(emailPattern) && email.length() > 0 && password.length() > 0) {
                    authViewModel.login(email, password);
                } else {
                    Toast.makeText(getApplicationContext(), "Email Address and Password Must Be Entered", Toast.LENGTH_SHORT).show();
                }
            }
        });
        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = edtEmail.getText().toString();
                String password = edtPassword.getText().toString();
                if ( email.length() > 0 && password.length() > 0) {
                    authViewModel.register(email, password);
                } else {
                    Toast.makeText(getApplicationContext(), "Email Address and Password Must Be Entered", Toast.LENGTH_SHORT).show();
                }
            }
        });
      }
    private void initView() {
        signInGoogle = findViewById(R.id.btngg);
        signInGoogle.setSize(SignInButton.SIZE_STANDARD);
        signInFacebook = findViewById(R.id.btnfb);
        btnLogin = (Button) findViewById(R.id.btnlogin);
        btnRegister = findViewById(R.id.btnregister);
        edtEmail = (EditText) findViewById(R.id.edtusername);
        edtPassword = (EditText) findViewById(R.id.edtpassword);
    }
}