package com.example.myapplication.takecar;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.PopupWindow;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SwitchCompat;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DatabaseError;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.Collections;

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

    private ConstraintLayout pageBackgroundLayout;

    private String TAKE_TEXT = "take your car today.";
    private String RENT_TEXT = "rent your car today.";

    private TextView textViewMainPage;

    private PopupWindow popupWindow;

    private enum SortingType{
        RANDOMLY,
        LOWEST_PRICE,
        BIGGEST_PRICE
    }

    @SuppressLint({"MissingInflatedId", "ResourceType"})
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_page);
        pageBackgroundLayout = findViewById(R.id.mainPageBackground);

        switchCompat = findViewById(R.id.switchCompat);
        tvTake = findViewById(R.id.tvTake);
        tvRent = findViewById(R.id.tvRent);
        layoutRent = findViewById(R.id.layoutRent);
        layoutTake = findViewById(R.id.layoutTake);
        rvRent = findViewById(R.id.rvRent);
        textViewMainPage = findViewById(R.id.textViewMainPage);
        setRecyclerViewItemsDivider(rvRent);
        rvTake = findViewById(R.id.rvTake);
        setRecyclerViewItemsDivider(rvTake);
        setSwitchListener();
        setUserName();
        runTakeView(SortingType.RANDOMLY);
    }

    public void setBackgroundColor(String hexColor) {
        int intColor = Color.parseColor(hexColor);
        pageBackgroundLayout.setBackgroundColor(intColor);
    }



    public void runTakeView(SortingType sortingType) {
        DatabaseManager databaseManager = new DatabaseManager();
        databaseManager.getCars(false, new DatabaseManager.CarDataCallback() {
            @Override
            public void onCarsDataReceived(ArrayList<Car> carsList) {
                switch (sortingType) {
                    case RANDOMLY:
                        runRecyclerView(carsList, rvTake, true);
                        break;
                    case LOWEST_PRICE:
                        Collections.sort(carsList);
                        runRecyclerView(carsList, rvTake, true);
                        break;
                    case BIGGEST_PRICE:
                        Collections.sort(carsList);
                        Collections.reverse(carsList);
                        runRecyclerView(carsList, rvTake, true);
                }
            }

            @Override
            public void onCarsDataError(String errorMessage) {

            }
        });
    }

    public void sortCarsByLowestPrice(View view) {
        runTakeView(SortingType.LOWEST_PRICE);
        popupWindow.dismiss();
    }

    public void sortCarsByHighestPrice(View view) {
        runTakeView(SortingType.BIGGEST_PRICE);
        popupWindow.dismiss();
    }

    @SuppressLint("ResourceType")
    public void runPopupWindow(View view) {
        View popupView = LayoutInflater.from(this).inflate(R.layout.popup_window, null);
        popupWindow = new PopupWindow(popupView, ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT, true);
        popupWindow.showAtLocation(view , Gravity.CENTER, 0, 0);
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
            textViewMainPage.setText(RENT_TEXT);
            setBackgroundColor("#A63D40");
        } else {
            tvTake.setTextColor(colorWhite);
            tvRent.setTextColor(colorBlack);
            hideLayout(layoutRent);
            showLayout(layoutTake);
            textViewMainPage.setText(TAKE_TEXT);
            setBackgroundColor("#CC0B6FF4");
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
                    textViewMainPage.setText(RENT_TEXT);
                    setBackgroundColor("#A63D40");
                } else {
                    tvTake.setTextColor(colorWhite);
                    tvRent.setTextColor(colorBlack);
                    hideLayout(layoutRent);
                    showLayout(layoutTake);
                    runTakeView(SortingType.RANDOMLY);
                    textViewMainPage.setText(TAKE_TEXT);
                    setBackgroundColor("#CC0B6FF4");
                }
            }
        });
    }
}
