package com.gpstrackingsystem;

import android.graphics.Color;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PolylineOptions;

import java.util.ArrayList;

public class MapActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

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
    }
}
