package com.qualis.qfood;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.Menu;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MapStyleOptions;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.qualis.qfood.Adapters.FoodItemAdapter;
import com.qualis.qfood.Common.Common;
import com.qualis.qfood.MapRoutesHelper.FetchURL;
import com.qualis.qfood.MapRoutesHelper.TaskLoadedCallback;
import com.qualis.qfood.Model.Food;
import com.qualis.qfood.Model.User;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.core.app.ActivityCompat;
import androidx.core.view.GravityCompat;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

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
    Location location;
    private MarkerOptions angelMarker;
    private Polyline currentPolyline;
    LatLng angelMarkerPosition =new LatLng(-1.320163, 36.704049);


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
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

        String profile = Common.currentUser.getProfilepicname();

        imgUserProfile = (CircleImageView)headerview.findViewById(R.id.imgUserProfile);
        txtUserEmail = (TextView)headerview.findViewById(R.id.txtUserEmail);
        txtUserName = (TextView)headerview.findViewById(R.id.txtUserName);

        String name = Common.currentUser.getFirstname() + " " + Common.currentUser.getLastname();
        String  mail = Common.currentUser.getEmail();

        txtUserName.setText(name);
        txtUserEmail.setText(mail);

        storage = FirebaseStorage.getInstance();



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


        //Google maps
        MapFragment mapFragment = (MapFragment) getFragmentManager()
                .findFragmentById(R.id.mMap);
        mapFragment.getMapAsync(this);


        //Recycler
        foodList = new ArrayList<>();

        recyclerFoodItems = (RecyclerView)findViewById(R.id.recycler_food_items);
        recyclerFoodItems.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        recyclerFoodItems.setLayoutManager(layoutManager);

        //static data
        foodList.add(
                new Food("2343", "Rice Rice, Beans veg",
                        "Non-Vegan",
                        "sddfdsfsdfs",
                        "sasadsad",
                        "asdsda",
                        "sddfdsfsdfs",
                        "Kibandi",
                        "sddfdsfsdfs",
                        "sasadsad",
                        "sddfdsfsdfs",
                        "sasadsad",
                        "sddfdsfsdfs",
                        "sasadsad"
                )
        );

        foodList.add(
                new Food("2343", "Rice Veg Chapo 2",
                        "Non-Vegan",
                        "sddfdsfsdfs",
                        "sasadsad",
                        "asdsda",
                        "sddfdsfsdfs",
                        "4",
                        "sddfdsfsdfs",
                        "sasadsad",
                        "sddfdsfsdfs",
                        "sasadsad",
                        "sddfdsfsdfs",
                        "sasadsad"
                )
        );
        adapter = new FoodItemAdapter(this, foodList);
        recyclerFoodItems.setAdapter(adapter);

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

        }  else if (id == R.id.nav_active_food) {

        } else if (id == R.id.nav_my_history) {


        } else if (id == R.id.nav_account) {

        } else if (id == R.id.nav_settings) {
            // Log out
            Intent signIn = new Intent (MainActivity.this, LogInActivity.class);
            signIn.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(signIn);

        } else if (id == R.id.nav_log_out) {

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
                // TODO: Consider calling
                //    ActivityCompat#requestPermissions
                // here to request the missing permissions, and then overriding
                //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                //                                          int[] grantResults)
                // to handle the case where the user grants the permission. See the documentation
                // for ActivityCompat#requestPermissions for more details.
                return;
            }
            mMap.setMyLocationEnabled(true);


            if (!success) {
                Log.e(TAG, "Style parsing failed.");
            }
        } catch (Resources.NotFoundException e) {
            Log.e(TAG, "Can't find style. Error: ", e);
        }

        LocationManager locationManager = (LocationManager) MainActivity.this.getSystemService(Context.LOCATION_SERVICE);
        Criteria criteria = new Criteria();

        location = locationManager.getLastKnownLocation(Objects.requireNonNull(locationManager.getBestProvider(criteria, false)));


        angelMarker = new MarkerOptions()
                .position(angelMarkerPosition)
                .snippet("Grubbys")
                .title("Angel");
        mMap.addMarker(angelMarker);
        if (location != null)
        {


            LatLng myMarkerPosition =  new LatLng(location.getLatitude(), location.getLongitude());

            mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(myMarkerPosition, 9));

            CameraPosition cameraPosition = new CameraPosition.Builder()
                    .target(myMarkerPosition)      // Sets the center of the map to location user
                    .zoom(9)                   // Sets the zoom
                    .bearing(0)                // Sets the orientation of the camera to north
                    .tilt(40)                   // Sets the tilt of the camera to 30 degrees
                    .build();                   // Creates a CameraPosition from the builder
            mMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));

            new FetchURL(MainActivity.this).execute(createUrl(myMarkerPosition, angelMarker.getPosition(), "driving"), "driving");


        }


    }

    private String createUrl(LatLng origin, LatLng destination, String directionMode){
        String strOrigin = "origin=" + origin.latitude + "," + origin.longitude;
        String strDestination = "destination=" + destination.latitude + "," + destination.longitude;

        String strMode = "mode=" + directionMode;
        String params = strOrigin + "&" + strDestination +"&" + strMode;

        String createdUrl = "https://maps.googleapis.com/maps/api/directions/json?" + params + "&key=AIzaSyBBp4gJ4sa_NW0SpmfffmZlFHPAyoEDYys";

        return createdUrl;
    }

    @Override
    public void onTaskDone(Object... values) {
        if (currentPolyline != null)
            currentPolyline.remove();
        currentPolyline = mMap.addPolyline((PolylineOptions) values[0]);
    }
}


