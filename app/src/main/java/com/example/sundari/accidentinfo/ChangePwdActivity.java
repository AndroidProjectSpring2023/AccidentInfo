package com.example.sundari.accidentinfo;

import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;


import java.util.HashMap;
import java.util.Map;

public class ChangePwdActivity extends AppCompatActivity {

    private EditText newPwd, conPwd, oldPwd;
    private Button SavePwd;
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



        SavePwd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                updatePassword();
            }
        });

    }








}
