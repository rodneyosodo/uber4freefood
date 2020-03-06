package com.qualis.qfood;

import androidx.appcompat.app.AppCompatActivity;
import com.qualis.qfood.Tools.Converter;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.rengwuxian.materialedittext.MaterialEditText;

import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;

import life.sabujak.roundedbutton.RoundedButton;

public class LogInActivity extends AppCompatActivity {

    ImageView arrowBack;
    com.rengwuxian.materialedittext.MaterialEditText edtEmail, edtPassword;
    life.sabujak.roundedbutton.RoundedButton btnLogin;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log_in);

        arrowBack = (ImageView)findViewById(R.id.arrowBack);
        edtEmail = (MaterialEditText)findViewById(R.id.edtEmail);
        edtPassword = (MaterialEditText)findViewById(R.id.edtPassword);
        btnLogin =(RoundedButton)findViewById(R.id.btnLogIn);


        arrowBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = edtEmail.getText().toString().trim();
                String password = edtPassword.getText().toString();
                if (TextUtils.isEmpty(email) || TextUtils.isEmpty(password)){
                    Toast.makeText(LogInActivity.this,"Kindly fill all details to proceed",Toast.LENGTH_LONG).show();
                }
                else if ( password.length() < 8 ){
                    Toast.makeText(LogInActivity.this,"Password cannot be less that 8 characters",Toast.LENGTH_LONG).show();
                }
                else{
                    try {
                        String encryptedPassword = Converter.SHA1(password);
                        Toast.makeText(LogInActivity.this,encryptedPassword ,Toast.LENGTH_LONG).show();

                    } catch (NoSuchAlgorithmException e) {
                        e.printStackTrace();
                    } catch (UnsupportedEncodingException e) {
                        e.printStackTrace();
                    }



                }
            }
        });
    }
}
