package com.qualis.angelapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.google.gson.Gson;
import com.qualis.angelapp.Common.Common;
import com.qualis.angelapp.Model.User;
import com.qualis.angelapp.Tools.Converter;
import com.rengwuxian.materialedittext.MaterialEditText;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import life.sabujak.roundedbutton.RoundedButton;

public class AddFood2Activity extends AppCompatActivity {

    StorageReference firebaseStorageReference = FirebaseStorage.getInstance().getReference();

    ImageView arrowBack;
    RoundedButton btnAddFood2;
    com.rengwuxian.materialedittext.MaterialEditText edtFoodDescription, edtSpecialIngredients, edtFoodServing, edtSpecialNote;
    ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_food2);


        edtFoodDescription = (MaterialEditText)findViewById(R.id.edtFoodDescription);
        edtSpecialIngredients = (MaterialEditText)findViewById(R.id.edtSpecialIngredients);
        edtFoodServing = (MaterialEditText)findViewById(R.id.edtFoodServing);
        edtSpecialNote = (MaterialEditText)findViewById(R.id.edtSpecialNote);

        arrowBack = (ImageView)findViewById(R.id.arrowBack);
        btnAddFood2 = (RoundedButton)findViewById(R.id.btnAddFood2);


        HashMap<String, String> mapSecondPage = new HashMap();

        mapSecondPage = (HashMap<String, String>) getIntent().getSerializableExtra("hashMap");

        Bundle extras = getIntent().getExtras();
        final byte[] byteArray = extras.getByteArray("picture");

        Toast.makeText(this, mapSecondPage.toString(),Toast.LENGTH_LONG).show();



        final HashMap<String, String> finalMapSecondPage = mapSecondPage;

        btnAddFood2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String foodDescription = edtFoodDescription.getText().toString();
                String specialIngredients = edtSpecialIngredients.getText().toString();
                String foodServing = edtFoodServing.getText().toString().trim();
                String specialNote = edtSpecialNote.getText().toString();


                if (TextUtils.isEmpty(foodDescription) || TextUtils.isEmpty(specialIngredients) || TextUtils.isEmpty(foodServing) || TextUtils.isEmpty(specialNote)){
                    Toast.makeText(AddFood2Activity.this,"Kindly fill all details to proceed",Toast.LENGTH_LONG).show();

                }
                else{

                    SimpleDateFormat dateTimeFormat = new SimpleDateFormat("yyyyMMddHHmmss", Locale.getDefault());
                    String currentDateTime = dateTimeFormat.format(new Date());

                    final String foodImageid = finalMapSecondPage.get("FoodName") + currentDateTime;



                    StorageReference profilePicRef = firebaseStorageReference.child("FoodImages").child(foodImageid);
                    UploadTask uploadTask = profilePicRef.putBytes(byteArray);

                    finalMapSecondPage.put("Description", foodDescription);
                    finalMapSecondPage.put("SpecialIngredients", specialIngredients);
                    finalMapSecondPage.put("FoodServing",foodServing);
                    finalMapSecondPage.put("FoodImageId", foodImageid);
                    finalMapSecondPage.put("SpecialNote", specialNote);
                    finalMapSecondPage.put("locationLat", "-1.320163");
                    finalMapSecondPage.put("locationLong", "36.704049");
                    finalMapSecondPage.put("status", "Inactive");


                    uploadTask.addOnCompleteListener(new OnCompleteListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<UploadTask.TaskSnapshot> task) {
                            Toast.makeText(AddFood2Activity.this, foodImageid,Toast.LENGTH_LONG).show();
                        }
                    });

                    try {
                        addFoodRequest(finalMapSecondPage);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }





                }

            }
        });
    }

    private void addFoodRequest(final Map data) throws JSONException {

        final RequestQueue requestQueue = Volley.newRequestQueue(this);
        String URL = "https://e4e7a0533d9f.ngrok.io/food";

        Gson gson = new Gson();
        String json = gson.toJson(data);


        JsonObjectRequest jsonObjReq = new JsonObjectRequest(
                Request.Method.POST, URL, new JSONObject(json),
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Toast.makeText(AddFood2Activity.this, response.toString(), Toast.LENGTH_LONG).show();
                        try {
                            addFoodCheck(response);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }

                    }

                }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {

            }
        }) {

            //Request headers

            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                HashMap<String, String> headers = new HashMap<String, String>();
                headers.put("Content-Type", "application/json; charset=utf-8");
                return headers;
            }

        };
        requestQueue.add(jsonObjReq);

    }
    private void addFoodCheck( JSONObject response) throws IOException {

        String message = null;
        try {
            message = response.getString("message");

        } catch (JSONException e) {
            e.printStackTrace();
        }

        Boolean status = null;
        try {
            status = response.getBoolean("status");
        } catch (JSONException e) {
            e.printStackTrace();
        }

        if(message.equals("Food has been created") && status == true){



            Intent mainActivity = new Intent(AddFood2Activity.this, MainActivity.class);
            mainActivity.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(mainActivity);




        }else {
            Toast.makeText(AddFood2Activity.this, message, Toast.LENGTH_LONG).show();
        }

    }
}