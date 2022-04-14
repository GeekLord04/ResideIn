package com.geekym.residein;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;


public class SignUp extends AppCompatActivity {
    RadioGroup radioGroup;
    RadioButton male,female,others;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        radioGroup = findViewById(R.id.radioGroup);
        male = findViewById(R.id.male_btn);
        female = findViewById(R.id.female_btn);
        others = findViewById(R.id.other_btn);
    }
    public void checkbtn(View v){
        int radioId = radioGroup.getCheckedRadioButtonId();

        RadioButton radiobtn = findViewById(radioId);
        Toast.makeText(getApplicationContext(), "Selected radio is"+radiobtn, Toast.LENGTH_SHORT).show();
    }
}