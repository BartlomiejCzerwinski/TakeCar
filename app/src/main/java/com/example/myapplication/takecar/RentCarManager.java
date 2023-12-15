package com.example.myapplication.takecar;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.widget.SeekBar;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class RentCarManager extends AppCompatActivity {
    private TextView tvHours;
    private TextView tvDays;
    private SeekBar sbHours;
    private SeekBar sbDays;

    private Car car;


    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rent_car);

        car = getCarFromIntent();

        tvHours = findViewById(R.id.tvHours);
        tvDays = findViewById(R.id.tvDays);
        sbHours = findViewById(R.id.sbHours);
        sbDays = findViewById(R.id.sbDays);
        manageSeekBar(sbHours, tvHours, "hour");
        manageSeekBar(sbDays, tvDays, "day");
    }

    public Car getCarFromIntent() {
        Car car = null;
        Intent intent = getIntent();
        if (intent.hasExtra("car")) {
            car = (Car) intent.getSerializableExtra("car");
        }
        return car;
    }

    public void manageSeekBar(SeekBar seekBar, TextView textView, String textViewUnit) {
        if (seekBar.getProgress() == 1)
            textView.setText(seekBar.getProgress() + " " + textViewUnit);
        else
            textView.setText(seekBar.getProgress() + " " + textViewUnit + "s");

        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                if (progress == 1)
                    textView.setText(progress + " " +textViewUnit);
                else
                    textView.setText(progress + " " + textViewUnit + "s");
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
    }


}
