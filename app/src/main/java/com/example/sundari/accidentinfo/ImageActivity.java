package com.example.sundari.accidentinfo;


import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class ImageActivity extends AppCompatActivity {

    private RecyclerView rv_Images;
    private DatabaseReference myRef;
    private ImageAdapter imageAdapter;
    private AlertDialog.Builder builder;
    private AlertDialog dialog;

    private ArrayList<String> mUpload;

    String fileName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image);

        rv_Images = findViewById(R.id.image_recycler_view);
        rv_Images.setHasFixedSize(true);
        rv_Images.setLayoutManager(new LinearLayoutManager(this));

        builder = new AlertDialog.Builder(ImageActivity.this);
        builder.setCancelable(false);
        builder.setView(R.layout.progress_layout);
        dialog = builder.create();
        dialog.show();

        Bundle bundle = getIntent().getExtras();
        if (bundle != null)
            fileName = bundle.getString("fileName");

        myRef = FirebaseDatabase.getInstance().getReference("files/" + fileName);

        mUpload = new ArrayList<>();

        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                for (DataSnapshot ds : dataSnapshot.getChildren()){
                    for (DataSnapshot d : ds.getChildren()){
                        if (d.getKey().equals("imageUrl")) {
                            String s = d.getValue(String.class);
                            mUpload.add(s);
                        }
                    }
                }

                if (mUpload.isEmpty())
                    Toast.makeText(ImageActivity.this , "There are no Images of this Incident" , Toast.LENGTH_SHORT).show();

                imageAdapter = new ImageAdapter(ImageActivity.this , mUpload);
                rv_Images.setAdapter(imageAdapter);

                dialog.dismiss();

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(ImageActivity.this , databaseError.getMessage() , Toast.LENGTH_SHORT).show();
                dialog.dismiss();
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
