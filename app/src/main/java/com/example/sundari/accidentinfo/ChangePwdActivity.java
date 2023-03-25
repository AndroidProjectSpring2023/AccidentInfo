package com.example.sundari.accidentinfo;

import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;
import java.util.Map;

public class ChangePwdActivity extends AppCompatActivity {

    private EditText newPwd, conPwd, oldPwd;
    private Button SavePwd;
    private FirebaseUser firebaseUser;
    private DatabaseReference databaseReference;
    static String NewPwd, ConPwd, OldPwd, CurrentPwd;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_pwd);


        newPwd = findViewById(R.id.etNewPwd);
        conPwd = findViewById(R.id.et_ConPwd);
        oldPwd = findViewById(R.id.etOldPwd);
        SavePwd =findViewById(R.id.btnSavePwd);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        databaseReference = FirebaseDatabase.getInstance().getReference();


        databaseReference.child("User").child(firebaseUser.getUid()).addListenerForSingleValueEvent(
                new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        UserProfile user = dataSnapshot.getValue(UserProfile.class);
                        assert user != null;
                        CurrentPwd = user.getUserPassword();
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                }
        );

        SavePwd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                updatePassword();
            }
        });

    }


    private void updatePassword() {
        if (validate()) {
            final String password = newPwd.getText().toString().trim();
            firebaseUser.updatePassword(password).addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    if (task.isSuccessful()) {

                        databaseReference.child("User").child(firebaseUser.getUid()).addListenerForSingleValueEvent(
                                new ValueEventListener() {
                                    @Override
                                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                        UserProfile user = dataSnapshot.getValue(UserProfile.class);

                                        updateUser(user.userName, user.userEmpID, user.userEmail, user.userPhone, user.userOccupation, user.userRegion, NewPwd);

                                    }

                                    @Override
                                    public void onCancelled(@NonNull DatabaseError databaseError) {
                                        Toast.makeText(ChangePwdActivity.this, "Failed to changed the Password in database", Toast.LENGTH_SHORT).show();
                                    }
                                }
                        );

                        Toast.makeText(ChangePwdActivity.this, "Successfully changed the Password", Toast.LENGTH_SHORT).show();
                        finish();
                    } else {
                        Toast.makeText(ChangePwdActivity.this, "Failed to change password", Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }
    }


    private Boolean validate() {

        boolean flag = false;

        NewPwd = newPwd.getText().toString().trim();
        ConPwd = conPwd.getText().toString().trim();
        OldPwd = oldPwd.getText().toString().trim();

        if (NewPwd.isEmpty() || ConPwd.isEmpty() || OldPwd.isEmpty())
            Toast.makeText(ChangePwdActivity.this, "Please enter the password", Toast.LENGTH_SHORT).show();

        else if (!CurrentPwd.equals(OldPwd))
            Toast.makeText(ChangePwdActivity.this, "Enter the current password Correctly", Toast.LENGTH_SHORT).show();

        else if (!NewPwd.equals(ConPwd))
            Toast.makeText(ChangePwdActivity.this, "Re-enter the password Correctly", Toast.LENGTH_SHORT).show();

        else
            flag = true;

        return flag;
    }



    private void updateUser(String userName, String userEmpID, String userEmail, String userPhone, String userOccupation, String userRegion, String userPassword) {

        String key = databaseReference.child("User").child(firebaseUser.getUid()).getKey();
        UserProfile userProfile = new UserProfile(userName, userEmpID, userEmail, userPhone, userOccupation, userRegion, userPassword);
        Map<String, Object> updateValues = userProfile.toMap();

        Map<String, Object> childUpdates = new HashMap<>();
        childUpdates.put("/User/" + key, updateValues);

        databaseReference.updateChildren(childUpdates);
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
