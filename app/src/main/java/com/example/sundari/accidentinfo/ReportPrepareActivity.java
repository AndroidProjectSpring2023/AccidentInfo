package com.example.sundari.accidentinfo;

import android.app.ProgressDialog;
import android.content.ContentResolver;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.view.MenuItem;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;


import java.util.Random;

public class ReportPrepareActivity extends AppCompatActivity {
    private static final int PICK_IMAGE_REQUEST = 1;
    private static final int PICK_VIDEO_REQUEST = 2;
    private ProgressBar mProgressBar;
    private Button chooseImg, chooseVideo, uploadImages, uploadVideos, submit;
    private ImageView imageView;
    private EditText et_Name, et_Age ,et_injuries, et_reason, et_policyNO, et_location;
    private RadioGroup radioGroup;
    private RadioButton radioButton;
    private TextView textView;
    private Uri mImageUri , mVideoUri;

    long imgName;
    String vName, vAge,injuries, reason, policyNO, location, insuCompany;
    private int fileName = randomNum();
    private int randomNum(){
        Random random = new Random();
        int nextInt = random.nextInt(100000);
        return nextInt;
    }
    @Override    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_report_prepare);
        mProgressBar = findViewById(R.id.progress_Bar);
        chooseImg = findViewById(R.id.btn_choose_Image);
        chooseVideo = findViewById(R.id.btn_choose_Video);
        imageView = findViewById(R.id.img_view);
        uploadImages = findViewById(R.id.btn_uploadImages);
        uploadVideos = findViewById(R.id.btn_uploadVideos);
        submit = findViewById(R.id.btn_submitData);
        et_Name = findViewById(R.id.et_VictimName);
        et_Age = findViewById(R.id.et_VictimAge);
        et_injuries = findViewById(R.id.et_Injuries);
        et_reason = findViewById(R.id.et_Reason);
        et_location = findViewById(R.id.et_location);
        et_policyNO = findViewById(R.id.et_Policy);
        radioGroup = findViewById(R.id.radio_InsuCompany);
        radioButton = findViewById(R.id.radio_NoInsu);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        et_policyNO.setEnabled(false);
        chooseImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                openFileChooserImg();
            }
        });
        uploadImages.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                if (mUploadTask != null && mUploadTask.isInProgress()) {
//                    Toast.makeText(ReportPrepareActivity.this, "Upload in progress", Toast.LENGTH_SHORT).show();
//                } else {
//                    uploadImg();
//                }
            }
        });
        chooseVideo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                openFileChooserVideo();
            }
        });
        uploadVideos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                if (mUploadTask != null && mUploadTask.isInProgress()) {
//                    Toast.makeText(ReportPrepareActivity.this, "Upload in progress", Toast.LENGTH_SHORT).show();
//                } else {
//                    uploadVideo();
//                }
            }
        });
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                radioButton = findViewById(radioGroup.getCheckedRadioButtonId());
//                uploadData();
            }
        });
    }

}