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

public class VideoActivity extends AppCompatActivity {

    private RecyclerView rv_Videos;
    private DatabaseReference myRef;
    private VideoAdapter videoAdapter;
    private ArrayList<String> mUpload;
    private AlertDialog.Builder builder;
    private AlertDialog dialog;


    String fileName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video);

        rv_Videos = findViewById(R.id.video_recycler_view);
        rv_Videos.setHasFixedSize(true);
        rv_Videos.setLayoutManager(new LinearLayoutManager(this));

        builder = new AlertDialog.Builder(VideoActivity.this);
        builder.setCancelable(false);
        builder.setView(R.layout.progress_layout);
        dialog = builder.create();
        dialog.show();

        Bundle bundle = getIntent().getExtras();
        if (bundle != null)
            fileName = bundle.getString("fileName");

        myRef = FirebaseDatabase.getInstance().getReference("files/" + fileName);

        mUpload = new ArrayList<>();


    }

    @Override
    protected void onStart() {
        super.onStart();
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                for (DataSnapshot ds : dataSnapshot.getChildren()){
                    for (DataSnapshot d : ds.getChildren()){
                        if (d.getKey().equals("videoUrl")) {
                            String s = d.getValue(String.class);
                            mUpload.add(s);
                        }
                    }
                }

                if (mUpload.isEmpty())
                    Toast.makeText(VideoActivity.this , "There are no Video's of this Incident" , Toast.LENGTH_SHORT).show();

                videoAdapter = new VideoAdapter(VideoActivity.this , mUpload);
                rv_Videos.setAdapter(videoAdapter);

                dialog.dismiss();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(VideoActivity.this , databaseError.toString() , Toast.LENGTH_SHORT).show();
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
