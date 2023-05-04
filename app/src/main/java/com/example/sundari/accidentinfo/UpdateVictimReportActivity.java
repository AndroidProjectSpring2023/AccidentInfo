package com.example.sundari.accidentinfo;

        import android.app.Activity;
        import android.app.AlertDialog;
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
        import android.widget.Toast;

        import androidx.activity.result.ActivityResult;
        import androidx.activity.result.ActivityResultCallback;
        import androidx.activity.result.ActivityResultLauncher;
        import androidx.activity.result.contract.ActivityResultContracts;
        import androidx.annotation.NonNull;
        import androidx.appcompat.app.AppCompatActivity;

        import com.bumptech.glide.Glide;
        import com.google.android.gms.tasks.OnCompleteListener;
        import com.google.android.gms.tasks.OnFailureListener;
        import com.google.android.gms.tasks.OnSuccessListener;
        import com.google.android.gms.tasks.Task;
        import com.google.firebase.database.DataSnapshot;
        import com.google.firebase.database.DatabaseError;
        import com.google.firebase.database.DatabaseReference;
        import com.google.firebase.database.FirebaseDatabase;
        import com.google.firebase.database.ValueEventListener;
        import com.google.firebase.storage.FirebaseStorage;
        import com.google.firebase.storage.OnProgressListener;
        import com.google.firebase.storage.StorageReference;
        import com.google.firebase.storage.StorageTask;
        import com.google.firebase.storage.UploadTask;

        import java.util.Random;
public class UpdateVictimReportActivity extends AppCompatActivity {
    private static int PICK_IMAGE_REQUEST = 1;
    private static final int PICK_VIDEO_REQUEST = 2;
    private ProgressBar mProgressBar;
    private Button chooseVictimImage, chooseImg, chooseVideo, uploadImages, uploadVideos, submit;
    private ImageView imageView, victimImageView;
    private EditText et_Name, et_Age ,et_injuries, et_reason, et_policyNO, et_location;
    private RadioGroup radioGroup;
    private RadioButton radioButton;
    private Uri mImageUri , mVideoUri, victimImageUri;
    private StorageReference mStorageRef;
    private DatabaseReference mDatabaseRef ,databaseReferenceX, myRef;
    private boolean status = false;
    String victimImageURL;
    AlertDialog.Builder builder;
    AlertDialog dialog;


    private StorageTask mUploadTask;
    long imgName;
    String vName, vAge,injuries, reason, policyNO, location, insuCompany;
    private int fileName ;



