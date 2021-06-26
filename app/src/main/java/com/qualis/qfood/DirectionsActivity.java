package com.qualis.qfood;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.Log;

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
import com.qualis.qfood.MapRoutesHelper.FetchURL;
import com.qualis.qfood.MapRoutesHelper.TaskLoadedCallback;

import java.util.Objects;

import static androidx.constraintlayout.widget.Constraints.TAG;

public class DirectionsActivity extends AppCompatActivity implements OnMapReadyCallback, TaskLoadedCallback {
    private GoogleMap mMap;
    private MarkerOptions angelMarker;
    private Polyline currentPolyline;
    LatLng angelMarkerPosition = new LatLng(-1.320163, 36.704049);
    Location location;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_directions);

        MapFragment mapFragment = (MapFragment) getFragmentManager()
                .findFragmentById(R.id.mMap);
        mapFragment.getMapAsync(this);


    }


    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        try {

            // Customise the styling of the base map using a JSON object defined
            // in a raw resource file.

            boolean success = googleMap.setMapStyle(
                    MapStyleOptions.loadRawResourceStyle(
                            DirectionsActivity.this, R.raw.style_json));

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

        LocationManager locationManager = (LocationManager) DirectionsActivity.this.getSystemService(Context.LOCATION_SERVICE);
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


            mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(myMarkerPosition, 100));

            CameraPosition cameraPosition = new CameraPosition.Builder()
                    .target(myMarkerPosition)      // Sets the center of the map to location user
                    .zoom(9)                   // Sets the zoom
                    .bearing(90)                // Sets the orientation of the camera to east
                    .tilt(40)                   // Sets the tilt of the camera to 30 degrees
                    .build();                   // Creates a CameraPosition from the builder
            mMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));

            new FetchURL(DirectionsActivity.this).execute(createUrl(myMarkerPosition, angelMarker.getPosition(), "driving"), "driving");


        }



    }

    private String createUrl(LatLng origin, LatLng destination, String directionMode){
        String strOrigin = "origin=" + origin.latitude + "," + origin.longitude;
        String strDestination = "destination=" + destination.latitude + "," + destination.longitude;

        String strMode = "mode=" + directionMode;
        String params = strOrigin + "&" + strDestination +"&" + strMode;

        String createdUrl = "https://maps.googleapis.com/maps/api/directions/json?" + params + "&key=AIzaSyCmXVYL_GNFva7QvP30jweNEhj9IwK3-bg";

        return createdUrl;
    }

    @Override
    public void onTaskDone(Object... values) {
        if (currentPolyline != null)
            currentPolyline.remove();
        currentPolyline = mMap.addPolyline((PolylineOptions) values[0]);
    }
}
