package com.qualis.qfood;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import life.sabujak.roundedbutton.RoundedButton;

public class StartActivity extends AppCompatActivity {

    RoundedButton btnLogIn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);

        btnLogIn = (RoundedButton)findViewById(R.id.btnStartActivity);
        btnLogIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent logIn = new Intent(StartActivity.this,LogInActivity.class);
                startActivity(logIn);


            }
        });
    }
}
