package com.example.myapplication.takecar;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class CarPageManager extends AppCompatActivity {

    private Car car;

    private TextView tvYear;
    private TextView tvTitle;
    private TextView tvPlaces;
    private TextView tvDoors;
    private TextView tvGearbox;
    private TextView tvAirConditioner;
    private TextView tvPower;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_car_page);
        setTextViewObjects();
        car = getCarFromIntent();
        System.out.println("CAR PAGE DATA:" + car.toString());
        initCarPage();
    }

    public Car getCarFromIntent() {
        Car car = null;
        Intent intent = getIntent();
        if (intent.hasExtra("car")) {
            car = (Car) intent.getSerializableExtra("car");
        }
        return car;
    }

    public void setTextViewObjects() {
        tvYear = findViewById(R.id.tvYearCarPage);
        tvTitle = findViewById(R.id.tvTitleCarPage);
        tvPlaces = findViewById(R.id.tvPlacesCarPage);
        tvDoors = findViewById(R.id.tvDoorsCarPage);
        tvGearbox = findViewById(R.id.tvGearboxCarPage);
        tvAirConditioner = findViewById(R.id.tvAcCarPage);
        tvPower = findViewById(R.id.tvPowerCarPage);
    }

    public void initCarPage() {
        setTextView(tvYear, Integer.toString(car.getYear()));
        setTextView(tvTitle, car.getProducer() + " " + car.getModel());
        setTextView(tvPlaces, Integer.toString(car.getPlaces()));
        setTextView(tvDoors, Integer.toString(car.getDoors()));
        setTextView(tvGearbox, car.getGearbox());
        setTextView(tvAirConditioner, car.isAirConditioner());
        setTextView(tvPower, Integer.toString(car.getPower()));
    }

    public void setTextView(TextView textView, String text) {
        textView.setText(text);
    }

}
