package com.example.myapplication.takecar;

import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.widget.ArrayAdapter;
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
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_car_form);
        initSelectGearboxSpinner();
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

}
