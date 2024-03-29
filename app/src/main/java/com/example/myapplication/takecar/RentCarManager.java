package com.example.myapplication.takecar;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.SeekBar;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class RentCarManager extends AppCompatActivity {
    private TextView tvHours;
    private TextView tvDays;
    private SeekBar sbHours;
    private SeekBar sbDays;

    private TextView tvProducerModel;
    private TextView tvHourPrice;
    private TextView tvDayPrice;
    private TextView tvRentalPrice;

    private TextView tvBetterOptionAvailable;

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
        tvProducerModel = findViewById(R.id.tvProducerModel);
        tvHourPrice = findViewById(R.id.tvTotalIncome);
        tvDayPrice = findViewById(R.id.tvDayPrice);
        tvRentalPrice = findViewById(R.id.tvRentalPrice);
        tvBetterOptionAvailable = findViewById(R.id.tvBetterOptionAvailable);

        setRentalInfo();

        manageSeekBar(sbHours, tvHours, "hour");
        manageSeekBar(sbDays, tvDays, "day");

    }

    public void rentCar(View view) {
        int numberOfHours = getNumberOfHours();
        int numberOfDays = getNumberOfDays();
        Context context = this;
        DatabaseManager databaseManager = new DatabaseManager();
        databaseManager.addCarRent(car, numberOfHours, numberOfDays
                , new DatabaseManager.RentCarCallback() {
            @Override
            public void onSuccessfulRent() {
                System.out.println("Successfully rented car!");
                showAlertDialog(context, "Successfully rented car!");
            }

            @Override
            public void onFailedRent() {

                System.out.println("Failed to rent car!");
                showAlertDialog(context, "Failed to rent car!");
            }
        });
    }
    public static void showAlertDialog(Context context, String message) {

        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(context);

        alertDialogBuilder.setTitle("Rent car info");

        alertDialogBuilder.setMessage(message);

        alertDialogBuilder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                backToMainPage(context);
            }
        });
        alertDialogBuilder.setCancelable(false);
        AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();
    }

    public static void backToMainPage(Context context) {
        Intent intent = new Intent(context, MainPageManager.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP); // Wyczyść stos aktywności
        context.startActivity(intent);
    }

    public void updateRentalPrice() {
        tvRentalPrice.setText(calculateRentalPrice() + "$");
    }

    public int calculateRentalPrice() {
        return (car.getHourlyPrice() * getNumberOfHours()) + (car.getDailyPrice() * getNumberOfDays());
    }

    public int getNumberOfHours() {
        return sbHours.getProgress();
    }

    public int getNumberOfDays() {
        return sbDays.getProgress();
    }

    public void setRentalInfo() {
        tvProducerModel.setText(car.getProducer() + " " + car.getModel());
        tvHourPrice.setText(car.getHourlyPrice() + "$");
        tvDayPrice.setText(car.getDailyPrice() + "$");
    }

    public void isBetterOptionAvailable() {
        int numberOfHours = getNumberOfHours();
        int hourlyPrice = car.getHourlyPrice();
        int dailyPrice = car.getDailyPrice();
        if ((numberOfHours * hourlyPrice) >= dailyPrice) {
            tvBetterOptionAvailable.setVisibility(View.VISIBLE);
        }
        else {
            tvBetterOptionAvailable.setVisibility(View.INVISIBLE);
        }
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
        updateRentalPrice();
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
                updateRentalPrice();
                isBetterOptionAvailable();
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
