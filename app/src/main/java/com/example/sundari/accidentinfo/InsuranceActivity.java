package com.example.sundari.accidentinfo;


import android.content.Intent;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class InsuranceActivity extends AppCompatActivity {

    private ListView listView;

    String insuranceCompany[] = new String[] {"State Farm Group" , "Berkshire Hathaway Ins",
            "Progressive Ins Group" , "Allstate Ins Group" , "Liberty Mutual Ins Cos"};


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_insurance);

        listView = findViewById(R.id.lv_InsuCompany);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this , android.R.layout.simple_list_item_1 , insuranceCompany);

        listView.setAdapter(arrayAdapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent(InsuranceActivity.this , InsuranceInfoActivity.class);
                intent.putExtra("InsuranceCompany" , insuranceCompany[i]);
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