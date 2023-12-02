package com.example.myapplication.takecar;

import static android.content.ContentValues.TAG;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.Vibrator;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.Firebase;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class LoginFormManager extends AppCompatActivity {
    private FirebaseAuth mAuth = FirebaseAuth.getInstance();

    private EditText etEmail;
    private EditText etPassword ;

    private TextView tvWrongLoginData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_form);
        mAuth = FirebaseAuth.getInstance();
        etEmail = (EditText) findViewById(R.id.etLoginFormEmail);
        etPassword = (EditText) findViewById(R.id.etLoginFormPassword);
        tvWrongLoginData = (TextView) findViewById(R.id.tvWrongLoginData);
    }

    @SuppressLint("SuspiciousIndentation")
    public void login(View view) {
        String email = getEmail();
        String password = getPassword();
        if(email.length() == 0 || password.length() == 0) {
            showLoginErrorMessage();
            Vibrator v = (Vibrator) getSystemService(getApplicationContext().VIBRATOR_SERVICE);
            v.vibrate(100);
        }
        else
        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            Log.d(TAG, "signInWithEmail:success");
                            FirebaseUser user = mAuth.getCurrentUser();
                            Intent intent = new Intent(getApplicationContext(), MainPageManager.class);
                            startActivity(intent);
                        } else {
                            showLoginErrorMessage();
                            Vibrator v = (Vibrator) getSystemService(getApplicationContext().VIBRATOR_SERVICE);
                            v.vibrate(100);
                            Log.w(TAG, "signInWithEmail:failure", task.getException());
                        }
                    }

                });

    }

    public void showLoginErrorMessage() {
        tvWrongLoginData.setVisibility(View.VISIBLE);
    }

    private String getEmail() {
        return etEmail.getText().toString().trim();
    }

    private String getPassword() {
        return etPassword.getText().toString().trim();
    }
}
