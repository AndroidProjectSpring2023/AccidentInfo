package com.example.sundari.accidentinfo;


import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class ProfileActivity extends AppCompatActivity {

    private TextView profileName, profileEmpID, profileEmail, profilePhone, profileOccupation, profileRegion;
    private Button  ChangePwd;
    private FirebaseAuth firebaseAuth;
    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference databaseReference;
    private AlertDialog.Builder builder;
    private AlertDialog dialog;
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

        firebaseAuth = FirebaseAuth.getInstance();
        firebaseDatabase = FirebaseDatabase.getInstance();
        AlertDialog.Builder builder = new AlertDialog.Builder(ProfileActivity.this);
        builder.setCancelable(false);
        builder.setView(R.layout.progress_layout);
        AlertDialog dialog = builder.create();
        dialog.show();
        Uid = firebaseAuth.getUid();
        databaseReference = firebaseDatabase.getReference("User").child(Uid);

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                UserProfile userProfile = dataSnapshot.getValue(UserProfile.class);
                dialog.dismiss();

                profileName.setText("Name   : " + userProfile.getUserName());
                profileEmpID.setText("EmpID  : " + userProfile.getUserEmpID());
                profileEmail.setText("Email    : " + userProfile.getUserEmail());
                profilePhone.setText("Phone No   : " + userProfile.getUserPhone());
                profileOccupation.setText("Occupation: " + userProfile.getUserOccupation());

                if ( userProfile.getUserOccupation().equals("AMBULANCE") )
                    profileRegion.setText("Hospital      : " + userProfile.getUserRegion());
                else
                    profileRegion.setText("Region         : " + userProfile.getUserRegion());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(ProfileActivity.this, databaseError.getCode(), Toast.LENGTH_SHORT).show();
            }
        });

        ChangePwd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(ProfileActivity.this , ChangePwdActivity.class));
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()){
            case android.R.id.home :
                onBackPressed();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

}
