package com.admin;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.app.ProgressDialog;
import android.content.ClipData;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.steppin.R;
import com.steppin.TempData;
import com.utils.ConnectionDetector;
import com.utils.MySession;
import com.utils.SetStatusBar;
import com.utils.ToastMsg;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

import gun0912.tedimagepicker.builder.TedImagePicker;
import gun0912.tedimagepicker.builder.listener.OnSelectedListener;

public class Send_Notification extends AppCompatActivity {
    ConnectionDetector cn;
    ImageView img;
    CardView cardSaveNow;
    EditText edTitle, edDetails;
    View vAddImage;
    String sTitle = "", sDetails = "", sImage = "";
    Uri fileUri;

    FirebaseStorage storage;
    StorageReference storageReference;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.send_notification);
        SetStatusBar.set(this, R.color.clr_White);
        ImageView imgLeft = (ImageView) findViewById(R.id.imgLeft);
        ImageView imgRight = (ImageView) findViewById(R.id.imgRight);
        TextView txtTitle = (TextView) findViewById(R.id.txtTitle);
        txtTitle.setText("Send Notification");
        imgLeft.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        cn = new ConnectionDetector(getApplicationContext());
        img = findViewById(R.id.img);
        storage = FirebaseStorage.getInstance();
        storageReference = storage.getReference();
        cardSaveNow = findViewById(R.id.cardSaveNow);
        edTitle = findViewById(R.id.edTitle);
        edDetails = findViewById(R.id.edDetails);
        vAddImage = findViewById(R.id.vAddImage);
        vAddImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (checkPermission()) {
                    chooseImage();
                }
            }
        });
        cardSaveNow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                callSave();
            }
        });
    }

    ProgressDialog progressDialog;

    public void callSave() {
        sTitle = edTitle.getText().toString();
        sDetails = edDetails.getText().toString();
        if (fileUri == null) {
            if (sTitle.equals("")) {
                edTitle.setError("Please enter title");
                edTitle.requestFocus();
                return;
            }
            if (sDetails.equals("")) {
                edDetails.setError("Please enter Details");
                edDetails.requestFocus();
                return;
            }
        }
        if (fileUri != null) {
            progressDialog = new ProgressDialog(this);
            progressDialog.setMessage("Image Uploading...");
            progressDialog.setCancelable(false);
            progressDialog.setCanceledOnTouchOutside(false);
            progressDialog.show();
            uploadImage();
        } else {
            progressDialog = new ProgressDialog(this);
            progressDialog.setMessage("Sending...");
            progressDialog.setCancelable(false);
            progressDialog.setCanceledOnTouchOutside(false);
            progressDialog.show();
            new SendAsync().execute();
        }

    }

    public void uploadImage() {
        if (fileUri != null) {

            String sUID = "" + new Date().getTime();
            StorageReference ref = storageReference.child("images/" + sUID + "_123");

            ref.putFile(fileUri)
                    .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            getDownloadURL(taskSnapshot.getMetadata().getPath());

                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            progressDialog.dismiss();
                            Toast.makeText(Send_Notification.this, "Failed " + e.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    })
                    .addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {
                            double progress = (100.0 * taskSnapshot.getBytesTransferred() / taskSnapshot
                                    .getTotalByteCount());
                        }
                    });
        }


    }

    public void getDownloadURL(String Path) {
            Log.e("downloadUrl", "11-" + Path);
            StorageReference dateRef = storageReference.child(Path);
            dateRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                @Override
                public void onSuccess(Uri downloadUrl) {

                    progressDialog.setMessage("Sending...");
                    Log.e("downloadUrl", "22-" + downloadUrl.toString());
                   sImage = downloadUrl.toString();
                   new SendAsync().execute();
                }
            });


    }

    private class SendAsync extends AsyncTask<Void, Void, Void> {


        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected Void doInBackground(Void... params) {

                String s = NotificationURL.send(sTitle, sDetails, sImage, MySession.ShareNewsNotification, "0", TempData.NotiChannaleClients);
                Log.e("sNoti", "-" + s);


            return null;
        }

        @Override
        protected void onPostExecute(Void args) {
            try {
                if (progressDialog.isShowing()) {
                    progressDialog.dismiss();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            ToastMsg.mToastMsg(getApplicationContext(),"Successfully Send Notification.",1);
            finish();
        }
    }

    public void chooseImage() {
        TedImagePicker.with(this).start(new OnSelectedListener() {
            @Override
            public void onSelected(Uri uri) {
                fileUri = uri;
                try {
                    Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), fileUri);
                    img.setImageBitmap(bitmap);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
//        Intent intent = new Intent();
//        intent.setType("image/*");
//        intent.setAction(Intent.ACTION_GET_CONTENT);
//        startActivityForResult(Intent.createChooser(intent, "Select Picture"), 2);

//        Intent chooseIntent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
//        chooseIntent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true);
//        startActivityForResult(chooseIntent, 2);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 2 && resultCode == RESULT_OK
                && data != null && data.getData() != null) {
            fileUri = data.getData();
            try {
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), fileUri);
                img.setImageBitmap(bitmap);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }

    public boolean checkPermission() {
        if (Build.VERSION.SDK_INT >= 23) {
            boolean isExternalStrage = false;

            try {
                int hasWriteContactsPermission = checkSelfPermission(android.Manifest.permission.WRITE_EXTERNAL_STORAGE);
                if (hasWriteContactsPermission != PackageManager.PERMISSION_GRANTED) {
                    isExternalStrage = false;
                } else {
                    isExternalStrage = true;
                }
            } catch (Exception e) {
                e.printStackTrace();
                isExternalStrage = true;
            }


            if (!isExternalStrage) {
                this.requestPermissions(new String[]{android.Manifest.permission.WRITE_EXTERNAL_STORAGE, android.Manifest.permission.READ_EXTERNAL_STORAGE}, 007);
            }
            if (isExternalStrage) {
                return true;
            } else {
                return false;
            }
        } else {
            return true;
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grants) {
        super.onRequestPermissionsResult(requestCode, permissions, grants);
        checkPermission();
    }
    @Override
    public void finish() {
        super.finish();
        overridePendingTransitionExit();
    }

    @Override
    public void startActivity(Intent intent) {
        super.startActivity(intent);
        overridePendingTransitionEnter();
    }

    protected void overridePendingTransitionEnter() {
        overridePendingTransition(R.anim.slide_from_right, R.anim.slide_to_left);
    }

    protected void overridePendingTransitionExit() {
        overridePendingTransition(R.anim.slide_from_left, R.anim.slide_to_right);
    }
}
