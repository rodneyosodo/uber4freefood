package com.qualis.qfood;

import android.Manifest;
import android.app.PendingIntent;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationManager;
import android.location.LocationProvider;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.Menu;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MapStyleOptions;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.location.LocationListener;

import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.FirebaseApp;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionDeniedResponse;
import com.karumi.dexter.listener.PermissionGrantedResponse;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.single.PermissionListener;
import com.qualis.qfood.Adapters.FoodItemAdapter;
import com.qualis.qfood.Common.Common;
import com.qualis.qfood.MapRoutesHelper.FetchURL;
import com.qualis.qfood.MapRoutesHelper.TaskLoadedCallback;
import com.qualis.qfood.Model.Food;
import com.qualis.qfood.Model.User;
import com.qualis.qfood.Service.UserLocationService;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AlertDialog;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import de.hdodenhof.circleimageview.CircleImageView;

import static androidx.constraintlayout.widget.Constraints.TAG;


public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener, OnMapReadyCallback, TaskLoadedCallback {

    CircleImageView imgUserProfile;
    TextView txtUserEmail, txtUserName;

    FirebaseStorage storage;
    StorageReference storageReference;

    private RecyclerView recyclerFoodItems;
    private FoodItemAdapter adapter;
    private RecyclerView.LayoutManager layoutManager;

    List<Food> foodList;


    private BottomSheetBehavior sheetBehavior;
    private LinearLayout bottom_sheet;


    private GoogleMap mMap;
    MapFragment mapFragment;


    static MainActivity instance;

    public static MainActivity getInstance() {
        return instance;
    }


    LocationRequest locationRequest;
    FusedLocationProviderClient fusedLocationProviderClient;


    private MarkerOptions angelMarker;
    private Polyline currentPolyline;

    LatLng angelMarkerPosition, myMarkerPosition;
    Location userCurrentLocation;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        instance = this;

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("Home");
        setSupportActionBar(toolbar);


        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        View headerview = navigationView.getHeaderView(0);

        FirebaseApp.initializeApp(this);
        storage = FirebaseStorage.getInstance();


        angelMarkerPosition = new LatLng(-1.3204, 36.7041);

        Dexter.withActivity(this)
                .withPermission(Manifest.permission.ACCESS_FINE_LOCATION)
                .withListener(new PermissionListener() {
                    @Override
                    public void onPermissionGranted(PermissionGrantedResponse response) {
                        updateUserLocation();




                    }

                    @Override
                    public void onPermissionDenied(PermissionDeniedResponse response) {
                        Toast.makeText(MainActivity.this, "Location services are required to acces functionality", Toast.LENGTH_LONG).show();

                    }

                    @Override
                    public void onPermissionRationaleShouldBeShown(PermissionRequest permission, PermissionToken token) {

                    }
                }).check();


        String profile = Common.currentUser.getProfilepicname();

        imgUserProfile = (CircleImageView) headerview.findViewById(R.id.imgUserProfile);
        txtUserEmail = (TextView) headerview.findViewById(R.id.txtUserEmail);
        txtUserName = (TextView) headerview.findViewById(R.id.txtUserName);

        String name = Common.currentUser.getFirstname() + " " + Common.currentUser.getLastname();
        String mail = Common.currentUser.getEmail();

        txtUserName.setText(name);
        txtUserEmail.setText(mail);


        storageReference = storage.getReference("ProfilePictures/").child(profile);


        storageReference.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {
                String imgURL = uri.toString();
                Glide.with(MainActivity.this).load(imgURL).into(imgUserProfile);
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception exception) {
                // Handle any errors
            }
        });

        //bottom sheet
        bottom_sheet = findViewById(R.id.bottomSheetContainer);
        sheetBehavior = BottomSheetBehavior.from(bottom_sheet);
        sheetBehavior.setState(BottomSheetBehavior.STATE_EXPANDED);
        sheetBehavior.setDraggable(true);

        bottom_sheet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (sheetBehavior.getState() != BottomSheetBehavior.STATE_EXPANDED) {
                    sheetBehavior.setState(BottomSheetBehavior.STATE_EXPANDED);
                } else {
                    sheetBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
                }
            }
        });


        //Recycler
        foodList = new ArrayList<>();

        recyclerFoodItems = (RecyclerView) findViewById(R.id.recycler_food_items);
        recyclerFoodItems.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        recyclerFoodItems.setLayoutManager(layoutManager);


        //Google maps
        mapFragment = (MapFragment) getFragmentManager()
                .findFragmentById(R.id.mMap);
        mapFragment.getMapAsync(MainActivity.this);

        // connection to db
        try {
            getFoodData();
        } catch (IOException e) {
            e.printStackTrace();
        }


    }

    private void updateUserLocation() {
        buildLocationRequest();

        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this);
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

            return;
        }

        fusedLocationProviderClient.requestLocationUpdates(locationRequest, getPendingIntent());
    }

    private PendingIntent getPendingIntent() {
        Intent intent = new Intent(this, UserLocationService.class);
        intent.setAction(UserLocationService.ACTION_PROCESS_UPDATE);

        return PendingIntent.getBroadcast(this, 1, intent, PendingIntent.FLAG_UPDATE_CURRENT);
    }

    private void buildLocationRequest() {
        locationRequest = new LocationRequest();
        locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        locationRequest.setInterval(5000);
        locationRequest.setFastestInterval(3000);
        locationRequest.setSmallestDisplacement(10f);

    }

    public void updateMapsUserLocation(final Location userlocation) {
        userCurrentLocation = userlocation;

        angelMarker = new MarkerOptions()
                .position(angelMarkerPosition)
                .snippet("Grubbys")
                .title("Angel");
        mMap.addMarker(angelMarker);




        if (userCurrentLocation != null)
        {
            myMarkerPosition =  new LatLng(userCurrentLocation.getLatitude(), userCurrentLocation.getLongitude());

            mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(myMarkerPosition, 17));

            CameraPosition cameraPosition = new CameraPosition.Builder()
                    .target(myMarkerPosition)      // Sets the center of the map to location user
                    .zoom(17)                   // Sets the zoom
                    .bearing(0)                // Sets the orientation of the camera to north
                    .tilt(40)                   // Sets the tilt of the camera to 30 degrees
                    .build();                   // Creates a CameraPosition from the builder
            mMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));

            new FetchURL(MainActivity.this).execute(createUrl(myMarkerPosition, angelMarker.getPosition(), "driving"), "driving");




            Toast.makeText(MainActivity.this,myMarkerPosition.toString(),Toast.LENGTH_LONG).show();

        }

        MainActivity.this.runOnUiThread(new Runnable() {
            @Override
            public void run() {





            }
        });
    }

    @Override
    public void onRestart() {
        super.onRestart();
        //When BACK BUTTON is pressed, the activity on the stack is restarted
        //Do what you want on the refresh procedure here
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

                for (int i = 0; i < foodListResponse.length(); i++) {
                    try {
                        JSONObject foodObject = foodListResponse.getJSONObject(i);

                        //Map json object to FoodClass
                        ObjectMapper mapper = new ObjectMapper();
                        mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
                        Food foodItem = mapper.readValue(foodObject.toString(), Food.class);

                        foodItem.setID(foodObject.getInt("id"));

                        String humanID = "" + foodItem.getHumanUserID();
                        String foodStatus = "" + foodItem.getStatus();

                        //  double withinRadius = getDistanceBetweenUserLocationandFoodLocation();
                        ///check thus code
                        if (humanID.equalsIgnoreCase(String.valueOf(Common.currentUser.getId())) && (foodStatus.equalsIgnoreCase("active"))) {
                            foodList.clear();
                            foodList.add(foodItem);
                            Float foodLat = Float.valueOf(foodList.get(0).getLocationLat());
                            Float foodLong = Float.valueOf(foodList.get(0).getLocationLong());


                            break;

                        } else {
                            foodList.add(foodItem);
                        }


                    } catch (JsonParseException e) {
                        e.printStackTrace();

                    } catch (JSONException e) {
                        e.printStackTrace();

                    } catch (JsonMappingException e) {
                        e.printStackTrace();

                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }

                adapter = new FoodItemAdapter(MainActivity.this, foodList);
                recyclerFoodItems.setAdapter(adapter);


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


    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }


    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_browse) {

        } else if (id == R.id.nav_active_food) {

        } else if (id == R.id.nav_my_history) {

            Intent myHistory = new Intent(MainActivity.this, MyHistoryActivity.class);
            startActivity(myHistory);


        } else if (id == R.id.nav_account) {

        } else if (id == R.id.nav_settings) {


        } else if (id == R.id.nav_log_out) {
            // Log out
            Intent signIn = new Intent(MainActivity.this, LogInActivity.class);
            signIn.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(signIn);
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }

    //Google Maps
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        try {
            // Customise the styling of the base map using a JSON object defined
            // in a raw resource file.

            boolean success = googleMap.setMapStyle(
                    MapStyleOptions.loadRawResourceStyle(
                            MainActivity.this, R.raw.style_json));

            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

                return;
            }
            mMap.setMyLocationEnabled(true);





            if (!success) {
                Log.e(TAG, "Style parsing failed.");
            }
        } catch (Resources.NotFoundException e) {
            Log.e(TAG, "Can't find style. Error: ", e);
        }


    }

    private String createUrl(LatLng origin, LatLng destination, String directionMode) {
        String strOrigin = "origin=" + origin.latitude + "," + origin.longitude;
        String strDestination = "destination=" + destination.latitude + "," + destination.longitude;

        String strMode = "mode=" + directionMode;
        String params = strOrigin + "&" + strDestination + "&" + strMode;

        String createdUrl = "https://maps.googleapis.com/maps/api/directions/json?" + params + "&key=AIzaSyC1mqv3W0bo4YaKfOgCyQOYXX76r1Ji8EA";

        return createdUrl;
    }

    @Override
    public void onTaskDone(Object... values) {
        if (currentPolyline != null)
            currentPolyline.remove();
        currentPolyline = mMap.addPolyline((PolylineOptions) values[0]);
    }











}

