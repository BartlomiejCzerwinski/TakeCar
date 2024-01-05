package com.example.myapplication.takecar;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SwitchCompat;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DatabaseError;

import java.util.ArrayList;

public class MainPageManager extends AppCompatActivity {

    private static final int CLOSE_APP_INTERVAL = 1000;
    private long backPressedTime = 0;

    private SwitchCompat switchCompat;
    private TextView tvTake;
    private TextView tvRent;

    private ConstraintLayout layoutRent;
    private ConstraintLayout layoutTake;

    private RecyclerView rvRent;
    private RecyclerView rvTake;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_page);
        switchCompat = findViewById(R.id.switchCompat);
        tvTake = findViewById(R.id.tvTake);
        tvRent = findViewById(R.id.tvRent);
        layoutRent = findViewById(R.id.layoutRent);
        layoutTake = findViewById(R.id.layoutTake);
        rvRent = findViewById(R.id.rvRent);
        setRecyclerViewItemsDivider(rvRent);
        rvTake = findViewById(R.id.rvTake);
        setRecyclerViewItemsDivider(rvTake);
        setSwitchListener();
        setUserName();
        runTakeView();
    }

    public void runTakeView() {
        DatabaseManager databaseManager = new DatabaseManager();
        databaseManager.getCars(false, new DatabaseManager.CarDataCallback() {
            @Override
            public void onCarsDataReceived(ArrayList<Car> carsList) {
                runRecyclerView(carsList, rvTake, true);
            }

            @Override
            public void onCarsDataError(String errorMessage) {

            }
        });
    }

    public void runRentView() {
        DatabaseManager databaseManager = new DatabaseManager();
        databaseManager.getCars(true, new DatabaseManager.CarDataCallback() {
            @Override
            public void onCarsDataReceived(ArrayList<Car> carsList) {
                runRecyclerView(carsList, rvRent, false);
            }

            @Override
            public void onCarsDataError(String errorMessage) {

            }
        });
    }

    public void setUserName() {
        TextView tvHelloName = findViewById(R.id.tvHelloName);
        DatabaseManager databaseManager = new DatabaseManager();
        databaseManager.getUserName(new DatabaseManager.UserNameCallback() {
            @Override
            public void onNameReceived(String name) {
                tvHelloName.setText("Hello " + name + ",");
            }

            @Override
            public void onCancelledCallback(DatabaseError error) {
                tvHelloName.setText("Hello,");
            }
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        if (backPressedTime + CLOSE_APP_INTERVAL > System.currentTimeMillis()) {
            moveTaskToBack(true);
        }
        backPressedTime = System.currentTimeMillis();
    }

    public void openAddCarForm(View view) {
        Intent intent = new Intent(this, AddCarManager.class);
        startActivity(intent);
    }

    public void openSettingsPage(View view) {
        Intent intent = new Intent(this, SettingsPageManager.class);
        startActivity(intent);
    }

    public void showLayout(ConstraintLayout layout) {
        layout.setVisibility(View.VISIBLE);
    }

    public void hideLayout(ConstraintLayout layout) {
        layout.setVisibility(View.INVISIBLE);
    }

    public void setRecyclerViewItemsDivider(RecyclerView recyclerView) {
        DividerItemDecoration itemDecorator = new DividerItemDecoration(recyclerView.getContext(), DividerItemDecoration.VERTICAL);
        itemDecorator.setDrawable(getDrawable(R.drawable.divider));
        recyclerView.addItemDecoration(itemDecorator);
    }
    
    public void runRecyclerView(ArrayList<Car> cars, RecyclerView recyclerView, boolean isRunningTakeList) {
        recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        MyAdapter myAdapter = new MyAdapter(cars, isRunningTakeList, this.getApplicationContext());
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
                    runRentView();
                } else {
                    tvTake.setTextColor(colorWhite);
                    tvRent.setTextColor(colorBlack);
                    hideLayout(layoutRent);
                    showLayout(layoutTake);
                    runTakeView();
                }
            }
        });
    }
}
