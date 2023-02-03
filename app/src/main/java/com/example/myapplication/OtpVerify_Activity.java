package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class OtpVerify_Activity extends AppCompatActivity {
    String ph_num, otp;
    EditText otp_edt;
    TextView phone_notv;
    Button btnSubmit;
    ImageView backbtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_otp_verify);
        otp_edt = findViewById(R.id.otp_phno);
        phone_notv = findViewById(R.id.edt_txtphno);
        btnSubmit = findViewById(R.id.btn_req_otp);
        backbtn = findViewById(R.id.back);
        backbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        Intent intent = getIntent();
        ph_num = intent.getStringExtra("phno");
        otp = intent.getStringExtra("otp");
        otp_edt.setText(otp);
        phone_notv.setText("+91 " + ph_num);
        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (validateOtp(otp_edt.getText().toString(), otp)) {

                    Intent i = new Intent(OtpVerify_Activity.this, ProfileUpdate.class);
                    i.putExtra("phn", ph_num);
                    startActivity(i);
                    finish();
                    Toast.makeText(OtpVerify_Activity.this, "Login Succesfully..", Toast.LENGTH_SHORT).show();

                } else {
                    Toast.makeText(OtpVerify_Activity.this, "Invalid Otp...", Toast.LENGTH_SHORT).show();
                }
            }
        });


    }

    private boolean validateOtp(String otp_edt, String otp) {
        if (otp.equals(otp_edt)) {
            return true;
        } else {
            return false;
        }
    }
}