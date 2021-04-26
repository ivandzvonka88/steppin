package com.admin;

import android.app.ProgressDialog;
import android.content.ClipData;
import android.content.Intent;
import android.content.pm.PackageManager;
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

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.steppin.BuildConfig;
import com.steppin.R;
import com.steppin.TempData;
import com.utils.ConnectionDetector;
import com.utils.MyAsync;
import com.utils.MyGridview;
import com.utils.MyKeyboard;
import com.utils.MySession;
import com.utils.SetStatusBar;
import com.utils.ToastMsg;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import gun0912.tedimagepicker.builder.TedImagePicker;
import gun0912.tedimagepicker.builder.listener.OnMultiSelectedListener;


public class Add_Dance extends AppCompatActivity {
    MyGridview gridView;
    EditText edTitle, edDetails, edMobile, edLocation, edCity;
    View vAddImage, vCity;
    FirebaseStorage storage;
    StorageReference storageReference;
    ArrayList<HashMap<String, Object>> File_Array = new ArrayList<>();
    ArrayList<Uri> URI_Array = new ArrayList<>();
    ArrayList<String> FilePath_Array = new ArrayList<>();
    ArrayList<String> DownloadURL_Array = new ArrayList<>();
    Dance_Image_Grid_Adapter image_grid_adapter;
    String sTitle = "", sDetails = "", sMobile = "", sLocation = "", sCityID = "", sCityName = "", sDateTimeMili = "";
    CardView cardSaveNow;
    ConnectionDetector cn;
    FirebaseFirestore db;
    String sUID = "";
    TextView txtTitle;
    SearchAsync searchAsync;
    boolean isEdit = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_dance);
        SetStatusBar.set(this, R.color.clr_White);
        storage = FirebaseStorage.getInstance();
        storageReference = storage.getReference();
        ImageView imgLeft = (ImageView) findViewById(R.id.imgLeft);
        ImageView imgRight = (ImageView) findViewById(R.id.imgRight);
        txtTitle = (TextView) findViewById(R.id.txtTitle);

        imgLeft.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        cn = new ConnectionDetector(getApplicationContext());
        db = FirebaseFirestore.getInstance();
        cardSaveNow = findViewById(R.id.cardSaveNow);
        gridView = findViewById(R.id.gridView);
        vAddImage = findViewById(R.id.vAddImage);
        edTitle = findViewById(R.id.edTitle);
        edDetails = findViewById(R.id.edDetails);
        edMobile = findViewById(R.id.edMobile);
        edLocation = findViewById(R.id.edLocation);
        edCity = findViewById(R.id.edCity);
        vCity = findViewById(R.id.vCity);
        searchAsync = new SearchAsync();
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
                if (cn.isConnectingToInternet()) {
                    saveNow();
                } else {
                    ToastMsg.mToastMsg(getApplicationContext(), "Please start your internet.", 1);
                }
            }
        });
        vCity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Add_Dance.this, City_List.class);
                startActivityForResult(intent, Admin_Home.danceCode);
            }
        });
        image_grid_adapter = new Dance_Image_Grid_Adapter(Add_Dance.this, File_Array);
        image_grid_adapter.notifyDataSetChanged();
        gridView.setExpanded(true);
        gridView.setAdapter(image_grid_adapter);
        edit();
    }

    public void edit() {
        if (Admin_Dance_Fragment.lastSelectedDance.UID.equals("")) {
            isEdit = false;
            txtTitle.setText("Add Dance");
            sUID = "" + new Date().getTime();
            sDateTimeMili = sUID;
        } else {
            isEdit = true;
            txtTitle.setText("Edit Dance");
            sUID = Admin_Dance_Fragment.lastSelectedDance.UID;
            edTitle.setText(Admin_Dance_Fragment.lastSelectedDance.Title);
            edDetails.setText(Admin_Dance_Fragment.lastSelectedDance.Details);
            edMobile.setText(Admin_Dance_Fragment.lastSelectedDance.Mobile);
            edLocation.setText(Admin_Dance_Fragment.lastSelectedDance.Location);
            sCityID = Admin_Dance_Fragment.lastSelectedDance.CityID;
            sCityName = Admin_Dance_Fragment.lastSelectedDance.CityName;
            edCity.setText(sCityName);
            sDateTimeMili = Admin_Dance_Fragment.lastSelectedDance.DateTime;
            for (int i = 0; i < Admin_Dance_Fragment.lastSelectedDance.Images.size(); i++) {
                String sP = Admin_Dance_Fragment.lastSelectedDance.Images.get(i);
                HashMap<String, Object> map = new HashMap<>();
                map.put("P", sP);
                File_Array.add(map);

            }
            image_grid_adapter.notifyDataSetChanged();
        }

    }

    ProgressDialog progressDialog;
    int current = 0;
    int totalUploadImage = 0;

    public void saveNow() {
        vAddImage.requestFocus();
        MyKeyboard.hide(this);

        URI_Array = new ArrayList<>();
        DownloadURL_Array = new ArrayList<>();
        FilePath_Array = new ArrayList<>();
        sTitle = edTitle.getText().toString();
        sDetails = edDetails.getText().toString();
        sMobile = edMobile.getText().toString();
        sLocation = edLocation.getText().toString();
        for (int i = 0; i < File_Array.size(); i++) {
            String s = File_Array.get(i).get("P").toString();
            if (s.contains("https") || s.contains("http")) {
                DownloadURL_Array.add(s);
            } else {
                Uri sPath = (Uri) File_Array.get(i).get("P");
                URI_Array.add(sPath);
            }
        }
        totalUploadImage = URI_Array.size();
        if (sTitle.equals("")) {
            edTitle.setError("Please Enter Title");
            edTitle.requestFocus();
            return;
        }
        if (sDetails.equals("")) {
            edDetails.setError("Please Enter Details");
            edDetails.requestFocus();
            return;
        }
        if (sMobile.equals("")) {
            edMobile.setError("Please Enter Valid Mobile Number");
            edMobile.requestFocus();
            return;
        }
        if (sCityID.equals("")) {
            ToastMsg.mToastMsg(getApplicationContext(), "Please Select City", 1);
            return;
        }
        if (URI_Array.size() == 0) {
            if (DownloadURL_Array.size() > 0) {
                progressDialog = new ProgressDialog(this);
                progressDialog.setMessage("Uploading...");
                progressDialog.setCancelable(false);
                progressDialog.setCanceledOnTouchOutside(false);
                progressDialog.show();
                storeTOFirebase();
            } else {
                ToastMsg.mToastMsg(getApplicationContext(), "Please Select at least one image.", 1);
                return;
            }
        } else {
            progressDialog = new ProgressDialog(this);
            progressDialog.setMessage("Uploading...");
            progressDialog.setCancelable(false);
            progressDialog.setCanceledOnTouchOutside(false);
            progressDialog.show();
            current = 0;
            progressDialog.setMessage(current + "/" + totalUploadImage + " Image Uploading...");
            uploadImage();
        }

    }

    public void uploadImage() {
        if (URI_Array.size() != 0) {
            Uri uri = URI_Array.get(0);
            if (uri != null) {
                StorageReference ref = storageReference.child("images/" + sUID + "_" + current);
                ref.putFile(uri)
                        .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                            @Override
                            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                                URI_Array.remove(0);
                                current++;
                                progressDialog.setMessage(current + "/" + totalUploadImage + " Image Uploading...");
                                FilePath_Array.add(taskSnapshot.getMetadata().getPath());
                                if (URI_Array.size() > 0) {
                                    uploadImage();
                                } else {
                                    progressDialog.setMessage(DPoss + "/" + totalUploadImage + " Download URL Getting...");
                                    getDownloadURL();
                                }
                            }
                        })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                progressDialog.dismiss();
                                Toast.makeText(Add_Dance.this, "Failed " + e.getMessage(), Toast.LENGTH_SHORT).show();
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

    }

    int DPoss = 0;

    public void getDownloadURL() {
        if (FilePath_Array.size() != 0) {
            StorageReference dateRef = storageReference.child(FilePath_Array.get(0));
            dateRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                @Override
                public void onSuccess(Uri downloadUrl) {
                    FilePath_Array.remove(0);
                    DPoss++;
                    progressDialog.setMessage(DPoss + "/" + totalUploadImage + " Download URL Getting...");
                    DownloadURL_Array.add(downloadUrl.toString());
                    if (FilePath_Array.size() > 0) {
                        getDownloadURL();
                    } else {
                        storeTOFirebase();
                    }
                }
            });
        }

    }

    public void chooseImage() {
        if (File_Array==null){
            File_Array = new ArrayList<>();
        }
        final String sMsg = "Only 10 images allow to upload.";
        if (File_Array.size()<10){
            TedImagePicker.with(this).max(10-File_Array.size(),sMsg).start(new OnMultiSelectedListener() {
                @Override
                public void onSelected(List<? extends Uri> list) {
                    for (int i = 0; i < list.size(); i++) {
                        Uri uri = list.get(i);
                        if (File_Array.size() < 10) {
                            HashMap<String, Object> map = new HashMap<>();
                            map.put("P", uri);
                            File_Array.add(map);
                        } else {
                            ToastMsg.mToastMsg(getApplicationContext(), sMsg, 1);
                            break;
                        }
                    }
                    image_grid_adapter.notifyDataSetChanged();
                }
            });
        }else{
            ToastMsg.mToastMsg(getApplicationContext(), sMsg, 1);
        }

//        Intent intent = new Intent();
//        intent.setType("image/*");
//        intent.setAction(Intent.ACTION_GET_CONTENT);
//        startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE_REQUEST);

//        Intent chooseIntent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
//        chooseIntent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true);
//        startActivityForResult(chooseIntent, 2);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 2 && resultCode == RESULT_OK) {
            ClipData clipData = data.getClipData();
            if (clipData != null) {
                for (int i = 0; i < clipData.getItemCount(); i++) {
                    ClipData.Item item = clipData.getItemAt(i);
                    Uri uri = item.getUri();
                    if (File_Array.size() < 10) {
                        HashMap<String, Object> map = new HashMap<>();
                        map.put("P", uri);
                        File_Array.add(map);
                    } else {
                        ToastMsg.mToastMsg(getApplicationContext(), "Only 10 images allow to upload.", 1);
                        break;
                    }
                }
                image_grid_adapter.notifyDataSetChanged();
            }
        } else if (requestCode == Admin_Home.danceCode && resultCode == RESULT_OK) {
            sCityID = data.getStringExtra(City.Key_UID);
            sCityName = data.getStringExtra(City.Key_CityName);
            edCity.setText(sCityName);
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

    public void deleteImage(int poss) {
        File_Array.remove(poss);
        image_grid_adapter.notifyDataSetChanged();
    }


    public void storeTOFirebase() {
        progressDialog.setMessage("Store Data To Server...");
        Map<String, Object> dance = new HashMap<>();
        dance.put(Dance.Key_UID, sUID);
        dance.put(Dance.Key_Title, sTitle);
        dance.put(Dance.Key_Details, sDetails);
        dance.put(Dance.Key_Location, sLocation);
        dance.put(Dance.Key_Mobile, sMobile);
        dance.put(Dance.Key_CityID, sCityID);
        dance.put(Dance.Key_CityName, sCityName);
        dance.put(Dance.Key_DateTime, sDateTimeMili);
        dance.put(Dance.Key_Images, DownloadURL_Array);
        Admin_Dance_Fragment.lastSelectedDance = new Dance();
        Admin_Dance_Fragment.lastSelectedDance.UID = sUID;
        Admin_Dance_Fragment.lastSelectedDance.Title = sTitle;
        Admin_Dance_Fragment.lastSelectedDance.Details = sDetails;
        Admin_Dance_Fragment.lastSelectedDance.Location = sLocation;
        Admin_Dance_Fragment.lastSelectedDance.Mobile = sMobile;
        Admin_Dance_Fragment.lastSelectedDance.CityID = sCityID;
        Admin_Dance_Fragment.lastSelectedDance.CityName = sCityName;
        Admin_Dance_Fragment.lastSelectedDance.DateTime = sDateTimeMili;
        Admin_Dance_Fragment.lastSelectedDance.Images = DownloadURL_Array;
        Task task = null;
        try {
            task = db.collection(Dance.TableName).document(sUID).set(dance);
        } catch (Exception e) {
            e.printStackTrace();
        }

        task.addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
            @Override
            public void onSuccess(DocumentReference documentReference) {


                callSearch();


            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                try {
                    if (progressDialog.isShowing()) {
                        progressDialog.dismiss();
                    }
                } catch (Exception e2) {
                    e2.printStackTrace();
                }
                ToastMsg.mToastMsg(getApplicationContext(), "" + e.getMessage(), 1);
                Log.e("Error Add", "-" + e.getMessage());
            }
        });


    }


    public void onDestroy() {
        super.onDestroy();
        if (!searchAsync.isCancelled()) {
            searchAsync.cancel(true);
        }
    }

    public void callSearch() {
        if (!searchAsync.isCancelled()) {
            searchAsync.cancel(true);
        }
        searchAsync = new SearchAsync();
        MyAsync.callAsync(searchAsync);

    }

    private class SearchAsync extends AsyncTask<Void, Void, Void> {

        ProgressDialog pd;
        String Result = "";
        ArrayList<Dance> array = new ArrayList<>();

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected Void doInBackground(Void... params) {
            if (isEdit) {
                for (int j = 0; j < Admin_Dance_Fragment.Temp_Array.size(); j++) {
                    Dance dance = Admin_Dance_Fragment.Temp_Array.get(j);
                    if (dance.UID.equals(Admin_Dance_Fragment.lastSelectedDance.UID)) {
                        Admin_Dance_Fragment.Temp_Array.set(j, Admin_Dance_Fragment.lastSelectedDance);
                        break;
                    }
                }
            } else {
                array.add(Admin_Dance_Fragment.lastSelectedDance);
                for (int j = 0; j < Admin_Dance_Fragment.Temp_Array.size(); j++) {
                    array.add(Admin_Dance_Fragment.Temp_Array.get(j));
                }
                Admin_Dance_Fragment.Temp_Array = array;
            }

            if (!isEdit) {
                String s = NotificationURL.send("New Dance", sTitle, "", MySession.ShareDanceNotification, "0", TempData.NotiChannaleClients);
                Log.e("sNoti", "-" + s);
            }
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
            Admin_Dance_Fragment.isRefresh = true;
            if (isEdit) {
                ToastMsg.mToastMsg(getApplicationContext(), "Successfully Edited", 1);
            } else {
                ToastMsg.mToastMsg(getApplicationContext(), "Successfully Added", 1);
            }
            finish();


        }
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