    @Override    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_victim_report);
        mProgressBar = findViewById(R.id.progress_Bar);

        chooseImg = findViewById(R.id.btn_choose_Image);
        chooseVideo = findViewById(R.id.btn_choose_Video);
        chooseVictimImage = findViewById(R.id.btn_chooseVictimImage);

        imageView = findViewById(R.id.img_view);
        victimImageView = findViewById(R.id.victim_image_view);

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

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        mStorageRef = FirebaseStorage.getInstance().getReference("files");
        et_policyNO.setEnabled(false);

        builder = new AlertDialog.Builder(UpdateVictimReportActivity.this);
        builder.setCancelable(false);
        builder.setView(R.layout.progress_layout);
        dialog = builder.create();

        Bundle bundle = getIntent().getExtras();

        int fileNameX = Integer.parseInt(bundle.getString("fileName"));

        fileName = fileNameX;

        databaseReferenceX = FirebaseDatabase.getInstance().getReference("information/" + fileNameX);

        databaseReferenceX.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                Accident_Info info = dataSnapshot.getValue(Accident_Info.class);
                if (info != null){
                    Glide.with(UpdateVictimReportActivity.this).load(info.getvImage()).into(victimImageView);
                    et_Name.append(info.getvName());
                    et_Age.append(info.getvAge());
                    et_reason.append(info.getReason());
                    et_location.append(info.getLocation());
                    et_injuries.append(info.getInjuries());
                    String selectedInsuranceCompany = info.getIncuranceCompany();
                    victimImageURL = info.getvImage();

                    if(selectedInsuranceCompany.equals("Not have an Insurance")){
                        radioGroup.check(R.id.radio_NoInsu);
                        et_policyNO.setText(" ");
                    } else if(selectedInsuranceCompany.equals("State Farm Group")){
                        radioGroup.check(R.id.radio_lic);
                        et_policyNO.setEnabled(true);
                        et_policyNO.setText(info.getPolicy_No());
                    } else if(selectedInsuranceCompany.equals("Berkshire Hathaway Ins")){
                        radioGroup.check(R.id.radio_icici);
                        et_policyNO.setEnabled(true);
                        et_policyNO.setText(info.getPolicy_No());
                    } else if(selectedInsuranceCompany.equals("Progressive Ins Group")){
                        radioGroup.check(R.id.radio_sbi);
                        et_policyNO.setEnabled(true);
                        et_policyNO.setText(info.getPolicy_No());
                    } else if(selectedInsuranceCompany.equals("Allstate Ins Group")){
                        radioGroup.check(R.id.radio_birla);
                        et_policyNO.setEnabled(true);
                        et_policyNO.setText(info.getPolicy_No());
                    } else if(selectedInsuranceCompany.equals("Liberty Mutual Ins Cos")){
                        radioGroup.check(R.id.radio_hdfc);
                        et_policyNO.setEnabled(true);
                        et_policyNO.setText(info.getPolicy_No());
                    }
                }
                dialog.dismiss();
            }


            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(UpdateVictimReportActivity.this , databaseError.getMessage() , Toast.LENGTH_SHORT).show();
                dialog.dismiss();
            }
        });

        ActivityResultLauncher<Intent> activityResultLauncher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                new ActivityResultCallback<ActivityResult>() {
                    @Override
                    public void onActivityResult(ActivityResult result) {
                        if (result.getResultCode() == Activity.RESULT_OK){
                            Intent data = result.getData();
                            victimImageUri = data.getData();
                            victimImageView.setImageURI(victimImageUri);
                            status = true;
                        } else {
                            Toast.makeText(UpdateVictimReportActivity.this, "No Victim Image Selected", Toast.LENGTH_SHORT).show();
                        }
                    }
                }
        );

        chooseVictimImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent photoPicker = new Intent(Intent.ACTION_PICK);
                photoPicker.setType("image/*");
                activityResultLauncher.launch(photoPicker);
            }
        });


        chooseImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openFileChooserImg();
            }
        });

        chooseVideo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openFileChooserVideo();
            }
        });

        uploadImages.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mUploadTask != null && mUploadTask.isInProgress()) {
                    Toast.makeText(UpdateVictimReportActivity.this, "Upload in progress", Toast.LENGTH_SHORT).show();
                } else {
                    uploadImg();
                }
            }
        });

        uploadVideos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mUploadTask != null && mUploadTask.isInProgress()) {
                    Toast.makeText(UpdateVictimReportActivity.this, "Upload in progress", Toast.LENGTH_SHORT).show();
                } else {
                    uploadVideo();
                }
            }
        });

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                radioButton = findViewById(radioGroup.getCheckedRadioButtonId());
                dialog.show();
                saveVictimData();
            }
        });
    }
    private void openFileChooserImg() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.putExtra("requestCode", PICK_IMAGE_REQUEST);
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent, PICK_IMAGE_REQUEST);
    }
    private void openFileChooserVideo() {
        Intent intent = new Intent();
        intent.setType("video/*");
        intent.putExtra("requestCode", PICK_VIDEO_REQUEST);
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent, PICK_VIDEO_REQUEST);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK  && data != null && data.getData() != null) {
            mImageUri = data.getData();
            imageView.setImageURI(mImageUri);
        }
        if (requestCode == PICK_VIDEO_REQUEST && resultCode == RESULT_OK   && data != null && data.getData() != null) {
            mVideoUri = data.getData();
        }
    }
    private String getFileExtension(Uri uri) {
        ContentResolver cR = getContentResolver();
        MimeTypeMap mime = MimeTypeMap.getSingleton();
        return mime.getExtensionFromMimeType(cR.getType(uri));
    }
    private void uploadImg() {
        imgName = System.currentTimeMillis() % 100000 ;
        if (mImageUri != null) {
            final StorageReference fileReference = mStorageRef.child(fileName + "/" + imgName +"." + getFileExtension(mImageUri) );
            mUploadTask = fileReference.putFile(mImageUri)
                    .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            Handler handler = new Handler();
                            handler.postDelayed(new Runnable() {
                                @Override
                                public void run() {
                                    mProgressBar.setProgress(0);
                                }
                            }, 500);
                            Toast.makeText(UpdateVictimReportActivity.this, "Upload successful", Toast.LENGTH_LONG).show();
                            mDatabaseRef = FirebaseDatabase.getInstance().getReference("files/" + fileName + "/" + imgName);
                            fileReference.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                @Override
                                public void onSuccess(Uri uri) {
                                    Upload upload = new Upload( uri.toString() );
                                    mDatabaseRef.setValue(upload);
                                }
                            });
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(UpdateVictimReportActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    })
                    .addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {
                            double progress = (100.0 * taskSnapshot.getBytesTransferred() / taskSnapshot.getTotalByteCount());
                            mProgressBar.setProgress((int) progress);
                        }
                    });
        } else {
            Toast.makeText(this, "No file selected", Toast.LENGTH_SHORT).show();
        }
    }
    private void uploadVideo() {
        imgName = System.currentTimeMillis() % 100000 ;
        if (mVideoUri != null) {
            final StorageReference fileReference = mStorageRef.child(fileName                    + "/" + imgName +"." + getFileExtension(mVideoUri) );
            mUploadTask = fileReference.putFile(mVideoUri)
                    .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            Handler handler = new Handler();
                            handler.postDelayed(new Runnable() {
                                @Override
                                public void run() {
                                    mProgressBar.setProgress(0);
                                }
                            }, 500);
                            Toast.makeText(UpdateVictimReportActivity.this, "Upload successful", Toast.LENGTH_LONG).show();
                            mDatabaseRef = FirebaseDatabase.getInstance().getReference("files/" + fileName + "/" + imgName);
                            fileReference.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                @Override
                                public void onSuccess(Uri uri) {
                                    Upload upload = new Upload(uri.toString() , imgName);
                                    mDatabaseRef.setValue(upload);
                                }
                            });
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(UpdateVictimReportActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    })
                    .addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {
                            double progress = (100.0 * taskSnapshot.getBytesTransferred() / taskSnapshot.getTotalByteCount());
                            mProgressBar.setProgress((int) progress);
                        }
                    });
        } else {
            Toast.makeText(this, "No file selected", Toast.LENGTH_SHORT).show();
        }
    }
    public void onRadioButtonClicked(View view) {
        // Is the button now checked?
        boolean checked = ((RadioButton) view).isChecked();
        // Check which radio button was clicked
        switch (view.getId()) {
            case R.id.radio_lic:
                if (checked) {
                    et_policyNO.setEnabled(true);
                    break;
                }
            case R.id.radio_icici:
                if (checked){
                    et_policyNO.setEnabled(true);   break;
                }
            case R.id.radio_sbi:
                if (checked) {
                    et_policyNO.setEnabled(true);    break;
                }
            case R.id.radio_birla:
                if (checked){
                    et_policyNO.setEnabled(true);     break;
                }
            case R.id.radio_hdfc:
                if (checked) {
                    et_policyNO.setEnabled(true);
                    break;
                }
            case R.id.radio_NoInsu:
                if (checked) {
                    et_policyNO.setEnabled(false);
                    break;
                }
        }
    }
    public void saveVictimData(){

        StorageReference storageReference = FirebaseStorage.getInstance().getReference().child("Android Images")
                .child(victimImageUri.getLastPathSegment());
        storageReference.putFile(victimImageUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                Task<Uri> uriTask = taskSnapshot.getStorage().getDownloadUrl();
                if(status){
                    while (!uriTask.isComplete());
                    Uri urlImage = uriTask.getResult();
                    victimImageURL = urlImage.toString();
                    dialog.show();
                    uploadData();
                    dialog.dismiss();
                } else {
                    dialog.show();
                    uploadData();
                    dialog.dismiss();
                }
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                dialog.dismiss();
            }
        });
    }
    private void uploadData() {
        if (validate()) {
            //Upload data to the database
            myRef = FirebaseDatabase.getInstance().getReference("information/" + fileName);
            Accident_Info info = new Accident_Info(vName , vAge , reason , location , injuries , insuCompany , policyNO , fileName, victimImageURL );
            myRef.setValue(info).addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    if (task.isSuccessful()){

                        Toast.makeText(UpdateVictimReportActivity.this , "Successfully Submitted the Information" , Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(UpdateVictimReportActivity.this , InformationActivity.class);
//                        intent.putExtra("fileName", fileName);
                        startActivity(intent);
//                        onBackPressed();
                    }
                    else
                        Toast.makeText(UpdateVictimReportActivity.this , "Failed to submit the Information" , Toast.LENGTH_SHORT).show();
                }
            });
