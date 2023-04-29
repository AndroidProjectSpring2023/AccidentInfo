package com.example.sundari.accidentinfo;


import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class InsuranceInfoActivity extends AppCompatActivity {

    private ListView listView;

    private DatabaseReference databaseReference;
    private AlertDialog.Builder builder;
    private AlertDialog dialog;
    private Intent i;
    private boolean status;

    private ArrayList<String> fileNames;

    String InsuranceCmp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_insurance_info);

        listView = findViewById(R.id.lv_insuFiles);
        i = getIntent();
        status = i.getBooleanExtra("isVictimReportViewingByAgent",false);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        builder = new AlertDialog.Builder(InsuranceInfoActivity.this);
        builder.setCancelable(false);
        builder.setView(R.layout.progress_layout);
        dialog = builder.create();
        dialog.show();

        Bundle bundle = getIntent().getExtras();
        if (bundle != null)
            InsuranceCmp = bundle.getString("InsuranceCompany");

        databaseReference = FirebaseDatabase.getInstance().getReference("information");
        fileNames = new ArrayList<>();
        final ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, fileNames);

        databaseReference.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                Accident_Info info = dataSnapshot.getValue(Accident_Info.class);
                if (info != null) {
                    if (InsuranceCmp.equals(info.getIncuranceCompany())) {
                        fileNames.add(dataSnapshot.getKey());
                        adapter.notifyDataSetChanged();
                    }
                }
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(InsuranceInfoActivity.this , databaseError.toString() , Toast.LENGTH_SHORT).show();
            }
        });

        listView.setAdapter(adapter);
        dialog.dismiss();

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                Intent intent = new Intent(InsuranceInfoActivity.this , InformationActivity.class);
                intent.putExtra("fileName" , listView.getItemAtPosition(i).toString());
                intent.putExtra("isVictimReportViewingByAgent", status);
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

