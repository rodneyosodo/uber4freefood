package com.qualis.qfood;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.qualis.qfood.Common.Common;
import com.qualis.qfood.Model.User;

import life.sabujak.roundedbutton.RoundedButton;

import static com.qualis.qfood.Common.Common.currentUser;

public class StartActivity extends AppCompatActivity {

    RoundedButton btnLogIn;
    TextView txtSignUp;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);

        btnLogIn = (RoundedButton)findViewById(R.id.btnStartActivity);
        txtSignUp = (TextView)findViewById(R.id.txtSignUp);



        txtSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentSignup = new Intent(StartActivity.this, MainActivity.class);
                startActivity(intentSignup);
            }
        });

        btnLogIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent logIn = new Intent(StartActivity.this,LogInActivity.class);
                startActivity(logIn);


            }
        });


        if(currentUser != null ){
            Intent mainActivity = new Intent(StartActivity.this, MainActivity.class);
            mainActivity.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(mainActivity);
        }

    }
}
