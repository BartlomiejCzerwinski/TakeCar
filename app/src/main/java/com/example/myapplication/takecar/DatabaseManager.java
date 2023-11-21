package com.example.myapplication.takecar;

import android.net.Uri;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.util.HashMap;
import java.util.List;

public class DatabaseManager {

    private String DB_URL = "https://takecar-a8abc-default-rtdb.europe-west1.firebasedatabase.app/";
    private FirebaseDatabase database = FirebaseDatabase.getInstance(DB_URL);
    private FirebaseStorage storage = FirebaseStorage.getInstance();

    public void addCarPhotos(List<Uri> uris, String carID) {
        for(Uri uri : uris) {
            StorageReference storageRef = storage.getReference();
            String timestamp = String.valueOf(System.currentTimeMillis());
            StorageReference riversRef = storageRef.child("cars/"+ carID + "_" + timestamp);

            UploadTask uploadTask = riversRef.putFile(uri);
            uploadTask.addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception exception) {
                    // Handle unsuccessful uploads
                }
            }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    // taskSnapshot.getMetadata() contains file metadata such as size, content-type, etc.
                    // ...
                }
            });
        }
    }

    public void addUser(String userID, User user) {
        HashMap<String, HashMap<String, String>> data;
        data = createUserObjectForDB(userID, user);
        System.out.println("OBJECT FOR DB: " + data + "AND ID : " + userID);
        DatabaseReference myRef = database.getReference("users");
        myRef.child(userID).setValue(data);
    }

    public HashMap<String, HashMap<String, String>> createUserObjectForDB(String userID, User user) {
        HashMap<String, HashMap<String, String>> userObject = new HashMap<>();
        HashMap<String, String> userInfo = new HashMap<>();
        HashMap<String, String> userAddress = new HashMap<>();

        userInfo.put("firstName", user.getUserFirstName());
        userInfo.put("lastName", user.getUserLastName());
        userInfo.put("phoneNumber", user.getUserPhoneNumber());
        userInfo.put("email", user.getUserEmail());
        userInfo.put("dateOfBirth", user.getUserBirthDate());

        userAddress.put("country", user.getAddressCountry());
        userAddress.put("street", user.getAddressStreet());
        userAddress.put("number", user.getAddressNumber());
        userAddress.put("postalCode", user.getAddressPostalCode());
        userAddress.put("city", user.getAddressCity());

        userObject.put("userInfo", userInfo);
        userObject.put("userAddress", userAddress);

        return userObject;
    }
}
