package com.example.myapplication.takecar;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;

public class DatabaseManager {

    private String DB_URL = "https://takecar-a8abc-default-rtdb.europe-west1.firebasedatabase.app/";
    private FirebaseDatabase database = FirebaseDatabase.getInstance(DB_URL);

    public void addUser(String userID, User user) {
        HashMap<String, HashMap<String, String>> data;
        data = createUserObjectForDB(userID, user);
        System.out.println("OBJECT FOR DB: " + data + "AND ID : " + userID);
        DatabaseReference myRef = database.getReference("users");
        myRef.child(userID).setValue(data);
    }

    private HashMap<String, HashMap<String, String>> createUserObjectForDB(String userID, User user) {
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
