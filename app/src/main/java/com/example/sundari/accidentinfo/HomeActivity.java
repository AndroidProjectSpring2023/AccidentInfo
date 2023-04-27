package com.example.sundari.accidentinfo;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Menu;
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

public class HomeActivity extends AppCompatActivity {

    private Button logout, prepReport, accessFile, profile;
    private TextView textView;
    private FirebaseAuth firebaseAuth;
    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference databaseReference;
    private AlertDialog.Builder builder;
    private AlertDialog dialog;
    private UserProfile userProfile = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        logout = findViewById(R.id.btnLogout);
        profile = findViewById(R.id.profileBTN);
        prepReport = findViewById(R.id.btn_PrepReport);
        accessFile = findViewById(R.id.btn_AccessFile);
        textView = findViewById(R.id.textView);

        firebaseAuth = FirebaseAuth.getInstance();
        firebaseDatabase = FirebaseDatabase.getInstance();
        builder = new AlertDialog.Builder(HomeActivity.this);
        builder.setCancelable(false);
        builder.setView(R.layout.progress_layout);
        dialog = builder.create();


        if(firebaseAuth.getUid() != null) {
            databaseReference = firebaseDatabase.getReference("User").child(firebaseAuth.getUid());
        }

        dialog.show();

        databaseReference.addValueEventListener(new ValueEventListener() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                userProfile = dataSnapshot.getValue(UserProfile.class);
                if (userProfile != null) {
                    textView.setText("Welcome , " + userProfile.getUserName() );
                    textView.setTextColor(Color.parseColor("#000000"));
                    if(userProfile.userOccupation.equals("Other")){
                        accessFile.setVisibility(View.INVISIBLE);
                    }
                    dialog.dismiss();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(HomeActivity.this, databaseError.getCode(), Toast.LENGTH_SHORT).show();
            }
        });


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
        firebaseAuth.signOut();
        finish();
        startActivity(new Intent(HomeActivity.this, LoginActivity.class));
    }



//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        getMenuInflater().inflate(R.menu.menu, menu);
//        return true;
//    }

//    @Override
//    public boolean onOptionsItemSelected(MenuItem item) {
//
//        switch(item.getItemId()) {
//            case R.id.profileMenu:
//                startActivity(new Intent(HomeActivity.this, ProfileActivity.class));
//                break;
//            case R.id.changePassMenu:
//                startActivity(new Intent(HomeActivity.this , ChangePwdActivity.class));
//                break;
//
//            case R.id.logoutMenu:
//                Logout();
//                break;
//        }
//        return super.onOptionsItemSelected(item);
//    }

}