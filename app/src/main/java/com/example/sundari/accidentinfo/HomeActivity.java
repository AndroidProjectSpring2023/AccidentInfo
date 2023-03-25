package com.example.sundari.accidentinfo;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
public class HomeActivity extends AppCompatActivity {

    private Button logout, prepReport, accessFile, profile;
    private TextView textView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        logout = findViewById(R.id.btnLogout);
        profile = findViewById(R.id.profileBTN);
        prepReport = findViewById(R.id.btn_PrepReport);
        accessFile = findViewById(R.id.btn_AccessFile);
        textView = findViewById(R.id.textView);


        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Logout();
            }
        });

        prepReport.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(HomeActivity.this , ReportPrepareActivity.class));
            }
        });

        accessFile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(HomeActivity.this ,AccessFileActivity.class ));
            }
        });

        profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(HomeActivity.this, ProfileActivity.class));
            }
        });
    }

    private void Logout(){
        startActivity(new Intent(HomeActivity.this, LoginActivity.class));
    }

}