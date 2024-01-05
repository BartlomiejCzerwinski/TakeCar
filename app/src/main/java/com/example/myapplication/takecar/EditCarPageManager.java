package com.example.myapplication.takecar;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.squareup.picasso.Picasso;

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

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_car_manager_page);
        car = getCarFromIntent();
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
    public void setEditTextObjects() {
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
        tvYear.setText(car.getYear());
        tvTitle.setText(car.getProducer() + " " + car.getModel());
        etPlaces.setText(car.getPlaces());
        etDoors.setText(car.getDoors());
        tvGearbox.setText(car.getGearbox());
        tvAirConditioner.setText(car.isAirConditioner());
        etPower.setText(car.getPower());
        etHourlyPrice.setText(car.getHourlyPrice() + "$");
        etDailyPrice.setText(car.getDailyPrice() + "$");
        Picasso.get().load(car.getPhotosUris().get(0)).fit().into(ivCar);
    }
}
