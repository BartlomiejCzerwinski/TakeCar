package com.example.myapplication.takecar;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ViewFlipper;

public class RegistrationForm extends AppCompatActivity {

    private ViewFlipper viewFlipper;
    private Button prevButton;
    private Button nextButton;

    private int REGISTRATION_FORM_PAGE = 1;
    private int NUMBER_OF_REGISTRATION_FORM_PAGES = 4;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration_form);

        viewFlipper = findViewById(R.id.viewFlipper);
        prevButton = (Button) findViewById(R.id.btnPrev);
        nextButton = findViewById(R.id.btnNext);
        hideButton(prevButton);
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

    public void nextStep(View view) {
        viewFlipper.showNext();
        REGISTRATION_FORM_PAGE++;
        if (isLastPage())
            hideButton(nextButton);
        else {
            showButton(nextButton);
            showButton(prevButton);
        }
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

    public void showButton(Button button) {
        button.setVisibility(View.VISIBLE);
    }

    public void hideButton(Button button) {
        button.setVisibility(View.INVISIBLE);
    }
}
