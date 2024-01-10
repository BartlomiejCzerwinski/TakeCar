package com.example.myapplication.takecar;

import static java.lang.Math.round;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.squareup.picasso.Picasso;

import java.text.DecimalFormat;
import java.util.ArrayList;

public class EditCarPageManager extends AppCompatActivity {

    private Car car;

    private TextView tvYear;
    private TextView tvTitle;
    private EditText etPlaces;
    private EditText etDoors;
    private TextView tvGearbox;
    private TextView tvAirConditioner;
    private EditText etPower;
    private EditText etHourlyPrice;
    private EditText etDailyPrice;
    private ImageView ivCar;

    private boolean isLoadedIncomesPage = false;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_car_manager_page);
        car = getCarFromIntent();
        setObjects();
        initCarPage();
    }

    public void recreatePage() {
        setContentView(R.layout.activity_car_manager_page);
        setObjects();
        initCarPage();
        isLoadedIncomesPage = false;
    }

    public void loadIncomesPage(View view) {
        setContentView(R.layout.acivity_car_incomes);
        loadCarStatistics();
        isLoadedIncomesPage = true;
    }

    public void onBackArrowClick(View view) {
        onBackPressed();
    }
    @Override
    public void onBackPressed() {
        if (isLoadedIncomesPage)
            recreatePage();
        else
            super.onBackPressed();
    }

    public void loadCarStatistics() {
        @SuppressLint({"MissingInflatedId", "LocalSuppress"}) TextView tvIncome = findViewById(R.id.tvTotalIncome);
        @SuppressLint({"MissingInflatedId", "LocalSuppress"}) TextView tvNumberOfRentals = findViewById(R.id.tvNumberOfRentals);
        @SuppressLint({"MissingInflatedId", "LocalSuppress"}) TextView tvAvgIncome = findViewById(R.id.tvAvgIncome);

        DatabaseManager databaseManager = new DatabaseManager();
        databaseManager.getRentalsOfCar(car.getID(), new DatabaseManager.RentalsCallback() {
            @Override
            public void onRentalsReceived(ArrayList<Rental> rentals) {
                tvIncome.setText(getTotalIncomeFromCar(rentals) + "$");
                tvNumberOfRentals.setText(Integer.toString(getNumberOfRentals(rentals)));
                tvAvgIncome.setText(getAvgIncomeFromCar(rentals) + "$/rent");
            }
        });
    }

    public int getNumberOfRentals(ArrayList<Rental> rentals) {
        return rentals.size();
    }

    public int getTotalIncomeFromCar(ArrayList<Rental> rentals) {
        int result = 0;
        for (Rental rental : rentals) {
            result += rental.getTotalPrice();
        }
        return result;
    }

    public float getAvgIncomeFromCar(ArrayList<Rental> rentals) {
        int numberOfRentals = getNumberOfRentals(rentals);
        int totalIncome = getTotalIncomeFromCar(rentals);
        float avgIncome =  (float)totalIncome / (float)numberOfRentals;
        DecimalFormat decimalFormat = new DecimalFormat("#.##");
        String roundedAvgIncome = decimalFormat.format(avgIncome);
        return Float.parseFloat(roundedAvgIncome);
    }

    public Car getCarFromIntent() {
        Car car = null;
        Intent intent = getIntent();
        if (intent.hasExtra("car")) {
            car = (Car) intent.getSerializableExtra("car");
        }
        return car;
    }

    @SuppressLint("WrongViewCast")
    public void setObjects() {
        tvYear = findViewById(R.id.tvYearCarEditPage);
        tvTitle = findViewById(R.id.tvTitleCarEditPage);
        etPlaces = findViewById(R.id.etPlacesCarEditPage);
        etDoors = findViewById(R.id.etDoorsCarEditPage);
        tvGearbox = findViewById(R.id.tvGearboxCarEditPage);
        tvAirConditioner = findViewById(R.id.tvAcCarEditPage);
        etPower = findViewById(R.id.etPowerCarEditPage);
        etHourlyPrice = findViewById(R.id.etCarEditPageHourlyPrice);
        etDailyPrice = findViewById(R.id.etCarEditPageDailyPrice);
        ivCar = findViewById(R.id.iVCarEditPage);
    }

    public void initCarPage() {
        System.out.println("tvYear:" + tvYear.toString());
        tvYear.setText(Integer.toString(car.getYear()));
        tvTitle.setText(car.getProducer() + " " + car.getModel());
        etPlaces.setText(Integer.toString(car.getPlaces()));
        etDoors.setText(Integer.toString(car.getDoors()));
        tvGearbox.setText(car.getGearbox());
        tvAirConditioner.setText(car.isAirConditioner());
        etPower.setText(Integer.toString(car.getPower()));
        etHourlyPrice.setText(car.getHourlyPrice() + "$");
        etDailyPrice.setText(car.getDailyPrice() + "$");
        Picasso.get().load(car.getPhotosUris().get(0)).fit().into(ivCar);
    }
}
