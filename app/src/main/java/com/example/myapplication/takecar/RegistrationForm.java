package com.example.myapplication.takecar;

import static android.content.ContentValues.TAG;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.Firebase;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.Toast;
import android.widget.ViewFlipper;

import java.util.HashMap;

public class RegistrationForm extends AppCompatActivity {

    private ViewFlipper viewFlipper;
    private Button prevButton;
    private Button nextButton;

    private Switch btnAcceptPolicy;
    private Switch btnProvidedTrueData;
    private Button btnRegister;

    private int REGISTRATION_FORM_PAGE = 1;
    private int NUMBER_OF_REGISTRATION_FORM_PAGES = 4;

    //Registration Form First Page
    private EditText etFirstName;
    private EditText etLastName;
    private EditText etPhoneNumber;
    private EditText etEmail;
    private EditText etPassword;
    private EditText etRepeatPassword;
    private EditText etUserBirthDate;

    //Registration Form Second Page
    private EditText etCountry;
    private EditText etStreet;
    private EditText etNumber;
    private EditText etPostalCode;
    private EditText etCity;

    private User user = new User();

    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration_form);

        viewFlipper = findViewById(R.id.viewFlipper);
        prevButton = (Button) findViewById(R.id.btnPrev);
        nextButton = findViewById(R.id.btnNext);
        hideButton(prevButton);
        setEtObjects();
        btnAcceptPolicy = (Switch) findViewById(R.id.btnAcceptPolicy);
        btnProvidedTrueData = (Switch) findViewById(R.id.btnProvidedTrueData);

        btnRegister = (Button) findViewById(R.id.btnRegisterUser);
        mAuth = FirebaseAuth.getInstance();

    }

    public void previousStep(View view) {
        viewFlipper.showPrevious();
        REGISTRATION_FORM_PAGE--;
        if (isFirstPage())
            hideButton(prevButton);
        else {
            showButton(prevButton);
            showButton(nextButton);
        }
    }

    public void register(View view) {
        mAuth.createUserWithEmailAndPassword(user.getUserEmail(), user.getUserPassword());
        String userId = mAuth.getUid();
        DatabaseManager databaseManager = new DatabaseManager();
        databaseManager.addUser(userId, user);
    }

    public void nextStep(View view) {
        viewFlipper.showNext();
        if (REGISTRATION_FORM_PAGE == 1)
            getDataFromRegistrationFirstPage();
        if (REGISTRATION_FORM_PAGE == 2)
            getDataFromRegistrationSecondPage();
        REGISTRATION_FORM_PAGE++;
        if (isLastPage())
            hideButton(nextButton);
        else {
            showButton(nextButton);
            showButton(prevButton);
        }
    }

    public void setEtObjects() {
        etFirstName = (EditText) findViewById(R.id.etFirstName);
        etLastName = (EditText) findViewById(R.id.etLastName);
        etPhoneNumber = (EditText) findViewById(R.id.etPhone);
        etEmail = (EditText) findViewById(R.id.etEmail);
        etPassword = (EditText) findViewById(R.id.etPassword);
        etRepeatPassword = (EditText) findViewById(R.id.etRepeatPassword);
        etUserBirthDate = (EditText) findViewById(R.id.etDateOfBirth);

        etCountry = (EditText) findViewById(R.id.etCountry);
        etStreet = (EditText) findViewById(R.id.etStreet);
        etNumber = (EditText) findViewById(R.id.etNumber);
        etPostalCode = (EditText) findViewById(R.id.etPostalCode);
        etCity = (EditText) findViewById(R.id.etCity);
    }

    public void getDataFromRegistrationFirstPage() {
        user.setUserFirstName(getEtValue(etFirstName));
        user.setUserLastName(getEtValue(etLastName));
        user.setUserPhoneNumber(getEtValue(etPhoneNumber));
        user.setUserEmail(getEtValue(etEmail));
        user.setUserPassword(getEtValue(etPassword));
        user.setUserRepeatPassword(getEtValue(etRepeatPassword));
        user.setUserBirthDate(getEtValue(etUserBirthDate));
        System.out.println(user.toString());
    }

    public void checkAgreements(View view) {
        if(btnAcceptPolicy.isChecked() && btnProvidedTrueData.isChecked())
            enableRegistration();
        else
            disableRegistration();
    }

    public void enableRegistration() {
        btnRegister.setEnabled(true);
    }

    public void disableRegistration() {
        btnRegister.setEnabled(false);
    }

    public void getDataFromRegistrationSecondPage() {
        user.setAddressCountry(getEtValue(etCountry));
        user.setAddressStreet(getEtValue(etStreet));
        user.setAddressNumber(getEtValue(etNumber));
        user.setAddressPostalCode(getEtValue(etPostalCode));
        user.setAddressCity(getEtValue(etCity));
        System.out.println(user.toString());
    }

    public boolean isLastPage() {
        if (REGISTRATION_FORM_PAGE == NUMBER_OF_REGISTRATION_FORM_PAGES)
            return true;
        else
            return false;
    }

    public boolean isFirstPage() {
        if (REGISTRATION_FORM_PAGE == 1)
            return true;
        else
            return false;
    }

    public String getEtValue(EditText editText) {
        return editText.getText().toString().trim();
    }

    public void showButton(Button button) {
        button.setVisibility(View.VISIBLE);
    }

    public void hideButton(Button button) {
        button.setVisibility(View.INVISIBLE);
    }


}
