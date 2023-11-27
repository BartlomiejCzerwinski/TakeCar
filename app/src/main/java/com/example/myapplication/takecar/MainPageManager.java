package com.example.myapplication.takecar;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.AbsListView;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SwitchCompat;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;

public class MainPageManager extends AppCompatActivity {

    private static final int CLOSE_APP_INTERVAL = 1000;
    private long backPressedTime = 0;

    private SwitchCompat switchCompat;
    private TextView tvTake;
    private TextView tvRent;

    private ConstraintLayout layoutRent;
    private ConstraintLayout layoutTake;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_page);
        switchCompat = findViewById(R.id.switchCompat);
        tvTake = findViewById(R.id.tvTake);
        tvRent = findViewById(R.id.tvRent);
        layoutRent = findViewById(R.id.layoutRent);
        layoutTake = findViewById(R.id.layoutTake);
        setSwitchListener();
        runRecyclerView();
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

    public void showLayout(ConstraintLayout layout) {
        layout.setVisibility(View.VISIBLE);
    }

    public void hideLayout(ConstraintLayout layout) {
        layout.setVisibility(View.INVISIBLE);
    }
    
    public void runRecyclerView() {
        RecyclerView recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));

        MyAdapter myAdapter = new MyAdapter();
        recyclerView.setAdapter(myAdapter);
    }

    public void setSwitchListener() {
        int colorWhite = Color.parseColor("#E2E2E2");
        int colorBlack = Color.parseColor("#2E2E2E");
        boolean isSwitchChecked = switchCompat.isChecked();

        if (isSwitchChecked) {
            tvTake.setTextColor(colorBlack);
            tvRent.setTextColor(colorWhite);
            hideLayout(layoutTake);
            showLayout(layoutRent);
        } else {
            tvTake.setTextColor(colorWhite);
            tvRent.setTextColor(colorBlack);
            hideLayout(layoutRent);
            showLayout(layoutTake);
        }

        switchCompat.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    tvTake.setTextColor(colorBlack);
                    tvRent.setTextColor(colorWhite);
                    hideLayout(layoutTake);
                    showLayout(layoutRent);
                } else {
                    tvTake.setTextColor(colorWhite);
                    tvRent.setTextColor(colorBlack);
                    hideLayout(layoutRent);
                    showLayout(layoutTake);
                }
            }
        });
    }
}
