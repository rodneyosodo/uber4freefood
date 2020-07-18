package com.qualis.qfood.Service;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.widget.Toast;

import com.google.android.gms.location.LocationResult;
import com.qualis.qfood.MainActivity;

public class UserLocationService extends BroadcastReceiver {
    public static final String ACTION_PROCESS_UPDATE = "com.qualis.qfood.Service.UPDATE_LOCATION";

    @Override
    public void onReceive(Context context, Intent intent) {

        if (intent != null){
            final String action = intent.getAction();

            if(ACTION_PROCESS_UPDATE.equals(action)){

                LocationResult result = LocationResult.extractResult(intent);

                if(result != null){
                    Location location = result.getLastLocation();
/*
                    String location_string = new StringBuilder("" + location.getLatitude())
                            .append("/ ")
                            .append(location.getLongitude())
                            .toString();

 */

                    try {
                        MainActivity.getInstance().updateMapsUserLocation(location);
                    }catch (Exception e){

                        Toast.makeText(context,location.toString(),Toast.LENGTH_LONG).show();

                    }

                }
            }
        }

    }
}
