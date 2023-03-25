package com.example.sundari.accidentinfo;


import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;



public class ProfileActivity extends AppCompatActivity {

    private TextView profileName, profileEmpID, profileEmail, profilePhone, profileOccupation, profileRegion;
    private Button  ChangePwd;

    String Uid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        profileName = findViewById(R.id.tvProfileName);
        profileEmpID = findViewById(R.id.tvProfileEmpID);
        profileEmail = findViewById(R.id.tvProfileEmail);
        profilePhone = findViewById(R.id.tvProfilePhone);
        profileOccupation = findViewById(R.id.tvProfileOccu);
        profileRegion = findViewById(R.id.tvProfileRegion);
        ChangePwd = findViewById(R.id.btnPwd);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);



        ChangePwd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(ProfileActivity.this , ChangePwdActivity.class));
            }
        });
    }

}
