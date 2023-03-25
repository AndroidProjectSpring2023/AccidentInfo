package com.example.sundari.accidentinfo;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;



public class LoginActivity extends AppCompatActivity {

    private EditText Email;
    private EditText Password;
    private TextView Info;
    private Button Login;
    private int counter = 5;
    private TextView userRegistration;
    private TextView forgotPassword , insuAgent;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);


        Email = findViewById(R.id.etEmail);
        Password = findViewById(R.id.etPassword);
        Info = findViewById(R.id.tvInfo);
        Login = findViewById(R.id.btnLogin);
        userRegistration = findViewById(R.id.tvRegister);
        forgotPassword = findViewById(R.id.tvForgotPassword);
        insuAgent = findViewById(R.id.tv_insuSignIn);

        Info.setText("No of attempts remaining: 5");

        Login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                validate(Email.getText().toString().trim() , Password.getText().toString().trim());
            }
        });

        userRegistration.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(LoginActivity.this, RegistrationActivity.class));
            }
        });

        forgotPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(LoginActivity.this, PasswordActivity.class));
            }
        });
    }

    private void validate(String userEmail, String userPassword) {
        if (userEmail.isEmpty() || userPassword.isEmpty()){
            Toast.makeText(this, "Please enter the details", Toast.LENGTH_SHORT).show();
        }
        else if (userEmail.equals("Admin") && userPassword.equals("password")){
            finish();
            Toast.makeText(LoginActivity.this, "Login Successful", Toast.LENGTH_SHORT).show();
            startActivity(new Intent(LoginActivity.this, HomeActivity.class));
        }

    }



}

