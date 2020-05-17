package com.qualis.qfood;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import com.qualis.qfood.Common.Common;
import com.qualis.qfood.Model.User;


import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.qualis.qfood.Tools.Converter;
import com.rengwuxian.materialedittext.MaterialEditText;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.Map;

import life.sabujak.roundedbutton.RoundedButton;



public class LogInActivity extends AppCompatActivity {

    ImageView arrowBack;
    com.rengwuxian.materialedittext.MaterialEditText edtEmail, edtPassword;
    TextView txtSignUp;
    life.sabujak.roundedbutton.RoundedButton btnLogin;
    ProgressDialog progressDialog;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log_in);

        arrowBack = (ImageView)findViewById(R.id.arrowBack);
        edtEmail = (MaterialEditText)findViewById(R.id.edtEmail);
        edtPassword = (MaterialEditText)findViewById(R.id.edtPassword);
        btnLogin =(RoundedButton)findViewById(R.id.btnLogIn);
        txtSignUp = (TextView)findViewById(R.id.txtSignUp);



        arrowBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        txtSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentSignup = new Intent(LogInActivity.this,Signup1Activity.class);
                startActivity(intentSignup);
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
                    progressDialog = new ProgressDialog(LogInActivity.this, R.style.ProgressDialogStyle);
                    progressDialog.setMessage("Please Wait..");
                    progressDialog.setCancelable(false);
                    progressDialog.show();

                    Converter pwdConverter = new Converter();

                    try {
                        password = pwdConverter.SHA1(password);
                    } catch (NoSuchAlgorithmException e) {
                        e.printStackTrace();
                    } catch (UnsupportedEncodingException e) {
                        e.printStackTrace();
                    }

                    try {
                        loginRequest(email,password);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }


                }
            }
        });
    }

    private void loginRequest(final String uname, final String pwd) throws JSONException {

        final RequestQueue requestQueue = Volley.newRequestQueue(this);
        String URL = "https://2a80e91f.ngrok.io/api/user/login";

        Map<String, String> dataMap = new HashMap<>();
        dataMap.put("email", uname);
        dataMap.put("password", pwd);

        Gson gson = new Gson();
        String json = gson.toJson(dataMap);


        JsonObjectRequest jsonObjReq = new JsonObjectRequest(
                Request.Method.POST, URL, new JSONObject(json),
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {


                        try {
                            loginCheck(response);
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

    private void loginCheck(JSONObject response) throws IOException {

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

        if(message.equals("Logged In") && status == true){
            JSONObject account = null;

            try {
                account = response.getJSONObject("account");

            } catch (JSONException e) {
                e.printStackTrace();
            }



            ObjectMapper mapper = new ObjectMapper();
            mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

            User user = mapper.readValue(account.toString(), User.class);




            Toast.makeText(LogInActivity.this,  user.getPhonenumber(), Toast.LENGTH_LONG).show();

            String userType = user.getUsertype();


            if (userType.equals("human")){


                while(Common.currentUser == null){
                    Common.currentUser = user;
                }


                Intent mainActivity = new Intent(LogInActivity.this, MainActivity.class);
                mainActivity.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                progressDialog.dismiss();
                startActivity(mainActivity);



            }
            else{
                Toast.makeText(LogInActivity.this, "Invalid User.", Toast.LENGTH_LONG).show();
            }



        }else {
            Toast.makeText(LogInActivity.this, message, Toast.LENGTH_LONG).show();
        }



    }



}
