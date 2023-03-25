package com.example.sundari.accidentinfo;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class InformationActivity extends AppCompatActivity {

    private TextView tv_VName , tv_VAge , tv_fileName , tv_reason , tv_location , tv_injuries , tv_InsuCompany , tv_policyNo , tv_imgInfo , tv_videoInfo;

    private DatabaseReference databaseReference;
    private ProgressDialog progressDialog;

    String fileName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_information);

        tv_VName = findViewById(R.id.tv_vName);
        tv_VAge = findViewById(R.id.tv_vAge);
        tv_fileName = findViewById(R.id.tv_fileName);
        tv_reason = findViewById(R.id.tv_Reason);
        tv_location = findViewById(R.id.tv_Location);
        tv_injuries = findViewById(R.id.tv_Injuries);
        tv_InsuCompany = findViewById(R.id.tv_InsuCompany);
        tv_policyNo = findViewById(R.id.tv_PolicyNo);
        tv_imgInfo = findViewById(R.id.tv_ImgInfo);
        tv_videoInfo = findViewById(R.id.tv_VideoInfo);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        Bundle bundle = getIntent().getExtras();
        if (bundle != null)
            fileName = bundle.getString("fileName");

        databaseReference = FirebaseDatabase.getInstance().getReference("information/" + fileName);

        progressDialog = new ProgressDialog(this);

        tv_fileName.append(fileName);

        progressDialog.setMessage("Loading.....");
        progressDialog.show();

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                Accident_Info info = dataSnapshot.getValue(Accident_Info.class);
                if (info != null){
                    tv_VName.append(info.getvName());
                    tv_VAge.append(info.getvAge());
                    tv_reason.append(info.getReason());
                    tv_location.append(info.getLocation());
                    tv_injuries.append(info.getInjuries());
                    tv_InsuCompany.append(info.getIncuranceCompany());
                    tv_policyNo.append(info.getPolicy_No());
                    progressDialog.dismiss();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(InformationActivity.this , databaseError.toString() , Toast.LENGTH_SHORT).show();
                progressDialog.dismiss();
            }
        });

        tv_imgInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(InformationActivity.this , ImageActivity.class);
                intent.putExtra("fileName", fileName);
                startActivity(intent);
            }
        });

        tv_videoInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(InformationActivity.this , VideoActivity.class);
                intent.putExtra("fileName", fileName);
                startActivity(intent);
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

