package com.example.sundari.accidentinfo;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

public class RegistrationActivity extends AppCompatActivity {

    private TextView textView , login;
    private RadioGroup radioGroup;
    private RadioButton radioButton;
    private EditText name , empID , email , phone , password , region;
    private Button register;
    private ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);

        textView = findViewById(R.id.tv_RegFor);
        login = findViewById(R.id.tv_LoginReg);
        radioGroup = findViewById(R.id.radio_Occupation);
        name = findViewById(R.id.et_Name);
        empID = findViewById(R.id.et_EmpID);
        email = findViewById(R.id.et_Email);
        phone = findViewById(R.id.et_Phone);
        password = findViewById(R.id.et_RegPwd);
        region = findViewById(R.id.et_region);
        register = findViewById(R.id.btn_Register);
        progressBar = findViewById(R.id.progress_Bar);



        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                radioButton = findViewById(radioGroup.getCheckedRadioButtonId());
                register();
            }
        });


        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(RegistrationActivity.this, LoginActivity.class));
            }
        });

    }


    public void onRadioButtonClicked(View view) {
        // Is the button now checked?
        boolean checked = ((RadioButton) view).isChecked();

        // Check which radio button was clicked
        switch (view.getId()) {
            case R.id.radio_police:
                if (checked)
                    region.setEnabled(true);
                break;
            case R.id.radio_rta:
                if (checked)
                    region.setEnabled(true);
                break;
            case R.id.radio_ambulance:
                if (checked)
                    region.setEnabled(true);
                break;
            case R.id.radio_other:
                if (checked)
                    region.setEnabled(false);
                break;
        }
    }


    private void register() {
            startActivity(new Intent(RegistrationActivity.this, LoginActivity.class));

    }









}
