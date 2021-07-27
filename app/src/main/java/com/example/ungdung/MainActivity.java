package com.example.ungdung;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.ungdung.Activity.FacebookAuthActivity;
import com.example.ungdung.Activity.GoogleSignInActivity;
import com.example.ungdung.Activity.LoaiSpActivity;
import com.facebook.login.widget.LoginButton;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import org.jetbrains.annotations.NotNull;


public class MainActivity extends AppCompatActivity {

    public static GoogleSignInClient mGoogleSignInClient;
    public static final int RC_SIGN_IN = 0;
    public SignInButton signInGoogle;
    LoginButton signInFacebook;
    public EditText edtUser;
    public EditText edtPass;
    Button btnLogin,btnRegister;
    TextView txtthongbao;
    ProgressDialog progressDialog;
    String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";


    public static FirebaseAuth mAuth;
    public static FirebaseUser mUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initView();

        if (mUser == null) {
            Toast.makeText(this, "Vui lòng đăng nhập", Toast.LENGTH_SHORT).show();
        }

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
        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = edtUser.getText().toString();
                String password = edtPass.getText().toString();
                PerforAuth(email,password);
            }
        });
    }
    public void PerforAuth(String email, String password) {

        if(!email.matches(emailPattern)){
            edtUser.setError("Enter correct email");
        } else if (password.isEmpty()|| password.length()<6){
            edtPass.setError("Enter correct password");
        }else{
            progressDialog.setMessage("Please wait....");
            progressDialog.setTitle("Registration");
            progressDialog.setCanceledOnTouchOutside(false);
            progressDialog.show();

            mAuth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull @NotNull Task<AuthResult> task) {
                    if(task.isSuccessful()){
                        progressDialog.dismiss();
                        sendUserToNextActivity();
                        Toast.makeText(MainActivity.this, "Register Successful", Toast.LENGTH_SHORT).show();
                    }else{
                        progressDialog.dismiss();
                        Toast.makeText(MainActivity.this, "Register Failed", Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }
    }
    private void sendUserToNextActivity() {
        Intent intent = new Intent(MainActivity.this, LoaiSpActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }
    private void initView() {
        signInGoogle = findViewById(R.id.btngg);
        signInGoogle.setSize(SignInButton.SIZE_STANDARD);
        signInFacebook = findViewById(R.id.btnfb);
        btnLogin = (Button) findViewById(R.id.btnlogin);
        btnRegister = findViewById(R.id.btnregister);
        edtUser = (EditText) findViewById(R.id.edtusername);
        edtPass = (EditText) findViewById(R.id.edtpassword);
        txtthongbao = (TextView) findViewById(R.id.txtmessage);
        progressDialog = new ProgressDialog(this);
        mAuth = FirebaseAuth.getInstance();
        mUser = mAuth.getCurrentUser();
    }
}