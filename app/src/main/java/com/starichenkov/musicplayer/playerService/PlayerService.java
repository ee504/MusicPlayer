package com.starichenkov.musicplayer.playerService;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.databinding.ObservableField;
import android.databinding.ObservableInt;
import android.os.Binder;
import android.os.Build;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import com.starichenkov.musicplayer.R;
import com.starichenkov.musicplayer.view.MainActivity;

public class PlayerService extends Service {

    private final String TAG = "myTag";

    public static final String CHANNEL_ID = "ForegroundServiceChannel";
    private ExoPlayer exo;
    private final IBinder binder = new LocalBinder();

    public void setPlayerTo(int value) {
        exo.exoPlayer.seekTo(value);
    }

    public class LocalBinder extends Binder {
        public PlayerService getService() {
            // Return this instance of LocalService so clients can call public methods
            return PlayerService.this;
        }
    }

    @Override
    public void onCreate() {
        super.onCreate();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        return START_NOT_STICKY;
    }

    public long getTrackDuration() {
        return exo.exoPlayer.getDuration();
    }

    public long getTrackCurrentPosition() {
        return exo.exoPlayer.getCurrentPosition();
    }

    public void setPlayPausePlayer(boolean value){
        exo.exoPlayer.setPlayWhenReady(value);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        exo.exoPlayer.setPlayWhenReady(false);
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        Log.d(TAG, "onBind()");

        String input = intent.getStringExtra("inputExtra");
        createNotificationChannel();

        Notification notification = new NotificationCompat.Builder(this, CHANNEL_ID)
                .setContentTitle("Foreground Service")
                .setContentText("Some track")
                .setSmallIcon(R.drawable.vinyl_record_60x60)
                //.setContentIntent(pendingIntent)
                .build();

        startForeground(1, notification);

        exo = new ExoPlayer(getApplicationContext(), input);
        setPlayPausePlayer(true);

        return binder;
    }

    private void createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel serviceChannel = new NotificationChannel(
                    CHANNEL_ID,
                    "Music player foreground service",
                    NotificationManager.IMPORTANCE_DEFAULT
            );

            NotificationManager manager = getSystemService(NotificationManager.class);
            manager.createNotificationChannel(serviceChannel);
        }
    }

}
