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

    private String APP_ID = "";
    private String API_KEY = "";
    private String PROJECT_ID = "";
    private String DB_URL = "";
    private String STORAGE_BUCKET = "";

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

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    public void back(View view) {
        onBackPressed();
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
                    .setApplicationId(APP_ID) // Replace with your Firebase App ID
                    .setApiKey(API_KEY)       // Replace with your Firebase API Key
                    .setProjectId(PROJECT_ID) // Replace with your Firebase Project ID
                    .setDatabaseUrl(DB_URL)
                    .setStorageBucket(STORAGE_BUCKET)
                    .build();
            FirebaseApp.initializeApp(this, options);
            System.out.println("Firebase initialization success");
        } catch (Exception e) {
            System.out.println("Firebase initialization failed: " + e.getMessage());
        }
    }
}