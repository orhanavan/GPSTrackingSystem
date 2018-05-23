package com.gpstrackingsystem;

import android.content.Intent;
import android.graphics.Color;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.util.Log;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PolylineOptions;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.zip.InflaterInputStream;

public class MapActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    Long start_milis, end_milis;
    public HashMap<String, String> chosen_coordinates = new HashMap<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        Intent intent = getIntent();
        String start_str = intent.getStringExtra("start_milis");
        String end_str = intent.getStringExtra("end_milis");

        start_milis = Long.parseLong(start_str);
        end_milis = Long.parseLong(end_str);

    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        int i=0;
        ArrayList<LatLng> points = new ArrayList<>();

        for (String key : MainActivity.coordinates.keySet()) {
            String value = MainActivity.coordinates.get(key);

            if (Long.parseLong(key) >= start_milis && Long.parseLong(key) <= end_milis){
                Log.e("orhan00", key+"-"+value);

                String[] values = value.split(",");
//                mMap.addMarker(new MarkerOptions().position(new LatLng(Long.parseLong(values[0]), Long.parseLong(values[1]))));
                Double latitude = Double.parseDouble(values[0]);
                Double longitude = Double.parseDouble(values[1]);

                if (i==0) {
                    LatLng start_point = new LatLng(latitude,longitude);
                    mMap.addMarker(new MarkerOptions().position(start_point)
                                                      .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_AZURE)));
                    i++;
                    points.add(start_point);
                    mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(start_point, 15F), 5000, null);


                } else {
                    LatLng continue_point = new LatLng(latitude,longitude);
                    mMap.addMarker(new MarkerOptions().position(continue_point)
                                                      .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN)));
                    points.add(continue_point);

                }
            }
        }


/*
        PolylineOptions polylineOptions = new PolylineOptions();
        polylineOptions.color(Color.RED);
        polylineOptions.width(5);
        polylineOptions.addAll(points);
        polylineOptions.geodesic(true);
        googleMap.addPolyline(polylineOptions);
*/
//        Map.Entry<String,String> entry = MainActivity.coordinates.entrySet().iterator().next();
//        String[] values = entry.getValue().split(",");
/*


        // Add a marker in Sydney and move the camera
        LatLng sydney = new LatLng(39.9417863,32.8548263);
        LatLng sydney2 = new LatLng(39.9447886,32.8544158);
        LatLng sydney3 = new LatLng(39.9432871,32.8491323);

        //mMap.addMarker(new MarkerOptions().position(sydney).title("Marker in Sydney"));
        //mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(sydney, 15F));
        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(sydney, 15F), 5000, null);


        MarkerOptions marker1 = new MarkerOptions();
        marker1.position(sydney);
        marker1.title("Sydney");
        mMap.addMarker(new MarkerOptions().position(sydney).title("Marker in Sydney")
                                          .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_AZURE)));

        MarkerOptions marker2 = new MarkerOptions();
        marker2.position(sydney);
        marker2.title("Sydney2");
        mMap.addMarker(new MarkerOptions().position(sydney2).title("Marker in Sydney"));


        MarkerOptions marker3 = new MarkerOptions();
        marker3.position(sydney);
        marker3.title("Sydney3");
        mMap.addMarker(new MarkerOptions().position(sydney3).title("Marker in Sydney"));


        ArrayList<LatLng> points = new ArrayList<>();
        points.add(sydney);
        points.add(sydney2);
        points.add(sydney3);

        PolylineOptions polylineOptions = new PolylineOptions();
        polylineOptions.color(Color.RED);
        polylineOptions.width(5);
        polylineOptions.addAll(points);
        polylineOptions.geodesic(true);
        googleMap.addPolyline(polylineOptions);

        */
    }
}
