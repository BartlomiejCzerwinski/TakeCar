package com.example.myapplication.takecar;

import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Spinner;

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

    private Car car = new Car();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_car_form);
        initSelectGearboxSpinner();
        setEtObjects();
        initPhotoPicker();
    }

    public void initPhotoPicker() {
        ActivityResultLauncher<PickVisualMediaRequest> pickMultipleMedia =
                registerForActivityResult(new ActivityResultContracts.PickMultipleVisualMedia(MAX_PHOTOS), uris -> {
                    if (!uris.isEmpty()) {
                        Log.d("PhotoPicker", "Number of items selected: " + uris.size());
                        Log.d("URIS:", uris.get(0).toString());
                        DatabaseManager databaseManager = new DatabaseManager();
                        databaseManager.addCarPhotos(uris, "ABC123123123");
                    } else {
                        Log.d("PhotoPicker", "No media selected");
                    }
                });
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
        car.setYear(Integer.getInteger(getEtValue(etYear)));
        car.setPower(Integer.getInteger(getEtValue(etPower)));
        car.setDoors(Integer.getInteger(getEtValue(etDoors)));
        car.setPlaces(Integer.getInteger(getEtValue(etPlaces)));
        car.setPlateNumber(getEtValue(etPlateNumber));
        car.setVin(getEtValue(etVin));
        car.setAirConditioner(rbIsAirConditioner.isChecked());
        car.setGearbox(spinnerGearbox.getSelectedItem().toString());
        car.setHourlyPrice(Integer.getInteger(getEtValue(etHourlyPrice)));
        car.setDailyPrice(Integer.getInteger(getEtValue(etDailyPrice)));
    }

    public void setEtObjects() {
        etProducer = findViewById(R.id.etCarProducer);
        etModel = findViewById(R.id.etCarModel);
        etYear = findViewById(R.id.etCarYear);
        etPower = findViewById(R.id.etCarYear);
        etDoors = findViewById(R.id.etCarDoors);
        etPlaces = findViewById(R.id.etCarPlaces);
        etPlateNumber = findViewById(R.id.etCarPlateNumber);
        etVin = findViewById(R.id.etCarVin);
        rbIsAirConditioner = findViewById(R.id.rbIsAirConditioner);
        spinnerGearbox = findViewById(R.id.spinnerGearbox);
        etHourlyPrice = findViewById(R.id.etCarHourlyPrice);
        etDailyPrice = findViewById(R.id.etCarDailyPrice);
    }

    public String getEtValue(EditText editText) {
        return editText.getText().toString().trim();
    }

}
