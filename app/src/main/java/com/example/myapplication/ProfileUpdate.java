package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.example.myapplication.model.UpateprofileVerifyModel;
import com.example.myapplication.utils.LoaderDialog;
import com.example.myapplication.webservice.RetrofitClientInstance;
import com.example.myapplication.webservice.RetrofitInterface;

import java.io.File;
import java.util.Objects;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ProfileUpdate extends AppCompatActivity {
    ImageView backbtn;
    EditText phnedt, firstnameedt, lastnameedt;
    Button btnsubmit;
    RelativeLayout imagerl;
    ImageView imageView;
    String ph_num, firstname, lastname;
    public static final int PICK_IMAGE = 100;
    MultipartBody.Part fileProf_Image = null;
    LoaderDialog loaderDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_update);
        initViews();
        Intent intent = getIntent();
        ph_num = intent.getStringExtra("phn");
        phnedt.setText(ph_num);
        backbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        imagerl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(intent, "Select Image"), PICK_IMAGE);
            }
        });
        btnsubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!TextUtils.isEmpty(firstname)) {

                } else if (!TextUtils.isEmpty(lastname)){

                }else if (!isValidMobile(ph_num)){

                }else if (fileProf_Image != null){
                    registerUser(firstname+lastname,ph_num);

                }
                else {
                    Toast.makeText(ProfileUpdate.this, "Please enter valid details", Toast.LENGTH_SHORT).show();
                }



            }
        });


    }
    private boolean isValidMobile(String phone) {
        if (phone.length() == 10) {
            return android.util.Patterns.PHONE.matcher(phone).matches();
        } else {
            return false;
        }
    }
    private void initViews() {
        loaderDialog = new LoaderDialog(ProfileUpdate.this);
        backbtn = findViewById(R.id.back);
        phnedt = findViewById(R.id.phno_edt);
        firstnameedt = findViewById(R.id.edt_first_name);
        lastnameedt = findViewById(R.id.edt_last_name);
        btnsubmit = findViewById(R.id.btn_submit);
        imageView = findViewById(R.id.imageview);
        imagerl = findViewById(R.id.imgrl);

    }

    private void registerUser(String fullnam, String ph_num) {
        loaderDialog.show();
        RequestBody mPhone = RequestBody.create(MediaType.parse("text/plain"), ph_num);
        RequestBody fullname = RequestBody.create(MediaType.parse("text/plain"), fullnam);
//        String latit = String.valueOf(RequestBody.create(MediaType.parse("text/plain"), lat));
//        String longit = String.valueOf(RequestBody.create(MediaType.parse("text/plain"), lon));


        RetrofitInterface service = RetrofitClientInstance.getRetrofitInstance().create(RetrofitInterface.class);
        Call<UpateprofileVerifyModel> calls = service.updateProfileInfo("dXBkYXRlLXByb2ZpbGU=",
                mPhone, fileProf_Image, fullname);


        calls.enqueue(new Callback<UpateprofileVerifyModel>() {
            @Override
            public void onResponse(Call<UpateprofileVerifyModel> call, Response<UpateprofileVerifyModel> response) {
                loaderDialog.dismiss();

                if (response.isSuccessful()) {
                    if (!response.body().getStatus().equals("Error")) {
                        Toast.makeText(getApplicationContext(), response.body().getMsg(), Toast.LENGTH_LONG).show();

                    } else {
                        Toast.makeText(getApplicationContext(), response.body().getMsg(), Toast.LENGTH_LONG).show();
                    }
                } else {
                    Toast.makeText(getApplicationContext(), "Something went wrong", Toast.LENGTH_LONG).show();
                }

            }

            @Override
            public void onFailure(Call<UpateprofileVerifyModel> call, Throwable t) {
                loaderDialog.dismiss();

                Toast.makeText(getApplicationContext(), t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });


    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PICK_IMAGE && resultCode == Activity.RESULT_OK) {
            try {
                // Get the cursor
                android.net.Uri selectedImage = data.getData();
                imageView.setImageURI(selectedImage);
                String[] filePathColumn = {MediaStore.Images.Media.DATA};
                android.database.Cursor cursor = getContentResolver().query(selectedImage, filePathColumn, null, null, null);
                if (cursor == null)
                    return;

                cursor.moveToFirst();

                int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
                String filePath = cursor.getString(columnIndex);
                cursor.close();

                File file = new File(filePath);
                RequestBody reqFile = RequestBody.create(MediaType.parse("image/*"), file);
                fileProf_Image = MultipartBody.Part.createFormData("upload", file.getName(), reqFile);

                Log.d("THIS", data.getData().getPath());

            } catch (NullPointerException e) {
                e.printStackTrace();
            }



        }
    }

}