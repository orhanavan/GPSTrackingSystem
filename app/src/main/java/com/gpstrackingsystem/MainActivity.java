package com.gpstrackingsystem;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.AsyncTask;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    public static Button startDate, startTime, endDate, endTime;
    private DatePickerFragment datePicker;
    private TimePickerFragment timePicker;
    FirebaseDatabase database;
    DatabaseReference myRef;

    public static HashMap<String, String> coordinates = new HashMap<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        startDate = findViewById(R.id.startDate);
        startTime = findViewById(R.id.startTime);
        endDate = findViewById(R.id.endDate);
        endTime = findViewById(R.id.endTime);

        startDate.setOnClickListener(this);
        startTime.setOnClickListener(this);
        endDate.setOnClickListener(this);
        endTime.setOnClickListener(this);


        datePicker = new DatePickerFragment();
        timePicker = new TimePickerFragment();

        if (runtime_permission())
            Log.e("orhan33", "izin verilmemiş izin isteniyor");
        else {
            Log.e("orhan33", "izin verilmiş, servis başlatılıyor");
            //startGPS();
        }

        database = FirebaseDatabase.getInstance();
        myRef = database.getReference("coordinates");
        new connect().execute();

    }



    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.startDate:
                datePicker.setFlag(DatePickerFragment.START_DATE);
                datePicker.show(getSupportFragmentManager(), "datePicker");
                break;
            case R.id.startTime:
                timePicker.setFlag(TimePickerFragment.START_TIME);
                timePicker.show(getSupportFragmentManager(), "time picker");
                break;
            case R.id.endDate:
                datePicker.setFlag(DatePickerFragment.END_DATE);
                datePicker.show(getSupportFragmentManager(), "datePicker");
                break;
            case R.id.endTime:
                timePicker.setFlag(TimePickerFragment.END_TIME);
                timePicker.show(getSupportFragmentManager(), "time picker");
                break;
        }
    }

    public void showInMap(View view) {
        String start = startDate.getText().toString() + ", " + startTime.getText().toString();
        String end = endDate.getText().toString() + ", " + endTime.getText().toString();
        long mStart=0, mEnd=0;
        SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyy, HH:mm");
        try {
            // başlangıç milisaniye
            Date dStart = sdf.parse(start);
            mStart = dStart.getTime();

            // bitiş milisaniye
            Date dEnd = sdf.parse(end);
            mEnd = dEnd.getTime();

        } catch (ParseException e) {
            e.printStackTrace();
        }



        Intent intent = new Intent(MainActivity.this, MapActivity.class);
        intent.putExtra("start_milis", Long.toString(mStart));
        intent.putExtra("end_milis", Long.toString(mEnd));


        startActivity(intent);

    }

    public class connect extends AsyncTask<Void, Void, Void> {

        List<String> info = new ArrayList<>();

        @Override
        protected Void doInBackground(Void... voids) {


            myRef.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    for (DataSnapshot ds : dataSnapshot.getChildren()) {
                        HashMap<String, Double> hashMap = (HashMap<String, Double>) ds.getValue();
                        Double d1 = hashMap.get("latitude");
                        Double d2 = hashMap.get("longitude");

                        String values = d1 + "," + d2;
                        coordinates.put(ds.getKey(), values);

                    }

                }
                @Override
                public void onCancelled(DatabaseError error) {
                    Log.e("orhan66", "Failed to read value.", error.toException());
                }


            });

            return null;
        }
        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);

        }

    }

    private boolean runtime_permission() {
        if (Build.VERSION.SDK_INT >= 23 &&
            ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED &&
            ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

            requestPermissions(new String[]{Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION}, 100);
            return true;
        }

        return false;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (requestCode == 100) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED && grantResults[1] == PackageManager.PERMISSION_GRANTED) {
                Log.e("orhan33", "izin verildi, servis başlatılıyor");
                startGPS();
            }
            else
                Log.e("orhan33", "izin verilmedi sevis başlamadı");

        }
    }

    public void startGPS() {
        Intent intent = new Intent(getApplicationContext(), GPS_Service.class);
        startService(intent);
        Log.e("orhan33", "GPS SERVİSİ BAŞLATILDI");
    }

}
