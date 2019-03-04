package com.example.sampreeth.swissknife;


import android.app.Service;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.util.Log;
import android.widget.Toast;


public class RingtonePlayingService extends Service {

    MediaPlayer media_song;
    int start_id;
    boolean isRunning;

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }



    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
       // Log.i("LocalService", "Received start id " + startId + ": " + intent);

        String state = intent.getExtras().getString("extra");

        assert state != null;
        switch (state) {
            case "alarm on":
                start_id = 1;
                break;
            case "alarm off":
                start_id = 0;
                break;
            default:
                start_id = 0;
                break;
        }

        if (!this.isRunning && startId == 1)
        {

            media_song = MediaPlayer.create(this, R.raw.ring);
            media_song.start();
            this.isRunning = true;
            this.start_id = 0;
        }
        else if (!this.isRunning && startId == 0)
        {
            media_song.stop();
            media_song.reset();

            this.isRunning = false;
            this.start_id = 0;

        }
        else if (this.isRunning && startId == 0)
        {

            this.isRunning = false;
            this.start_id=0;
        }
        else if (this.isRunning && startId == 1)
        {
            this.isRunning = true;
            this.start_id = 1;

        }
        else
        {
            media_song.stop();

        }


        return START_NOT_STICKY;
    }

    @Override
    public void onDestroy() {
        // Tell the user we stopped.
        Toast.makeText(this, "on destroy called" , Toast.LENGTH_SHORT).show();
    }


}
