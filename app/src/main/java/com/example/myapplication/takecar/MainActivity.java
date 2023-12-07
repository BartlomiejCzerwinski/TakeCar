package com.example.myapplication.takecar;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Vibrator;
import android.view.View;

import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        configFirebase();
        FirebaseAuth mAuth;
        mAuth = FirebaseAuth.getInstance();
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if(currentUser != null) {
            Intent intent = new Intent(this, MainPageManager.class);
            startActivity(intent);
        }
        setContentView(R.layout.activity_main);
    }

    public void openRegistrationForm(View view) {
        Intent intent = new Intent(this, RegistrationFormManager.class);
        startActivity(intent);
    }

    public void openLoginForm(View view) {
        Intent intent = new Intent(this, LoginFormManager.class);
        startActivity(intent);
    }

    public void configFirebase() {
        try {
            FirebaseOptions options = new FirebaseOptions.Builder()
                    .setApplicationId("1:757032153397:android:5719cf22ba57d8089d5c7c") // Replace with your Firebase App ID
                    .setApiKey("AIzaSyAlaFGL9XWiMgxfg2Xv5QZ9emllKVgaNQQ")       // Replace with your Firebase API Key
                    .setProjectId("takecar-a8abc") // Replace with your Firebase Project ID
                    .setDatabaseUrl("https://takecar-a8abc-default-rtdb.europe-west1.firebasedatabase.app/")
                    .setStorageBucket("takecar-a8abc.appspot.com")
                    .build();

            FirebaseApp.initializeApp(this, options);
            System.out.println("Firebase initialization success");
        } catch (Exception e) {
            System.out.println("Firebase initialization failed: " + e.getMessage());
        }
    }
}