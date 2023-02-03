package com.example.myapplication;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

public class OtpActivity extends AppCompatActivity {
    EditText phn_editText;
    Button otpReq_Btn;
    ImageView backbtn;
    Dialog dialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_otp);
        phn_editText = findViewById(R.id.edt_phno);
        otpReq_Btn = findViewById(R.id.btn_req_otp);
        backbtn = findViewById(R.id.back);
      dialog = new Dialog(OtpActivity.this);

        backbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        otpReq_Btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isValidMobile(phn_editText.getText().toString())) {
                    showOtpDialog(getOtp(phn_editText.getText().toString()));
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            Intent i = new Intent(OtpActivity.this, OtpVerify_Activity.class);
                            i.putExtra("phno", phn_editText.getText().toString());
                            i.putExtra("otp", getOtp(phn_editText.getText().toString()));
                            startActivity(i);
                        }
                    }, 3000);
                } else {
                    Toast.makeText(OtpActivity.this, "Please Enter Valid Phone Number!", Toast.LENGTH_SHORT).show();
                }

            }
        });

    }


    public String getOtp(String phn_no) {
        String firstTwo = phn_no.substring(0, 2);
        String lastTwo = phn_no.substring(8, 10);
        String otp = firstTwo + lastTwo;
        return otp;
    }

    private boolean isValidMobile(String phone) {
        if (phone.length() == 10) {
            return android.util.Patterns.PHONE.matcher(phone).matches();
        } else {
            return false;
        }
    }


    private void showOtpDialog(String otp) {
        View view = getLayoutInflater().inflate(R.layout.dialog_otp, null);
        TextView otp_tv = (TextView) view.findViewById(R.id.otp_phno);
        otp_tv.setText(otp);
        dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        dialog.setContentView(view);
        dialog.setCancelable(false);
        dialog.show();
    }

    @Override
    protected void onStop() {
        super.onStop();
        dialog.dismiss();
    }
}