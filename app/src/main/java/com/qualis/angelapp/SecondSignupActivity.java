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
import com.google.gson.JsonObject;
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

public class SecondSignupActivity extends AppCompatActivity {

    StorageReference firebaseStorageReference = FirebaseStorage.getInstance().getReference();

    ImageView arrowBack;
    RoundedButton btnSignup2;
    com.rengwuxian.materialedittext.MaterialEditText edtEmail, edtPhone, edtPasswordReg;
    ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second_signup);


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



        final HashMap<String, String> finalMapSecondPage = mapSecondPage;

        btnSignup2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = edtEmail.getText().toString().trim();
                String phoneNumber = edtPhone.getText().toString().trim();
                String password = edtPasswordReg.getText().toString();


                if (TextUtils.isEmpty(email) || TextUtils.isEmpty(phoneNumber) || TextUtils.isEmpty(password)){
                    Toast.makeText(SecondSignupActivity.this,"Kindly fill all details to proceed",Toast.LENGTH_LONG).show();
                }
                else  if(TextUtils.getTrimmedLength(password) < 8) {
                    Toast.makeText(SecondSignupActivity.this,"Password must be greater than 8 characters",Toast.LENGTH_LONG).show();

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

                    finalMapSecondPage.put("email", email);
                    finalMapSecondPage.put("phonenumber", phoneNumber);
                    finalMapSecondPage.put("password",password);
                    finalMapSecondPage.put("profilepicname", profilePicName);
                    finalMapSecondPage.put("usertype", "angel");

                    uploadTask.addOnCompleteListener(new OnCompleteListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<UploadTask.TaskSnapshot> task) {
                            Toast.makeText(SecondSignupActivity.this, profilePicName,Toast.LENGTH_LONG).show();
                        }
                    });

                    try {
                        signUpRequest(finalMapSecondPage);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }





                }

            }
        });
    }

    private void signUpRequest(final Map data) throws JSONException {

        final RequestQueue requestQueue = Volley.newRequestQueue(this);
        String URL = "https://f29c264d.ngrok.io/api/user/new";

        Gson gson = new Gson();
        String json = gson.toJson(data);


        JsonObjectRequest jsonObjReq = new JsonObjectRequest(
                Request.Method.POST, URL, new JSONObject(json),
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Toast.makeText(SecondSignupActivity.this, response.toString(), Toast.LENGTH_LONG).show();

                        try {
                            signUpCheck(response);
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
    private void signUpCheck( JSONObject response) throws IOException {

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

        if(message.equals("Account has been created") && status == true){

            JSONObject account = null;

            try {
                account = response.getJSONObject("account");

            } catch (JSONException e) {
                e.printStackTrace();
            }


            ObjectMapper mapper = new ObjectMapper();
            mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

            User user = mapper.readValue(account.toString(), User.class);

            Common.currentUser = user;

            Intent mainActivity = new Intent(SecondSignupActivity.this, MainActivity.class);
            mainActivity.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(mainActivity);




        }else {
            Toast.makeText(SecondSignupActivity.this, message, Toast.LENGTH_LONG).show();
        }

    }


}
