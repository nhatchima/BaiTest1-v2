package com.example.ungdung.ViewModel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import com.example.ungdung.Model.AuthAppRepository;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import org.jetbrains.annotations.NotNull;

public class AuthViewModel extends AndroidViewModel {

    private AuthAppRepository authAppRepository;
    private FirebaseAuth firebaseAuth;
    private MutableLiveData<FirebaseUser> userMutableLiveData;
    private MutableLiveData<Boolean> loggedOutLiveData;
    private MutableLiveData<Boolean> loginLiveData;

    public AuthViewModel(@NonNull @NotNull Application application) {
        super(application);

        authAppRepository = new AuthAppRepository(application);
        userMutableLiveData = new MutableLiveData<>();
        userMutableLiveData = new MutableLiveData<>();
        firebaseAuth = FirebaseAuth.getInstance();
        loggedOutLiveData = authAppRepository.getLoggedOutLiveData();
        loginLiveData = authAppRepository.getLoginLiveData();

    }

    public void login(String email, String password) {
        authAppRepository.login(email, password);
    }

    public void register(String email, String password) {
        authAppRepository.register(email, password);
    }
    public void logOut() {
        authAppRepository.logOut();
    }
    public MutableLiveData<Boolean> getLoggedOutLiveData() {
        return loggedOutLiveData;
    }
    public MutableLiveData<FirebaseUser> getUserMutableLiveData() {
        return userMutableLiveData;
    }

    public MutableLiveData<Boolean> getLoginLiveData() {
        return loginLiveData;
    }
}