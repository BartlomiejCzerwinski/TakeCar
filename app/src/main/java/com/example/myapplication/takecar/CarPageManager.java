package com.example.myapplication.takecar;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class CarPageManager extends AppCompatActivity {

    private Car car;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_car_page);
        car = getCarFromIntent();
        System.out.println("CAR PAGE DATA:" + car.toString());
    }

    public Car getCarFromIntent() {
        Car car = null;
        Intent intent = getIntent();
        if (intent.hasExtra("car")) {
            car = (Car) intent.getSerializableExtra("car");
        }
        return car;
    }

}
