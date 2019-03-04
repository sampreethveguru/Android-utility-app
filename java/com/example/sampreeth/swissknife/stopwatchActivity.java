package com.example.sampreeth.swissknife;

import android.os.Handler;
import android.os.SystemClock;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

public class stopwatchActivity extends AppCompatActivity {

    Button btnstart,btnpause,btnstop;

    TextView txtTimer;
    Handler customHandler=new Handler();
    LinearLayout container;
    long startTime= 0L,timeInMilliseconds=0L,timeSwapBuff=0L,updateTime= 0L;
    Runnable updateTimerThread= new Runnable() {
        @Override
        public void run() {
            timeInMilliseconds= SystemClock.uptimeMillis()-startTime;
            updateTime= timeSwapBuff+timeInMilliseconds;
            int secs=(int)(updateTime/1000);
            int mins = secs/60;
            secs%=60;
            int milliseconds=(int)(updateTime%1000);
            txtTimer.setText(""+mins+":"+String.format("%2d",secs)+":"+String.format("%3d",milliseconds));
            customHandler.postDelayed(this,0);


        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stopwatch);
        btnstart= (Button)findViewById(R.id.btnstart);
        btnpause= (Button)findViewById(R.id.btnpause);
        btnstop= (Button)findViewById(R.id.btnstop);
        txtTimer= (TextView)findViewById(R.id.timerValue);
        btnpause.setEnabled(false);
       // container= (LinearLayout)findViewById(R.id.container);

        btnstart.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                btnstart.setEnabled(false);
                btnpause.setEnabled(true);
                startTime= SystemClock.uptimeMillis();

                customHandler.postDelayed(updateTimerThread,0);

            }
        });


        btnpause.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                btnstart.setEnabled(true);
                btnpause.setEnabled(false);
                timeSwapBuff+=timeInMilliseconds;
                customHandler.removeCallbacks(updateTimerThread);

            }
        });

        btnstop.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                btnstart.setEnabled(true);
                btnpause.setEnabled(false);
                startTime= 0L;
                timeInMilliseconds=0L;
                timeSwapBuff=0L;
                updateTime= 0L;


                //txtTimer.setText(""+0+":"+String.format("%2d",0)+":"+String.format("%3d",0));
                txtTimer.setText("0:00:000");
                customHandler.removeCallbacks(updateTimerThread);
            }
        });


    }
}
