package com.example.sampreeth.swissknife;

import android.app.Activity;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.icu.util.Calendar;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.TimePicker;


public class AlarmActivity extends AppCompatActivity {

    TimePicker alarm_timepicker;
    AlarmManager alarm_manager;
    TextView update_text;
    Context context;
    PendingIntent pending_intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alarm);
        this.context = this;

        //Initialize alarm manager
        alarm_manager = (AlarmManager) getSystemService(ALARM_SERVICE);

        //initialize timepicker
        alarm_timepicker = (TimePicker) findViewById(R.id.timePicker);

        //Initialize text box
        update_text = (TextView) findViewById(R.id.update_text);

        //create instance of calender
        final Calendar calendar = Calendar.getInstance();

        //Initialize Start buttons
        Button alarm_on = (Button) findViewById(R.id.alarmon);

        //Create an Intent to alarm receiver class
        final Intent my_intent = new Intent(this.context, Alarm_Receiver.class);

        //create onclick listener
        alarm_on.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //setting calender time to time picker
                calendar.set(Calendar.HOUR_OF_DAY,alarm_timepicker.getHour());
                calendar.set(Calendar.MINUTE,alarm_timepicker.getMinute());

                //get the string values of the hour and minute
                int hour = alarm_timepicker.getHour();
                int minute = alarm_timepicker.getMinute();

                //convert the int values to strings
                String hour_string = String.valueOf(hour);
                String minute_string = String.valueOf(minute);

                //convert 24 hour format
                if(hour>12)
                {
                    hour_string = String.valueOf(hour - 12);
                }

                //update text box
                set_alarm_text("Alarm at " + hour_string + " : " + minute_string);

                my_intent.putExtra("extra", "alarm on");

                //Create a pending intent
                pending_intent = PendingIntent.getBroadcast(AlarmActivity.this, 0, my_intent, PendingIntent.FLAG_UPDATE_CURRENT);

                //Set Alarm manager
                alarm_manager.set(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), pending_intent);

            }
        });

        //Initialize Stop button
        Button alarm_off = (Button) findViewById(R.id.alarmoff);
        //create onclicl listener
        alarm_off.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                set_alarm_text("alarm off");

                alarm_manager.cancel(pending_intent);

                //you pressed the alamoff
                my_intent.putExtra("extra", "alarmoff");

                //stop the ringtone
                sendBroadcast(my_intent);
            }
        });


    }

    private void set_alarm_text(String output) {
        update_text.setText(output);
    }


}