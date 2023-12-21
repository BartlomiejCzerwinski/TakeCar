package com.example.myapplication.takecar;

import android.annotation.SuppressLint;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.PickVisualMediaRequest;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.File;
import java.time.LocalDateTime;

public class AddCarManager extends AppCompatActivity {
    private int MAX_PHOTOS = 8;

    private EditText etProducer;
    private EditText etModel;
    private EditText etYear;
    private EditText etPower;
    private EditText etDoors;
    private EditText etPlaces;
    private EditText etPlateNumber;
    private EditText etVin;
    private RadioButton rbIsAirConditioner;
    private Spinner spinnerGearbox;
    private EditText etHourlyPrice;
    private EditText etDailyPrice;

    private TextView tvAddedPhotosMessage;

    private Car car = new Car();

    private ActivityResultLauncher<PickVisualMediaRequest> pickMultipleMedia;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_car_form);
        tvAddedPhotosMessage = findViewById(R.id.tvAddedPhotosMessage);
        setNotAddedPhotosMessage();
        initSelectGearboxSpinner();
        setEtObjects();
        initPhotoPicker();

    }

    public void addCar(View view) {
        getDataFromAddCarForm();
        System.out.println(car.toString());
        DatabaseManager databaseManager = new DatabaseManager();
        databaseManager.addCar(car);
    }

    public void initPhotoPicker() {
        pickMultipleMedia =
                registerForActivityResult(new ActivityResultContracts.PickMultipleVisualMedia(MAX_PHOTOS), uris -> {
                    if (!uris.isEmpty()) {
                        Log.d("PhotoPicker", "Number of items selected: " + uris.size());
                        car.setPhotosUris(uris);
                        setAddedPhotosMessage(uris.size());
                    } else {
                        Log.d("PhotoPicker", "No media selected");
                        setNotAddedPhotosMessage();
                    }
                });
    }

    public void launchPhotoPicker(View view) {
        pickMultipleMedia.launch(new PickVisualMediaRequest.Builder()
                .setMediaType(ActivityResultContracts.PickVisualMedia.ImageAndVideo.INSTANCE)
                .build());
    }

    public void initSelectGearboxSpinner() {
        Spinner spinner = (Spinner) findViewById(R.id.spinnerGearbox);
        String[] gearboxes = new String[]{"Gearbox", "Manual", "Automatic"};
        ArrayAdapter<CharSequence> adapter = new ArrayAdapter<>(this, R.layout.spinner_item, gearboxes);
        spinner.setAdapter(adapter);
    }

    public void getDataFromAddCarForm() {
        car.setProducer(getEtValue(etProducer));
        car.setModel(getEtValue(etModel));
        car.setYear(Integer.parseInt(getEtValue(etYear)));
        car.setPower(Integer.parseInt(getEtValue(etPower)));
        car.setDoors(Integer.parseInt(getEtValue(etDoors)));
        car.setPlaces(Integer.parseInt(getEtValue(etPlaces)));
        car.setPlateNumber(getEtValue(etPlateNumber));
        car.setVin(getEtValue(etVin));
        car.setAirConditioner(rbIsAirConditioner.isChecked());
        car.setGearbox(spinnerGearbox.getSelectedItem().toString());
        car.setHourlyPrice(Integer.parseInt(getEtValue(etHourlyPrice)));
        car.setDailyPrice(Integer.parseInt(getEtValue(etDailyPrice)));
    }

    public void setEtObjects() {
        etProducer = findViewById(R.id.etCarProducer);
        etModel = findViewById(R.id.etCarModel);
        etYear = findViewById(R.id.etCarYear);
        etPower = findViewById(R.id.etCarPower);
        etDoors = findViewById(R.id.etCarDoors);
        etPlaces = findViewById(R.id.etCarPlaces);
        etPlateNumber = findViewById(R.id.etCarPlateNumber);
        etVin = findViewById(R.id.etCarVin);
        rbIsAirConditioner = findViewById(R.id.rbIsAirConditioner);
        spinnerGearbox = findViewById(R.id.spinnerGearbox);
        etHourlyPrice = findViewById(R.id.etCarHourlyPrice);
        etDailyPrice = findViewById(R.id.etCarDailyPrice);
    }

    public void setAddedPhotosMessage(int numberOfPhotos) {
        tvAddedPhotosMessage.setText("✅ Successfully added " + Integer.toString(numberOfPhotos) + " photos!");
    }

    public void setNotAddedPhotosMessage() {
        tvAddedPhotosMessage.setText("Not added photos yet.");
    }

    public String getEtValue(EditText editText) {return editText.getText().toString().trim();}

}