//            myRef.addValueEventListener(new ValueEventListener() {
//                @Override
//                public void onDataChange(@NonNull DataSnapshot snapshot) {
//                    dataList.clear();
//                    for (DataSnapshot itemSnapshot: snapshot.getChildren()){
//                        DataClass dataClass = itemSnapshot.getValue(DataClass.class);
//                        dataClass.setKey(itemSnapshot.getKey());
//                        dataList.add(dataClass);
//                    }
//                    adapter.notifyDataSetChanged();
//                    dialog.dismiss();
//                }
//                @Override
//                public void onCancelled(@NonNull DatabaseError error) {
//                    dialog.dismiss();
//                }
//            });
        }
    }
    private boolean validate() {

        Boolean flag = false;
        vName = et_Name.getText().toString().trim();
        vAge = et_Age.getText().toString().trim();
        reason = et_reason.getText().toString().trim();
        location = et_location.getText().toString().trim();
        injuries = et_injuries.getText().toString().trim();
        policyNO = et_policyNO.getText().toString().trim();
        insuCompany = radioButton.getText().toString().trim();
        if(victimImageURL.isEmpty()){
            dialog.dismiss();
            Toast.makeText(this, "Please upload victim image", Toast.LENGTH_SHORT).show();

        } else if (vName.isEmpty() || vAge.isEmpty() || reason.isEmpty() || location.isEmpty() || injuries.isEmpty()) {
            dialog.dismiss();
            Toast.makeText(this, "Please enter the required details", Toast.LENGTH_SHORT).show();
        }
        else if (policyNO.isEmpty() || insuCompany.isEmpty()){
            policyNO = "Not have an Insurance";
            insuCompany = "Not have an Insurance";
            flag = true;
        }
        else {
            flag = true;
        }
        return flag;
    }
    @Override    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home :
                onBackPressed();
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}