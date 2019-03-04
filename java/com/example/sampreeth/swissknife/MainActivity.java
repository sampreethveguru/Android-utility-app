package com.example.sampreeth.swissknife;

import android.*;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.provider.CalendarContract;
import android.provider.CallLog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.util.Calendar;


public class MainActivity extends AppCompatActivity {
    

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        // initiate stopwatch
        final Button button = (Button) findViewById(R.id.stopwatch);
        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent stopwatch_intent= new Intent(MainActivity.this,stopwatchActivity.class);
                startActivity(stopwatch_intent);

            }
        });
        // initiate maps
        final Button b1 = (Button) findViewById(R.id.maps);
        b1.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                Intent maps_intent= new Intent(MainActivity.this,MapsActivity.class);
                startActivity(maps_intent);

            }
        });
        // initiate contacts
        final Button b2 = (Button) findViewById(R.id.contacts);
        b2.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                                Intent contacts_intent= new Intent(MainActivity.this,ContactsReader.class);
                startActivity(contacts_intent);

            }
        });

        //initiate alarm process
        final Button b3= (Button)findViewById(R.id.alarm);
        b3.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
            Intent alarm_intent= new Intent(MainActivity.this,AlarmActivity.class);
            startActivity(alarm_intent);
            }

        });



        // initiate redial
        final Button b4= (Button) findViewById(R.id.redial);
        b4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && checkSelfPermission(android.Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                    requestPermissions(new String[]{android.Manifest.permission.CALL_PHONE}, 100);
                    //After this point you wait for callback in onRequestPermissionsResult(int, String[], int[])
                }

                if (checkSelfPermission(android.Manifest.permission.CALL_PHONE) == PackageManager.PERMISSION_GRANTED){


                    String lastCalledNumber = CallLog.Calls.getLastOutgoingCall(getApplicationContext());
                    Intent call = new Intent(Intent.ACTION_CALL);
                    call.setData(Uri.parse("tel:" + lastCalledNumber));

                    startActivity(call);
                    }

                    else{
                    Toast.makeText(MainActivity.this, "Until you grant the permission, we canot display the names", Toast.LENGTH_SHORT).show();

                }




            }
        });

        // initiate adding event to calendar
        final Button b5= (Button)findViewById(R.id.calender);
        b5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar calender = Calendar.getInstance();
                Intent intent_calender = new Intent(Intent.ACTION_EDIT);
                intent_calender.setType("vnd.android.cursor.item/event");
                intent_calender.putExtra(CalendarContract.EXTRA_EVENT_BEGIN_TIME,calender.getTimeInMillis());
                intent_calender.putExtra(CalendarContract.EXTRA_EVENT_END_TIME,calender.getTimeInMillis()+60*60*1000);
                intent_calender.putExtra(CalendarContract.EXTRA_EVENT_ALL_DAY, true);
                intent_calender.putExtra(CalendarContract.Events.TITLE, "Event title here");
                intent_calender.putExtra(CalendarContract.Events.DESCRIPTION, "This is a sample description");
                intent_calender.putExtra(CalendarContract.Events.EVENT_LOCATION, "Event Address");
                intent_calender.putExtra(CalendarContract.Events.RRULE, "FREQ=YEARLY");
                startActivity(intent_calender);

            }
        });



    }
}