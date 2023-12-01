package com.example.myapplication.takecar;

import android.net.Uri;
import android.os.Build;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.gms.tasks.Tasks;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.ListResult;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class DatabaseManager {

    private String DB_URL = "https://takecar-a8abc-default-rtdb.europe-west1.firebasedatabase.app/";
    private FirebaseDatabase database = FirebaseDatabase.getInstance(DB_URL);
    private FirebaseStorage storage = FirebaseStorage.getInstance();
    private int numberOfCars = 0;

    public interface CarPhotosCallback {
        void onCarPhotosReceived(ArrayList<Uri> photosUrisList);
        void onCarPhotosError(String errorMessage);
    }

    public interface CarDataCallback {
        void onCarsDataReceived(ArrayList<Car> carsList);
        void onCarsDataError(String errorMessage);
    }

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

    public void getCarPhotos(String carID, CarPhotosCallback carPhotosCallback) {
        StorageReference storageRef = storage.getReference().child("cars");
        storageRef.listAll().addOnSuccessListener(new OnSuccessListener<ListResult>() {
            @Override
            public void onSuccess(ListResult listResult) {
                ArrayList<Task<Uri>> tasks = new ArrayList<>();

                for (StorageReference item : listResult.getItems()) {
                    String filename = item.getName();

                    if (filename.startsWith(carID)) {
                        Task<Uri> task = item.getDownloadUrl();
                        tasks.add(task);
                    }
                }

                Tasks.whenAllComplete(tasks).addOnCompleteListener(new OnCompleteListener<List<Task<?>>>() {
                    @Override
                    public void onComplete(@NonNull Task<List<Task<?>>> allTasks) {
                        ArrayList<Uri> photosUris = new ArrayList<>();

                        for (Task<?> task : allTasks.getResult()) {
                            if (task.isSuccessful()) {
                                Uri uri = (Uri) task.getResult();
                                photosUris.add(uri);
                            } else {
                                // Handle failure for individual tasks if needed
                                Exception exception = task.getException();
                            }
                        }

                        if (photosUris.isEmpty()) {
                            System.out.println("no photos for this car!");
                            carPhotosCallback.onCarPhotosError("Error");
                        } else {
                            carPhotosCallback.onCarPhotosReceived(photosUris);
                        }
                    }
                });
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                System.out.println("Failed to list photos for this car!");
                carPhotosCallback.onCarPhotosError("Error");
            }
        });
    }


    public void addCar(Car car) {
        HashMap<String, String> data;
        String userID = FirebaseAuth.getInstance().getUid();
        String timestamp = String.valueOf(System.currentTimeMillis());
        String carID = userID + "_" + timestamp;
        data = createCarObjectForDB(carID, car);
        DatabaseReference myRef = database.getReference("cars");
        myRef.child(carID).setValue(data);
        addCarPhotos(car.getPhotosUris(), carID);
    }

    public void getCars(CarDataCallback carDataCallback) {
        DatabaseReference ref = database.getReference("cars");
        ArrayList<Car> carsList = new ArrayList<Car>();
        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                HashMap<String, HashMap<String, String>> cars = (HashMap<String, HashMap<String, String>>)dataSnapshot.getValue();

                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {

                    cars.forEach((key, value) -> {
                        carsList.add(createCarObject(key, value));
                        getCarPhotos(key, new CarPhotosCallback() {
                            @Override
                            public void onCarPhotosReceived(ArrayList<Uri> photosUrisList) {
                                carsList.get(carsList.size()-1).setPhotosUris(photosUrisList);
                                numberOfCars ++;

                                if ( numberOfCars == cars.size()) {
                                    carDataCallback.onCarsDataReceived(carsList);
                                    numberOfCars = 0;
                                }

                            }

                            @Override
                            public void onCarPhotosError(String errorMessage) {
                                System.out.println(errorMessage);
                            }
                        });
                    });

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                System.out.println("The read failed: " + error.getCode());
                carDataCallback.onCarsDataError("ERROR");
            }

        });
    }

    public Car createCarObject(String ID, HashMap<String, String> properties) {
        return new Car(ID,
                properties.get("producer"),
                properties.get("model"),
                Integer.parseInt(properties.get("year")),
                Integer.parseInt(properties.get("power")),
                Integer.parseInt(properties.get("doors")),
                Integer.parseInt(properties.get("places")),
                properties.get("plateNumber"), properties.get("vin"),
                Boolean.parseBoolean(properties.get("airConditioner")),
                properties.get("gearbox"),
                Integer.parseInt(properties.get("priceForHour")),
                Integer.parseInt(properties.get("priceForDay")));
    }

    public HashMap<String, String> createCarObjectForDB(String carID, Car car) {
        HashMap<String, String> carInfo = new HashMap<>();
        carInfo.put("producer", car.getProducer());
        carInfo.put("model", car.getModel());
        carInfo.put("year", String.valueOf(car.getYear()));
        carInfo.put("power", String.valueOf(car.getPower()));
        carInfo.put("doors", String.valueOf(car.getDoors()));
        carInfo.put("places", String.valueOf(car.getPlaces()));
        carInfo.put("plateNumber", car.getPlateNumber());
        carInfo.put("vin", car.getVin());
        carInfo.put("airConditioner", car.isAirConditioner());
        carInfo.put("gearbox", car.getGearbox());
        carInfo.put("priceForHour", String.valueOf(car.getHourlyPrice()));
        carInfo.put("priceForDay", String.valueOf(car.getDailyPrice()));

        return carInfo;
    }

    public void addUser(String userID, User user) {
        HashMap<String, HashMap<String, String>> data;
        data = createUserObjectForDB(userID, user);
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
