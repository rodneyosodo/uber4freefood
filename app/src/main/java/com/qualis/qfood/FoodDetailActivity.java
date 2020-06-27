package com.qualis.qfood;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.android.material.appbar.CollapsingToolbarLayout;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.gson.Gson;
import com.qualis.qfood.Adapters.FoodItemAdapter;
import com.qualis.qfood.Model.Food;
import com.qualis.qfood.ViewHolder.FoodViewHolder;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.qualis.qfood.Common.Common.currentUser;

public class FoodDetailActivity extends AppCompatActivity {

    TextView foodName, foodDietType, foodServing, foodDescription, foodIngredients, foodSpecialNote;


    ImageView food_image;
    CollapsingToolbarLayout collapsingToolbarLayout;
    FloatingActionButton fabFood;


    String stringfoodId = "";
    FirebaseStorage storage;
    StorageReference storageReference;

    Food currentFood;

    List<Food> allFoodList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_food_detail);




        foodName = (TextView)findViewById(R.id.food_name);
        foodDietType = (TextView)findViewById(R.id.food_diet_type);
        foodServing = (TextView)findViewById(R.id.food_serving);
        foodDescription = (TextView)findViewById(R.id.food_description);
        foodIngredients = (TextView)findViewById(R.id.food_ingredients);
        foodSpecialNote = (TextView)findViewById(R.id.food_special_note);

        fabFood = (FloatingActionButton)findViewById(R.id.btnCart);


        collapsingToolbarLayout = (CollapsingToolbarLayout)findViewById(R.id.collapsing);
        collapsingToolbarLayout.setExpandedTitleTextAppearance(R.style.ExpandedAppbar);
        collapsingToolbarLayout.setCollapsedTitleTextAppearance(R.style.CollapsedAppbar);

        allFoodList = new ArrayList<>();


        if (getIntent() != null){
            Bundle extras = getIntent().getExtras();
            stringfoodId = extras.getString("FoodId");

        }
        if (!stringfoodId.isEmpty())
        {
            int intfoodId = Integer.parseInt(stringfoodId);
            getFood(stringfoodId);
        }

        fabFood.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                try {
                    checkActiveFood();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });






    }

    private void getFood(String stringfoodId) {

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        String URL = "https://02bce1164642.ngrok.io/food/" + stringfoodId;

        JsonObjectRequest jsonObjReq = new JsonObjectRequest(
                Request.Method.GET, URL, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {

                JSONObject foodObject = null;

                try {
                    foodObject = response.getJSONObject("data");
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                ObjectMapper mapper = new ObjectMapper();
                mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);


                try {
                    currentFood = mapper.readValue(foodObject.toString(), Food.class);
                } catch (IOException e) {
                    e.printStackTrace();
                }

                try {
                    currentFood.setID(foodObject.getInt("id"));
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                setFoodDetails(currentFood);

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

    private void setFoodDetails(Food currentFood) {
        collapsingToolbarLayout.setTitle(currentFood.getFoodName());

        foodName.setText(currentFood.getFoodName());
        foodDietType.setText(currentFood.getDietType());
        foodServing.setText("Serves: " + currentFood.getServing());
        foodDescription.setText(currentFood.getDescription());
        foodIngredients.setText(currentFood.getSpecialIngredients());
        foodSpecialNote.setText(currentFood.getSpecialNote());



    }

    private void checkActiveFood() throws IOException {

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        String URL = "https://02bce1164642.ngrok.io/food";

        JsonObjectRequest jsonObjReq = new JsonObjectRequest(
                Request.Method.GET, URL, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {

                JSONArray foodListResponse = null;
                try {
                    foodListResponse = response.getJSONArray("data");
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                for(int i = 0; i < foodListResponse.length(); i++) {
                    try {
                        JSONObject foodObject = foodListResponse.getJSONObject(i);


                        //Map json object to FoodClass
                        ObjectMapper mapper = new ObjectMapper();
                        mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
                        Food foodItem = mapper.readValue(foodObject.toString(), Food.class);

                        foodItem.setID(foodObject.getInt("id"));

                        //To be removed later
                        String humanID = "" + foodItem.getHumanUserID();
                        String foodStatus = "" + foodItem.getStatus();

                        if(humanID.equalsIgnoreCase(String.valueOf(currentUser.getId())) && (foodStatus.equalsIgnoreCase("active"))){
                            Toast.makeText(FoodDetailActivity.this, "You already have an active food", Toast.LENGTH_LONG).show();
                            break;

                        }else{
                            addFood(stringfoodId);

                        }


                    } catch (JSONException | IOException e) {
                        e.printStackTrace();

                    }
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

    private void addFood(String stringfoodId) throws JSONException {

        final RequestQueue requestQueue = Volley.newRequestQueue(this);
        String URL = "https://02bce1164642.ngrok.io/food/" + stringfoodId;

        String updatedAt = new SimpleDateFormat("yyyyMMddHHmmss").format(Calendar.getInstance().getTime());


        Map<String, String> updateFoodMap = new HashMap<>();

        updateFoodMap.put("humanUserID", String.valueOf(currentUser.getId()));
        updateFoodMap.put("updatedAt", updatedAt);
        updateFoodMap.put("status", "active");

        Gson gson = new Gson();
        String json = gson.toJson(updateFoodMap);


        JsonObjectRequest jsonObjReq = new JsonObjectRequest(
                Request.Method.PATCH, URL, new JSONObject(json),
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {

                        //To be added once the response is added to the api
                        /*

                        Boolean status = null;

                        try {
                            status = response.getBoolean("status");
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                        if(status) {
                            Toast.makeText(FoodDetailActivity.this, "Food Reserved Successfully. Go pick up your food", Toast.LENGTH_SHORT).show();
                        }

                       */

                        Toast.makeText(FoodDetailActivity.this, "Food Reserved Successfully. Go pick up your food", Toast.LENGTH_SHORT).show();

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
}