package com.qualis.angelapp;

import androidx.appcompat.app.AppCompatActivity;

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

import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.rengwuxian.materialedittext.MaterialEditText;

import java.io.ByteArrayOutputStream;
import java.util.HashMap;

import de.hdodenhof.circleimageview.CircleImageView;
import life.sabujak.roundedbutton.RoundedButton;

public class AddFoodActivity extends AppCompatActivity {

    static final int REQUEST_TAKE_PHOTO = 1;
    byte[] imgdata;


    MaterialEditText edtFoodName, edtDietType;

    TextView txtCameraInvoke;
    RoundedButton btnAddFood1;
    ImageView foodImage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_food);



        edtFoodName = (MaterialEditText)findViewById(R.id.edtFoodName);
        edtDietType = (MaterialEditText)findViewById(R.id.edtDietType);
        btnAddFood1 =(RoundedButton)findViewById(R.id.btnAddFood1);
        foodImage = (ImageView) findViewById(R.id.imgFoodPhoto);
        txtCameraInvoke = (TextView)findViewById(R.id.cameraInvoke);





        txtCameraInvoke.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent takePhoto = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(takePhoto, REQUEST_TAKE_PHOTO);

            }
        });


        btnAddFood1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String foodName = edtFoodName.getText().toString().trim();
                String foodDietType = edtDietType.getText().toString().trim();


                if (TextUtils.isEmpty(foodName) || TextUtils.isEmpty(foodDietType) || imgdata == null){
                    Toast.makeText(AddFoodActivity.this,"Kindly fill all details to proceed",Toast.LENGTH_LONG).show();
                }
                else{
                    HashMap<String, String> mapFirstPage = new HashMap<>();
                    mapFirstPage.put("FoodName", foodName);
                    mapFirstPage.put("DietType", foodDietType);


                    Intent addFood2 = new Intent(AddFoodActivity.this, AddFood2Activity.class);
                    addFood2.putExtra("hashMap", mapFirstPage);
                    addFood2.putExtra("picture", imgdata);
                    startActivity(addFood2);
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
                foodImage.setImageBitmap(imageBitmap);

                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                imageBitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
                byte[] databaos = baos.toByteArray();

                imgdata = databaos;


            }
        }
    }
}