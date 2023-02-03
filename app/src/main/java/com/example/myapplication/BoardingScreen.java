package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class BoardingScreen extends AppCompatActivity {
    Button btn_getstart;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_boarding_screen);
        btn_getstart=findViewById(R.id.btn_get_start);
        btn_getstart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(BoardingScreen.this, OtpActivity.class);
                startActivity(i);
                finish();
            }
        });
    }
}