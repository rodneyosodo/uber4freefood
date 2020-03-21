package com.qualis.qfood;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.qualis.qfood.Tools.Converter;
import com.rengwuxian.materialedittext.MaterialEditText;

import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;

import life.sabujak.roundedbutton.RoundedButton;

public class Signup2Activity extends AppCompatActivity {

    TextView txtAngel;
    ImageView arrowBack;
    RoundedButton btnSignup2;
    com.rengwuxian.materialedittext.MaterialEditText edtEmail, edtPhone, edtPasswordReg;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up2);


        txtAngel = (TextView)findViewById(R.id.txtAngel);
        edtEmail = (MaterialEditText)findViewById(R.id.edtEmail);
        edtPhone = (MaterialEditText)findViewById(R.id.edtPhone);
        edtPasswordReg = (MaterialEditText)findViewById(R.id.edtPasswordReg);
        arrowBack = (ImageView)findViewById(R.id.arrowBack);
        btnSignup2 = (RoundedButton)findViewById(R.id.btnSignup2);


        HashMap<String, String> mapSecondPage = new HashMap();

        mapSecondPage = (HashMap<String, String>) getIntent().getSerializableExtra("hashMap");
        Toast.makeText(this, mapSecondPage.toString(),Toast.LENGTH_LONG).show();

        arrowBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        txtAngel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentSignup2 = new Intent(Signup2Activity.this,Signup1Activity.class);
                startActivity(intentSignup2);
            }
        });

        final HashMap<String, String> finalMapSecondPage = mapSecondPage;

        btnSignup2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = edtEmail.getText().toString().trim();
                String phoneNumber = edtPhone.getText().toString().trim();
                String password = edtPasswordReg.getText().toString();


                if (TextUtils.isEmpty(email) || TextUtils.isEmpty(phoneNumber) || TextUtils.isEmpty(password)){
                    Toast.makeText(Signup2Activity.this,"Kindly fill all details to proceed",Toast.LENGTH_LONG).show();
                }
                else  if(TextUtils.getTrimmedLength(password) < 8) {
                    Toast.makeText(Signup2Activity.this,"Password must be greater than 8 characters",Toast.LENGTH_LONG).show();

                }
                else{
                    Converter pwdRegConvert = new Converter();
                    try {
                        password = pwdRegConvert.SHA1(password);
                    } catch (NoSuchAlgorithmException e) {
                        e.printStackTrace();
                    } catch (UnsupportedEncodingException e) {
                        e.printStackTrace();
                    }



                    finalMapSecondPage.put("Email", email);
                    finalMapSecondPage.put("Phone", phoneNumber);
                    finalMapSecondPage.put("Password",password);

                    Toast.makeText(Signup2Activity.this, finalMapSecondPage.toString(),Toast.LENGTH_LONG).show();;


                }

            }
        });
    }
}
