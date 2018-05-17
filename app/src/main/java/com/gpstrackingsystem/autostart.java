package com.gpstrackingsystem;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

public class autostart extends BroadcastReceiver{

    @Override
    public void onReceive(Context context, Intent intent) {
//        Intent gps = new Intent(context, GPS_Service.class);
//        context.startService(gps);
        if (intent.getAction().equalsIgnoreCase(Intent.ACTION_BOOT_COMPLETED)) {
            Intent serviceIntent = new Intent(context, GPS_Service.class);
            context.startService(serviceIntent);
        }

    }
}
