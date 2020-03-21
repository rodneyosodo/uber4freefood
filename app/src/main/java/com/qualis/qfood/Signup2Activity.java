package com.qualis.qfood;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.qualis.qfood.Tools.Converter;
import com.rengwuxian.materialedittext.MaterialEditText;

import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;

import life.sabujak.roundedbutton.RoundedButton;

public class Signup2Activity extends AppCompatActivity {

    StorageReference firebaseStorageReference = FirebaseStorage.getInstance().getReference();

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

        Bundle extras = getIntent().getExtras();
        final byte[] byteArray = extras.getByteArray("picture");

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

                    SimpleDateFormat dateTimeFormat = new SimpleDateFormat("yyyyMMddHHmmss", Locale.getDefault());
                    String currentDateTime = dateTimeFormat.format(new Date());

                    final String profilePicName = finalMapSecondPage.get("FirstName") + finalMapSecondPage.get("FirstName") + currentDateTime;



                    StorageReference profilePicRef = firebaseStorageReference.child("ProfilePictures").child(profilePicName);
                    UploadTask uploadTask = profilePicRef.putBytes(byteArray);

                    finalMapSecondPage.put("Email", email);
                    finalMapSecondPage.put("Phone", phoneNumber);
                    finalMapSecondPage.put("Password",password);
                    finalMapSecondPage.put("ProfilePicName", profilePicName);

                    uploadTask.addOnCompleteListener(new OnCompleteListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<UploadTask.TaskSnapshot> task) {
                            Toast.makeText(Signup2Activity.this, profilePicName,Toast.LENGTH_LONG).show();
                        }
                    });




                }

            }
        });
    }
}
