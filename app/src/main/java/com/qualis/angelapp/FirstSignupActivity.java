package com.qualis.angelapp;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.rengwuxian.materialedittext.MaterialEditText;

import java.io.ByteArrayOutputStream;
import java.util.HashMap;

import de.hdodenhof.circleimageview.CircleImageView;
import life.sabujak.roundedbutton.RoundedButton;

public class FirstSignupActivity extends AppCompatActivity {
    static final int REQUEST_TAKE_PHOTO = 1;
    byte[] imgdata;
    StorageReference storageReference;

    MaterialEditText edtFirstName, edtLastName;

    TextView  txtCameraInvoke;
    RoundedButton btnSignup1;
    CircleImageView circleImageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_first_signup);

        storageReference = FirebaseStorage.getInstance().getReference();

        edtFirstName = (MaterialEditText)findViewById(R.id.edtFirstName);
        edtLastName = (MaterialEditText)findViewById(R.id.edtLastName);
        btnSignup1 =(RoundedButton)findViewById(R.id.btnSignup1);
        circleImageView = (CircleImageView) findViewById(R.id.imgView);
        txtCameraInvoke = (TextView)findViewById(R.id.cameraInvoke);





        txtCameraInvoke.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent takePhoto = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(takePhoto, REQUEST_TAKE_PHOTO);

            }
        });


        btnSignup1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String firstName = edtFirstName.getText().toString().trim();
                String lastName = edtLastName.getText().toString().trim();


                if (TextUtils.isEmpty(firstName) || TextUtils.isEmpty(lastName) || imgdata == null){
                    Toast.makeText(FirstSignupActivity.this,"Kindly fill all details to proceed",Toast.LENGTH_LONG).show();
                }
                else{
                    HashMap<String, String> mapFirstPage = new HashMap<>();
                    mapFirstPage.put("FirstName", firstName);
                    mapFirstPage.put("LastName", lastName);


                    Intent signup2 = new Intent(FirstSignupActivity.this, SecondSignupActivity.class);
                    signup2.putExtra("hashMap", mapFirstPage);
                    signup2.putExtra("picture", imgdata);
                    startActivity(signup2);
                }



            }
        });
    }



    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_TAKE_PHOTO && resultCode == RESULT_OK) {

            if (requestCode == REQUEST_TAKE_PHOTO) {
                Uri uri = data.getData();
                Bundle extras = data.getExtras();
                Bitmap imageBitmap = (Bitmap) extras.get("data");
                circleImageView.setImageBitmap(imageBitmap);

                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                imageBitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
                byte[] databaos = baos.toByteArray();

                imgdata = databaos;


            }
        }
    }



}
