package com.gpstrackingsystem;

import android.app.Dialog;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.text.format.DateFormat;
import android.widget.TimePicker;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class TimePickerFragment extends DialogFragment implements TimePickerDialog.OnTimeSetListener {

    public static final int START_TIME = 0;
    public static final int END_TIME = 1;
    private int flag = 0;

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        Calendar c = Calendar.getInstance();
        int hour = c.get(Calendar.HOUR_OF_DAY);
        int minute = c.get(Calendar.MINUTE);

        return new TimePickerDialog(getActivity(), this, hour, minute, DateFormat.is24HourFormat(getActivity()));
    }

    public void setFlag(int i) {
        flag = i;
    }

    @Override
    public void onTimeSet(TimePicker view, int hour, int minute) {
        String s = String.format("%02d:%02d", hour, minute);
//        if(hours <10) hours ="0"+ hours;
//        if(minute<10)minute="0"+minute;

        if (flag == START_TIME)
            MainActivity.startTime.setText(s);
        else if (flag == END_TIME)
            MainActivity.endTime.setText(s);


    }
}
