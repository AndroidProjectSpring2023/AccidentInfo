package com.example.sundari.accidentinfo;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class RegistrationActivity extends AppCompatActivity {

    private TextView textView , login;
    private RadioGroup radioGroup;
    private RadioButton radioButton;
    private EditText name , empID , email , phone , password , region;
    private Button register;
    private AlertDialog.Builder builder;
    private AlertDialog dialog;

    private FirebaseAuth firebaseAuth;
    private FirebaseDatabase firebaseDatabase;
    String cemail, cname, pwd, cempID, cphone, cregion, coccupation;


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

        firebaseAuth = FirebaseAuth.getInstance();

        builder = new AlertDialog.Builder(RegistrationActivity.this);
        builder.setCancelable(false);
        builder.setView(R.layout.progress_layout);
        dialog = builder.create();

        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.show();
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
                if (checked) {
                    empID.setEnabled(true);
                    region.setHint("COP's County Address");
                }
                break;
            case R.id.radio_ambulance:
                if (checked) {
                    empID.setEnabled(true);
                    region.setHint("Hospital Address");
                }
                break;
            case R.id.radio_Volunteer:
                if (checked) {
                    empID.setEnabled(false);
                    empID.setText("");
                    region.setHint("Address");
                }
                break;
        }
    }


    private void register() {
        if (validate()) {
            //Upload data to the database
            String user_email = email.getText().toString().trim();
            String user_password = password.getText().toString().trim();
            firebaseAuth.createUserWithEmailAndPassword(user_email , user_password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if (task.isSuccessful()) {
                        sendEmailVerification();

                    } else {

                        Toast.makeText(RegistrationActivity.this,task.getException().getMessage() , Toast.LENGTH_SHORT).show(); }

                }
            });
        }
        else
            Toast.makeText(this, "Please enter all the details", Toast.LENGTH_SHORT).show();

    }


    private Boolean validate() {
        Boolean flag = false;

        cname = name.getText().toString().trim();
        cempID = empID.getText().toString().trim();
        pwd = password.getText().toString().trim();
        cemail = email.getText().toString().trim();
        cphone = phone.getText().toString().trim();
        cregion = region.getText().toString().trim();
        coccupation = radioButton.getText().toString().trim();


        if (cname.isEmpty() || pwd.isEmpty() || cemail.isEmpty() || coccupation.equals("Volunteer")?false:(cempID.isEmpty()?true:false)
                || cphone.isEmpty() || coccupation.isEmpty())
        {
            dialog.dismiss();
            Toast.makeText(this, "Please enter all the details", Toast.LENGTH_SHORT).show();
        }
        else if (cempID.isEmpty()){
            cempID = "-1";
            flag = true;
        }
        else {
            flag = true;
        }
        return flag;
    }


    private void sendEmailVerification() {
        FirebaseUser firebaseUser = firebaseAuth.getCurrentUser();
        if (firebaseUser != null) {
            firebaseUser.sendEmailVerification().addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    if (task.isSuccessful()) {
                        dialog.dismiss();
                        sendUserData();
                        Toast.makeText(RegistrationActivity.this, "Successfully Registered, Verification mail sent!", Toast.LENGTH_SHORT).show();
                        firebaseAuth.signOut();
                        finish();
                        startActivity(new Intent(RegistrationActivity.this, LoginActivity.class));
                    } else {
                        dialog.dismiss();
                        Toast.makeText(RegistrationActivity.this, "Verification mail has not been sent!", Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }
    }

    private void sendUserData() {
        firebaseDatabase = FirebaseDatabase.getInstance();
        DatabaseReference myRef = firebaseDatabase.getReference("User").child(firebaseAuth.getUid());
        UserProfile userProfile = new UserProfile(cname, cempID, cemail, cphone, coccupation, cregion, pwd);
        myRef.setValue(userProfile);
        Toast.makeText(RegistrationActivity.this, "Data Inserted Successfully",Toast.LENGTH_SHORT).show();

    }

}
