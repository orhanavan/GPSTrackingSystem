package com.gpstrackingsystem;

import android.annotation.SuppressLint;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.IBinder;
import android.provider.Settings;
import android.util.Log;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class GPS_Service extends Service {

    private LocationListener locationListener;
    private LocationManager locationManager;
    int i = 1;

    public GPS_Service() {
    }

    @Override
    public IBinder onBind(Intent intent) {

        return null;
    }

    @SuppressLint("MissingPermission")
    @Override
    public void onCreate() {
        super.onCreate();

        Log.e("orhan33", "servis içindesin");
        locationListener = new LocationListener() {

            @Override
            public void onLocationChanged(Location location) {

                Log.e("orhan33", location.toString());

                // get current time
//                Date currentTime = Calendar.getInstance().getTime();
//                String primaryKey = currentTime.toString();

                SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy-HH-mm");
                String primaryKey = sdf.format(new Date());


                // saving to firebase
                FirebaseDatabase database =  FirebaseDatabase.getInstance();
                DatabaseReference mRef =  database.getReference().child("coordinates");
                mRef.child(primaryKey).child("latitude").setValue(location.getLatitude());
                mRef.child(primaryKey).child("longitude").setValue(location.getLongitude());


            }

            @Override
            public void onStatusChanged(String s, int i, Bundle bundle) {

            }

            @Override
            public void onProviderEnabled(String s) {

            }

            @Override
            public void onProviderDisabled(String s) {
                Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
            }
        };


        locationManager = (LocationManager) getApplicationContext().getSystemService(Context.LOCATION_SERVICE);
        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 60000, 0, locationListener);
    }
/*
    @Override
    public void onDestroy() {
        super.onDestroy();

        if (locationManager != null){
            locationManager.removeUpdates(locationListener);
        }
    }
    */
}
