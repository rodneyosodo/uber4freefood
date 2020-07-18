package com.qualis.qfood;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
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
import com.qualis.qfood.Adapters.FoodItemAdapter;
import com.qualis.qfood.Model.Food;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.qualis.qfood.Common.Common.currentUser;

public class MyHistoryActivity extends AppCompatActivity {

    TextView txtHistoryPresent;

    private RecyclerView recyclerHistoryFoodItems;
    private FoodItemAdapter adapter;
    private RecyclerView.LayoutManager layoutManager;

    List<Food> historyFoodList;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_history);

        txtHistoryPresent = (TextView)findViewById(R.id.txtHistoryPresent);

        historyFoodList = new ArrayList<>();

        recyclerHistoryFoodItems = (RecyclerView)findViewById(R.id.recycler_history_food_items);
        recyclerHistoryFoodItems.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        recyclerHistoryFoodItems.setLayoutManager(layoutManager);

        try {
            getFoodData();
        } catch (IOException e) {
            e.printStackTrace();
        }


    }

    private void getFoodData() throws IOException {

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        String URL = "https://d464d72f89df.ngrok.io/food";

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
                        if(humanID.equalsIgnoreCase(String.valueOf(currentUser.getId()))){
                            historyFoodList.add(foodItem);
                        }


                    } catch (JSONException | IOException e) {
                        e.printStackTrace();

                    }
                }



                if (historyFoodList.size() == 0){
                    txtHistoryPresent.setText("You Have No Food Pickup History");

                }else{
                    adapter = new FoodItemAdapter(MyHistoryActivity.this, historyFoodList);
                    recyclerHistoryFoodItems.setAdapter(adapter);
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
}

