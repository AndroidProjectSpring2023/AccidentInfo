package com.example.sundari.accidentinfo;


import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


public class InformationActivity extends AppCompatActivity {

    private TextView tv_VName , tv_VAge , tv_fileName , tv_reason , tv_location , tv_injuries , tv_InsuCompany , tv_policyNo , tv_imgInfo , tv_videoInfo;
    private ImageView im_VImage;
    private DatabaseReference databaseReference;
    private AlertDialog.Builder builder;
    private AlertDialog dialog;
    private Button editButton, backToHomeButton;
    private Accident_Info info ;
    private Intent i;
    private boolean status;
    String fileName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_information);

        im_VImage = findViewById(R.id.vImage);
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
        editButton = findViewById(R.id.editVictimButton);
        backToHomeButton = findViewById(R.id.backToHomeButton);


        i = getIntent();
        status = i.getBooleanExtra("isVictimReportViewingByAgent",false);

        if(status == true){
            editButton.setVisibility(View.INVISIBLE);
            backToHomeButton.setVisibility(View.INVISIBLE);
        }
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        Bundle bundle = getIntent().getExtras();
        if (bundle != null) fileName = bundle.getString("fileName");

        databaseReference = FirebaseDatabase.getInstance().getReference("information/" + fileName);

        builder = new AlertDialog.Builder(InformationActivity.this);
        builder.setCancelable(false);
        builder.setView(R.layout.progress_layout);
        dialog = builder.create();
        tv_fileName.append(fileName);
        dialog.show();

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                 info = dataSnapshot.getValue(Accident_Info.class);
                if (info != null){
                    Glide.with(InformationActivity.this).load(info.getvImage()).into(im_VImage);
                    tv_VName.append(info.getvName());
                    tv_VAge.append(info.getvAge());
                    tv_reason.append(info.getReason());
                    tv_location.append(info.getLocation());
                    tv_injuries.append(info.getInjuries());
                    tv_InsuCompany.append(info.getIncuranceCompany());
                    tv_policyNo.append(info.getPolicy_No());
                    dialog.dismiss();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(InformationActivity.this , databaseError.getMessage() , Toast.LENGTH_SHORT).show();
                dialog.dismiss();
            }
        });

        editButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(InformationActivity.this , UpdateVictimReportActivity.class);
                intent.putExtra("fileName", fileName);
                intent.putExtra("victimImage", bundle.getString("Image"));
                intent.putExtra("victimName", info.getvName());
                intent.putExtra("victimAge", info.getvAge());
                intent.putExtra("victimReason", info.getReason());
                intent.putExtra("victimLocation", info.getLocation());
                intent.putExtra("victimInjuries", info.getInjuries());
                intent.putExtra("victimInsuranceCompany", info.getIncuranceCompany());
                intent.putExtra("victimPolicyNumber", info.getPolicy_No());

                startActivity(intent);
            }
        });

        backToHomeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(InformationActivity.this , HomeActivity.class);
                startActivity(intent);
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

//    @Override
//    public boolean onOptionsItemSelected(MenuItem item) {
//
//        switch (item.getItemId()){
//            case android.R.id.home :
//                onBackPressed();
//                break;
//        }
//        return super.onOptionsItemSelected(item);
//    }
}

