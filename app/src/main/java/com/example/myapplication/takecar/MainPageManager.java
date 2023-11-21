package com.example.myapplication.takecar;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;

public class MainPageManager extends AppCompatActivity {

    private static final int CLOSE_APP_INTERVAL = 1000;
    private long backPressedTime = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_page);
    }

    @Override
    public void onBackPressed() {
        if (backPressedTime + CLOSE_APP_INTERVAL > System.currentTimeMillis()) {
            moveTaskToBack(true);
        }
        backPressedTime = System.currentTimeMillis();
    }


    public void logout(View view) {
        FirebaseAuth mAuth;
        mAuth = FirebaseAuth.getInstance();
        mAuth.signOut();
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

    public void openAddCarForm(View view) {
        Intent intent = new Intent(this, AddCarManager.class);
        startActivity(intent);
    }
}
