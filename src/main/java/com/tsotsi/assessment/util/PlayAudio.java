package com.tsotsi.assessment.util;

import android.app.Service;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.IBinder;

import com.tsotsi.assessment.myapp.AppConstants;

/**
 * Created by TSOTSI on 2016/04/20.
 */
public class PlayAudio extends Service {

    public static int POSITION;
    private MediaPlayer mediaPlayer;

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }


    public void onCreate() {
        int resID = getResourceId(POSITION);
        mediaPlayer = MediaPlayer.create(this, resID);
    }

    public int getResourceId(int position) {
        return getResources().getIdentifier(AppConstants.TRACKS[position], AppConstants.RAW_DIRECTORY, getPackageName());
    }

    public int onStartCommand(Intent intent, int flags, int startId) {
        mediaPlayer.start();
        return 1;
    }

    public void onDestroy() {
        mediaPlayer.stop();
        mediaPlayer.release();
    }

}
