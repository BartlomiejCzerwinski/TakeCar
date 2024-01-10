package com.example.myapplication.takecar;

import android.content.BroadcastReceiver;
import android.health.connect.datatypes.StepsCadenceRecord;
import android.net.Uri;
import android.os.Build;
import android.provider.ContactsContract;

import androidx.annotation.NonNull;
import androidx.dynamicanimation.animation.SpringAnimation;

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

import java.lang.reflect.Array;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class DatabaseManager {

    private String DB_URL = "https://takecar-a8abc-default-rtdb.europe-west1.firebasedatabase.app/";
    private FirebaseDatabase database = FirebaseDatabase.getInstance(DB_URL);
    private FirebaseStorage storage = FirebaseStorage.getInstance();
    private int numberOfCars = 0;
    private int reservedCars = 0;
    private float INITIAL_ACCOUNT_BALANCE = 1500.0F;

    public interface CarPhotosCallback {
        void onCarPhotosReceived(ArrayList<Uri> photosUrisList);
        void onCarPhotosError(String errorMessage);
    }

    public interface CarDataCallback {
        void onCarsDataReceived(ArrayList<Car> carsList);
        void onCarsDataError(String errorMessage);
    }

    public interface AccountBalanceCallback {
        void onBalanceReceived(float accountBalance);
    }

    public interface MoneyTransferCallback {
        void onSuccessfulTransfer(String message);
        void onFailedTransfer(String message);
    }

    public interface RentCarCallback {
        void onSuccessfulRent();
        void onFailedRent();
    }

    public interface RentalsCallback {
        void onRentalsReceived(ArrayList<Rental> rentals);
    }

    public void addCarRent(Car car, int numberOfHours, int numberOfDays,
                           RentCarCallback rentCarCallback) {
        HashMap<String, String> data = new HashMap<String, String>();
        String userID = FirebaseAuth.getInstance().getUid();
        String timestamp = String.valueOf(System.currentTimeMillis());
        String rentID = userID + "_" + car.getID() + "_" + timestamp;
        int totalPrice = car.getRentalTotalPrice(numberOfHours, numberOfDays);

        rentCarMoneyTransfer(car.getOwnerID(), userID, (float) totalPrice,
                new MoneyTransferCallback() {
            @Override
            public void onSuccessfulTransfer(String message) {
                System.out.println(message);
                String endRentalTime = null;
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                    endRentalTime = LocalDateTime.now().plusHours(numberOfHours)
                            .plusDays(numberOfDays).toString();
                }
                setCarEndRentalTime(car.getID(), endRentalTime);
                data.put("carID", car.getID());
                data.put("ownerID", car.getOwnerID());
                data.put("takerID", userID);
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                    data.put("startTime", LocalDateTime.now().toString());
                }
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                    data.put("endTime", endRentalTime);
                }
                data.put("totalPrice", String.valueOf(totalPrice));

                DatabaseReference myRef = database.getReference("rentals");
                myRef.child(rentID).setValue(data);
                rentCarCallback.onSuccessfulRent();
            }

            @Override
            public void onFailedTransfer(String message) {
                System.out.println(message);
                rentCarCallback.onFailedRent();
            }
        });
    }

    public void setCarEndRentalTime(String carID, String endRentalTime) {
        DatabaseReference databaseReference = database.getReference("cars/"+carID+"/endRentalTime");
        databaseReference.setValue(endRentalTime);
    }

    public void rentCarMoneyTransfer(String carOwnerID, String carTakerID,
                                     float money, MoneyTransferCallback moneyTransferCallback) {
        getAccountBalance(carTakerID, new AccountBalanceCallback() {
            @Override
            public void onBalanceReceived(float accountBalance){
                float carTakerAccountBalance = accountBalance;
                if (carTakerAccountBalance - money < 0) {
                    moneyTransferCallback.onFailedTransfer("Not enought money on account");
                    return;
                }
                getAccountBalance(carOwnerID, new AccountBalanceCallback() {
                    @Override
                    public void onBalanceReceived(float accountBalance) {
                        float carOwnerAccountBalance = accountBalance;
                        setAccountBalance(carOwnerID, carOwnerAccountBalance + money);
                        setAccountBalance(carTakerID, carTakerAccountBalance - money);
                        moneyTransferCallback.onSuccessfulTransfer("Successfully rented a car!");
                    }
                });
            }
        });
    }

    public void getAccountBalance(String accountID, AccountBalanceCallback accountBalanceCallback) {
        DatabaseReference databaseReference = database.getReference("users/"+accountID+"/userInfo/accountBalance");
        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                System.out.println("accountID: " + accountID);
                float accountBalance = Float.valueOf(snapshot.getValue().toString());
                System.out.println("balance:" + accountBalance);
                accountBalanceCallback.onBalanceReceived(accountBalance);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    public void setAccountBalance(String accountID, float newBalance) {
        DatabaseReference databaseReference = database
                .getReference("users/"+accountID+"/userInfo/accountBalance");
        databaseReference.setValue(String.valueOf(newBalance));
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

    public boolean isCarOwnerWantsToLoadHisCarOnTakePage(String carID, String userID) {
        return carID.equals(userID);
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

    public interface UserNameCallback {
        void onNameReceived(String name);
        void onCancelledCallback(DatabaseError error);
    }

    public void getUserName(UserNameCallback userNameCallback) {
        DatabaseReference ref = database.getReference("users");
        String userID = FirebaseAuth.getInstance().getUid();

        ref.child(userID+"/userInfo/firstName").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    String name = dataSnapshot.getValue().toString();
                    System.out.println(name);
                    userNameCallback.onNameReceived(name);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                System.err.println("Error: " + databaseError.getMessage());
                userNameCallback.onCancelledCallback(databaseError);
            }
        });
    }


    public void getCars(Boolean getCarsOwnedByLoggedUser, CarDataCallback carDataCallback) {
        DatabaseReference ref = database.getReference("cars");
        ArrayList<Car> carsList = new ArrayList<Car>();
        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                HashMap<String, HashMap<String, String>> cars = (HashMap<String, HashMap<String, String>>)dataSnapshot.getValue();

                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                    cars.forEach((key, value) -> {
                        if (!getCarsOwnedByLoggedUser) {
                            if (isCarRented(value.get("endRentalTime")) || !isCarAvailable(value.get("isAvailable"))) {
                                reservedCars++;
                                return;
                            }
                        }
                        else {
                            if (!isCarAvailable(value.get("isAvailable"))) {
                                reservedCars++;
                                return;
                            }
                        }
                        Car car = createCarObject(key, value);
                            getCarPhotos(key, new CarPhotosCallback() {
                                @Override
                                public void onCarPhotosReceived(ArrayList<Uri> photosUrisList) {
                                    car.setPhotosUris(photosUrisList);
                                    carsList.add(car);
                                    numberOfCars++;

                                    if (numberOfCars == cars.size() - reservedCars) {
                                        ArrayList<Car> result = null;
                                        if (getCarsOwnedByLoggedUser)
                                            result = getCarsOwnedByLoggedUser(carsList);
                                        else
                                            result = getCarsNotOwnedByLoggedUser(carsList);
                                        carDataCallback.onCarsDataReceived(result);
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

    public boolean isCarAvailable(String isAvailable) {
        System.out.println("is available: " + isAvailable);
        return Boolean.valueOf(isAvailable);
    }

    public void getRentalsOfCar(String carID, RentalsCallback rentalsCallback) {
        getRentals(rentals -> {
            ArrayList<Rental> result = new ArrayList<Rental>();
            for (Rental rental : rentals) {
                if (rental.getCarID() != null)
                if (rental.getCarID().equals(carID))
                    result.add(rental);
            }
            rentalsCallback.onRentalsReceived(result);
        });
    }

    public void getRentals(RentalsCallback rentalsCallback) {
        DatabaseReference ref = database.getReference("rentals");
        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                ArrayList<Rental> rentals = new ArrayList<Rental>();
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    Rental rental = dataSnapshot.getValue(Rental.class);
                    if (rental != null)
                        rentals.add(rental);
                }
                System.out.println("RENTALS OBJECTS: " + rentals.toString());
                rentalsCallback.onRentalsReceived(rentals);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    public ArrayList<Car> getCarsNotOwnedByLoggedUser(ArrayList<Car> carsList) {
        ArrayList<Car> result = new ArrayList<Car>();
        for (Car car : carsList) {
            if (!isCarOwnerWantsToLoadHisCarOnTakePage(car.getOwnerID(), FirebaseAuth.getInstance().getUid())) {
                result.add(car);
            }
        }
        return result;
    }

    public ArrayList<Car> getCarsOwnedByLoggedUser(ArrayList<Car> carsList) {
        ArrayList<Car> result = new ArrayList<Car>();
        for (Car car : carsList) {
            if (isCarOwnerWantsToLoadHisCarOnTakePage(car.getOwnerID(), FirebaseAuth.getInstance().getUid())) {
                result.add(car);
            }
        }
        return result;
    }

    public boolean isCarRented(String endRentalTime) {
        LocalDateTime now = null;
        LocalDateTime endRentalTimeValue = null;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            now = LocalDateTime.now();
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            endRentalTimeValue = LocalDateTime.parse(endRentalTime);
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            if(now.isBefore(endRentalTimeValue)) {
                return true;
            }
        }
        return false;
    }

    public Car createCarObject(String ID, HashMap<String, String> properties) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
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
        return null;
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
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            carInfo.put("endRentalTime", String.valueOf(LocalDateTime.now().minusDays(1))); // If the car was never rented, it's rental date is expired.
        }                                                                                   // The car will be available after adding it.

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
        userInfo.put("accountBalance", String.valueOf(INITIAL_ACCOUNT_BALANCE));

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
